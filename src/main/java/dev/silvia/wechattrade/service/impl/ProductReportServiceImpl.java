package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.ProductReportDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.report.ProductReportDto;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.ProductReport;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.ReportPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IProductReportService;
import dev.silvia.wechattrade.vo.report.my.MyReportCommentVo;
import dev.silvia.wechattrade.vo.report.my.MyReportProductVo;
import dev.silvia.wechattrade.vo.report.product.ProductReportDetailVo;
import dev.silvia.wechattrade.vo.report.product.ProductReportOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductReportServiceImpl extends ServiceImpl<ProductReportDao, ProductReport> implements IProductReportService {
    @Autowired
    ProductReportDao productReportDao;
    @Autowired
    NotificationDao notificationDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ReportPacking reportPacking;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    ReadFile readFile;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer userPostReport(ProductReportDto dto) {
        if(getUser(dto.getPhone()).getAuthority()!=0){
            return 400;     // ???????????????
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
                return 201;     // ???????????????
            }
        }
        return 422; // ??????????????????
    }

    @Override
    public List<MyReportProductVo> showAllMyReport(String phone) {
        QueryWrapper<ProductReport> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        List<ProductReport> reports = productReportDao.selectList(wrapper);
        List<MyReportProductVo> reportVos = new ArrayList<>();
        if(reports.isEmpty()){
            return null;
        }
        for(ProductReport report : reports){
            Product product = getProduct(report.getNumber());
            User seller = getUser(product.getSPhone());
            String headPic = readFile.readAvatarPicture(seller.getPhone());
            Map<String, Object> map = readFile.getProductCoverPic(product.getNumber());
            MyReportProductVo reportVo = reportPacking.ReportToMyReportVo(report, seller, product, map, headPic);
            reportVos.add(reportVo);
        }
        return reportVos;
    }

    @Override
    public List<MyReportProductVo> showMyReportByStatus(String phone, Integer status) {
        QueryWrapper<ProductReport> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        wrapper.eq("status", status);
        List<ProductReport> reports = productReportDao.selectList(wrapper);
        if(reports.isEmpty()){
            return null;
        }
        List<MyReportProductVo> reportVos = new ArrayList<>();
        for(ProductReport report : reports){
            Product product = getProduct(report.getNumber());
            User seller = getUser(product.getSPhone());
            String headPic = readFile.readAvatarPicture(seller.getPhone());
            Map<String, Object> map = readFile.getProductCoverPic(product.getNumber());
            MyReportProductVo reportVo = reportPacking.ReportToMyReportVo(report, seller, product, map, headPic);
            reportVos.add(reportVo);
        }
        return reportVos;
    }

    @Override
    public Integer deleteMyReport(Integer id) {
        ProductReport report = productReportDao.selectById(id);
        if(productReportDao.deleteById(report) > 0){
            Product product = getProduct(report.getNumber());
            product.setReportCount(product.getReportCount()-1);
            if(productDao.updateById(product) > 0){
                return 204; // ??????????????????
            }
            return 404; // ?????????????????????????????????????????????????????????
        }
        return 422; // ??????????????????(?????????????????????)
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
        ProductReportDetailVo detailVo = reportPacking.ReportToDetailVo(report, product, user);
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
                    sendNotification(reportPacking.autoProductReport(explain, "??????????????????", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "already off shelf"));
                    System.out.println("Product has already been off shelf.");
                }else {     // ???????????????????????????5??????????????????
                    if(product.getReportCount() >= 5){  // ??????????????????????????????????????????????????????????????????
                        product.setStatus(3);
                        sendNotification(reportPacking.autoProductReport(explain, "??????????????????", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "off shelf"));
                        sendNotification(reportPacking.autoProductReport(explain, "??????????????????", product.getSPhone(), true, transferUTF8.UTF8toC(product.getName()), "off shelf"));
                        spreadNotification(product.getNumber(), id, product.getName());
                        if(productDao.updateById(product) > 0){
                            System.out.println("Product off shelf success.");
                        }else {
                            System.out.println("Product off shelf failed.");
                        }
                    } else{
                        // ????????????????????????????????????
                        sendNotification(reportPacking.autoProductReport(explain, "??????????????????", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "add report count"));
                        sendNotification(reportPacking.autoProductReport(explain, "??????????????????", product.getSPhone(), true, transferUTF8.UTF8toC(product.getName()), "add report count"));
                    }
                }
                break;
            case "reject":  // ??????????????????
                report.setStatus(2);
                sendNotification(reportPacking.autoProductReport(explain, "??????????????????", report.getPhone(), false, transferUTF8.UTF8toC(product.getName()), "no violation"));
                break;
            default:
                return 404; // ???????????????
        }
        if(productReportDao.updateById(report) > 0){
            return 201;
        }
        return 400; // ?????????????????????
    }

    @Override
    public Integer sendNotification(Notification notification) {
        if(getUser(notification.getTarget()) == null ){
            return 422; // ??????????????????
        }
        notification.setId(0);
        notificationDao.insert(notification);
        return 201; // ????????????
    }

    private User getUser(String phone){
        String findUser = "select * from user_info where phone='"+phone+"'";
        User user = jdbcTemplate.queryForObject(findUser, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    public void spreadNotification(String number, Integer id, String productName){ // ????????????????????????????????????????????????????????????
        QueryWrapper<ProductReport> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        wrapper.ne("id", id);   // ???????????????????????????
        List<ProductReport> reports = productReportDao.selectList(wrapper);
        if(reports == null || reports.isEmpty()){
            System.out.println("no other related report found.");
        } else {
            for(ProductReport report : reports){
                report.setStatus(1);
                sendNotification(reportPacking.autoProductReport("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", "??????????????????", report.getPhone(), false, transferUTF8.UTF8toC(productName), "off shelf"));
                productReportDao.updateById(report);
            }
        }
    }

    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        return productDao.selectOne(wrapper);
    }
}
