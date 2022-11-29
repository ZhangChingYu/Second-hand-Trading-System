package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.WriteFile;
import dev.silvia.wechattrade.service.IProductUploadService;
import dev.silvia.wechattrade.vo.product.MyProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUploadServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductUploadService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransferUTF8 transferUTF8;
    @Autowired
    private WriteFile writeFile;
    @Autowired
    private CheckUserAuthority CUA;

    @Override
    public int uploadProductRequest(ProductUploadDto dto) {
        if(!CUA.isAuthorized(dto.getPhone())){
            return 403;     // 用戶沒有權限
        }
        // 用商品分類編號+當前時間，作為每個商品的唯一標示
        String number = dto.getNumber();
        String check = "select count(*) from product_manage where number = '" +number+ "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 422;     // 商品編號重複，請間隔一段時間後再嘗試一次
        } else {
            if(writeFile.storePictures(dto.getCatalog(), dto.getNumber(), dto.getPictures()) == 800){
                return 404;     // 圖片保存失敗
            }
            Product product = new Product();
            product.setName(transferUTF8.CtoUTF8(dto.getName()));
            product.setSPhone(dto.getPhone());
            product.setNumber(dto.getNumber());
            product.setStorage(dto.getStorage());
            product.setPicture(dto.getPictures().size());
            product.setCatalog(dto.getCatalog());
            product.setIntro(transferUTF8.CtoUTF8(dto.getIntro()));
            product.setPrice(dto.getPrice());
            product.setStatus(1);   // 設置為審核中
            product.setReportCount(0);  // 初始舉報數為0
            product.setAddress(transferUTF8.CtoUTF8(dto.getAddress()));
            product.setLikeCount(0);    // 初始收藏數為0
            productDao.insert(product);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                return 201; // 商品添加成功
            }
            return 404; // 商品添加失敗
        }
    }

    @Override
    public List<MyProductVo> showAllMyProduct(String phone) {
        return null;
    }

    @Override
    public List<MyProductVo> showByOrder(String phone) {
        return null;
    }

    @Override
    public List<MyProductVo> showByStatus(String phone, Integer status) {
        return null;
    }

    @Override
    public List<MyProductVo> showByCatalog(String phone, String catalog) {
        return null;
    }
}
