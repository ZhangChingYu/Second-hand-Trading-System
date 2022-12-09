package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.ProductPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IProductManageService;
import dev.silvia.wechattrade.vo.request.product.UploadRequestDetailVo;
import dev.silvia.wechattrade.vo.request.product.UploadRequestOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductManageServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductManageService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private ProductPacking productPacking;
    @Autowired
    private ReadFile readFile;
    @Autowired
    private TransferUTF8 transferUTF8;


    @Override
    public List<UploadRequestOutlineVo> showAllRequest() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<Product> products = productDao.selectList(wrapper);
        if(products == null || products.isEmpty()){
            return null;    // 沒有待審核的上架請求
        }
        List<UploadRequestOutlineVo> outlineVos = new ArrayList<>();
        for(Product product: products){
            UploadRequestOutlineVo outlineVo = productPacking.ProductToRequestOutline(product);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public List<UploadRequestOutlineVo> showAllRequestByCatalog(String catalog) {
        List<UploadRequestOutlineVo> allOutlineVos = showAllRequest();
        List<UploadRequestOutlineVo> outlineVos = new ArrayList<>();
        for(UploadRequestOutlineVo outlineVo : allOutlineVos){
            if(outlineVo.getCatalog() == catalog){
                outlineVos.add(outlineVo);
            }
        }
        return outlineVos;
    }

    @Override
    public UploadRequestDetailVo readUploadRequest(String number) {
        Product product = getProduct(number);
        User user = getUser(product.getSPhone());
        List<String> pictures = readFile.getProductPictures(number, product.getPicture());
        List<String> picturesFormat = readFile.getProductPicturesFormat(number, product.getPicture());
        UploadRequestDetailVo detailVo = productPacking.ProductToRequestDetail(product,user,pictures,picturesFormat);
        return detailVo;
    }

    @Override
    public Integer processUploadRequest(String number, String decision, String explain) {
        Product product = getProduct(number);
        switch (decision){
            case "pass":
                product.setStatus(0);   // 設置為上架狀態
                if(sendNotification(product.getSPhone(), decision, explain, transferUTF8.UTF8toC(product.getName())) == 201){
                    System.out.println("notification send success.");
                }
                System.out.println("notification send failed.");
                break;
            case "reject":
                product.setStatus(2);   // 設置為審核不通過狀態
                if(sendNotification(product.getSPhone(), decision, explain, transferUTF8.UTF8toC(product.getName())) == 201){
                    System.out.println("notification send success.");
                }
                System.out.println("notification send failed.");
                break;
        }
        if(productDao.updateById(product) > 0){
            return 201; // 上架處理成功
        }
        return 422;     // 上架處理失敗
    }

    @Override
    public Integer sendNotification(String target, String decision, String explain, String productName) {
        Notification note = new Notification();
        String content = "";
        note.setTarget(target);
        note.setSource(transferUTF8.CtoUTF8("系统管理员"));
        note.setDate(new Date());
        note.setType(1);    // [common]
        note.setStatus(1);  // 未讀
        if(decision.equals("pass")){
            note.setTitle(transferUTF8.CtoUTF8("商品上架審核通過"));
            content = "恭喜您，您的商品 [" + productName + "] 已通過系统管理员的審核，目前已成功上架。\n" + explain;
        }
        if(decision.equals("reject")){
            note.setTitle(transferUTF8.CtoUTF8("商品上架審核未通過"));
            content = "很遺憾的通知您，您的商品 [" + productName + "] 未通過系统管理员的審核，請詳細閱讀[設置]-->[幫助]中商品上架的詳細規範。\n" + explain;
        }
        note.setContent(transferUTF8.CtoUTF8(content));
        if(notificationDao.insert(note) > 0){
            return 201;
        }
        return 422;
    }

    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        return productDao.selectOne(wrapper);
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }
}
