package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.ProductReportDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.report.ProductReportDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.ProductReport;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
import dev.silvia.wechattrade.handlers.Packing.ReportPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IProductReportService;
import dev.silvia.wechattrade.vo.report.ProductReportDetailVo;
import dev.silvia.wechattrade.vo.report.ProductReportOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductReportServiceImpl extends ServiceImpl<ProductReportDao, ProductReport> implements IProductReportService {
    @Autowired
    ProductReportDao productReportDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    CheckUserAuthority CUA;
    @Autowired
    ReportPacking reportPacking;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private NotificationServiceImpl noteService = new NotificationServiceImpl();

    @Override
    public Integer userPostReport(ProductReportDto dto) {
        if(!CUA.isAuthorized(dto.getPhone())){
            return 400;     // 用戶無權限
        }
        ProductReport report = new ProductReport();
        report.setDate(new Date());
        report.setNumber(dto.getNumber());
        report.setPhone(dto.getPhone());
        report.setContent(transferUTF8.CtoUTF8(dto.getContent()));
        report.setStatus(0);
        if(productReportDao.insert(report) > 0){
            Product product = getProduct(dto.getNumber());
            Integer report_count = product.getReportCount();
            report_count ++;
            String sql = "update product_manage set report_count = " + report_count + " where number = '" + dto.getNumber() +"'";
            if(jdbcTemplate.update(sql) > 0){
                return 201;     // 舉報已發送
            }
            return 422; // 商品舉報數並未更新
        }
        return 404; // 舉報發送失敗
    }

    @Override
    public List<ProductReportOutlineVo> showAllProductReport() {
        QueryWrapper<ProductReport> wrapper = new QueryWrapper<>();
        List<ProductReport> reports = productReportDao.selectList(wrapper);
        List<ProductReportOutlineVo> outlineVos = new ArrayList<>();
        for(ProductReport report : reports){
            ProductReportOutlineVo outlineVo = reportPacking.ReportToOutlineVo(report);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public List<ProductReportOutlineVo> showProductReportByNumber(String number) {
        QueryWrapper<ProductReport> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        List<ProductReport> reports = productReportDao.selectList(wrapper);
        List<ProductReportOutlineVo> outlineVos = new ArrayList<>();
        for(ProductReport report : reports){
            ProductReportOutlineVo reportVo = reportPacking.ReportToOutlineVo(report);
            outlineVos.add(reportVo);
        }
        return outlineVos;
    }

    @Override
    public List<ProductReportOutlineVo> showProductReportByStatus(Integer status) {
        QueryWrapper<ProductReport> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        List<ProductReport> reports = productReportDao.selectList(wrapper);
        List<ProductReportOutlineVo> outlineVos = new ArrayList<>();
        for(ProductReport report : reports){
            ProductReportOutlineVo reportVo = reportPacking.ReportToOutlineVo(report);
            outlineVos.add(reportVo);
        }
        return outlineVos;
    }

    @Override
    public ProductReportDetailVo readProductReportDetail(Integer id) {
        ProductReport report = productReportDao.selectById(id);
        Product product = getProduct(report.getNumber());
        User user = getUser(report.getPhone());
        ProductReportDetailVo detailVo = reportPacking.ReportToDetailVo(report, product, user, new ProductServiceImpl().getProductDetail(report.getNumber()));
        return detailVo;
    }

    @Override
    public Integer processProductReport(Integer id, String decision, String explain) {
        ProductReport report = productReportDao.selectById(id);
        Product product = getProduct(report.getNumber());
        switch (decision){
            case "pass":
                report.setStatus(1);
                if(product.getStatus() == 3 || product.getStatus() == 6){
                    noteService.sendNotification(reportPacking.autoProductReport(explain, "舉報處理通知", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "already off shelf"));
                    System.out.println("Product has already been off shelf.");
                }else {     // 檢查舉報數是否超過5，做下架處理
                    if(product.getReportCount() >= 5){
                        product.setStatus(3);
                        noteService.sendNotification(reportPacking.autoProductReport(explain, "舉報成功通知", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "off shelf"));
                        noteService.sendNotification(reportPacking.autoProductReport(explain, "商品下架通知", product.getSPhone(), true, transferUTF8.UTF8toC(product.getName()), "off shelf"));
                        if(productDao.updateById(product) > 0){
                            System.out.println("Product off shelf success.");
                        }else {
                            System.out.println("Product off shelf failed.");
                        }
                    } else{
                        // 發送通知給賣家，要求整改
                        noteService.sendNotification(reportPacking.autoProductReport(explain, "舉報成功通知", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "add report count"));
                        noteService.sendNotification(reportPacking.autoProductReport(explain, "商品舉報通知", product.getSPhone(), true, transferUTF8.UTF8toC(product.getName()), "add report count"));
                    }
                }
                break;
            case "reject":  // 商品沒有違規
                report.setStatus(2);
                noteService.sendNotification(reportPacking.autoProductReport(explain, "舉報失敗通知", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "no violation"));
                break;
            default:
                return 404; // 請求不合法
        }
        if(productReportDao.updateById(report) > 0){
            return 201;
        }
        return 400; // 數據庫更新失敗
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }

    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        return productDao.selectOne(wrapper);
    }
}
