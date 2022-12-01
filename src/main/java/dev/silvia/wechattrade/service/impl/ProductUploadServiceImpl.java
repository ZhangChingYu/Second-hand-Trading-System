package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dto.product.ProductUpdateDto;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
import dev.silvia.wechattrade.handlers.ProductPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.DeleteFile;
import dev.silvia.wechattrade.handlers.fileHandler.WriteFile;
import dev.silvia.wechattrade.service.IProductUploadService;
import dev.silvia.wechattrade.vo.product.MyProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private ProductPacking productPacking;
    @Autowired
    private WriteFile writeFile;
    @Autowired
    private DeleteFile deleteFile;
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
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("s_phone", phone);
        List<Product> products = productDao.selectList(wrapper);
        if(products.isEmpty() || products == null){
            return null;
        }
        List<MyProductVo> myProductVos = new ArrayList<>();
        for(int i = 0 ; i < products.size(); i ++){
            MyProductVo my = productPacking.ProductToMyProduct(products.get(i));
            myProductVos.add(my);
        }
        return myProductVos;
    }

    @Override
    public List<MyProductVo> showByStatus(String phone, Integer status) {
        List<MyProductVo> allProductVos = showAllMyProduct(phone);
        List<MyProductVo> myProductVos = new ArrayList<>();
        if(allProductVos.isEmpty() || allProductVos == null){
            return null;
        }
        for(int i = 0 ; i < allProductVos.size(); i++){
            if(allProductVos.get(i).getStatus().equals(status)){
                myProductVos.add(allProductVos.get(i));
            }
        }
        return myProductVos;
    }

    @Override
    public List<MyProductVo> showByCatalog(String phone, String catalog) {
        List<MyProductVo> allMyProductVos = showAllMyProduct(phone);
        List<MyProductVo> myProductVos = new ArrayList<>();
        if(allMyProductVos.isEmpty() || allMyProductVos == null){
            return null;
        }
        for(int i = 0 ; i < allMyProductVos.size(); i++){
            if(allMyProductVos.get(i).getNumber().contains(catalog)){
                myProductVos.add(allMyProductVos.get(i));
            }
        }
        return myProductVos;
    }

    @Override
    public List<MyProductVo> showByKey(String phone, String keyword) {
        String key = transferUTF8.CtoUTF8(keyword);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("s_phone", phone);
        wrapper.like("name", key);
        List<Product> products = productDao.selectList(wrapper);
        if(products.isEmpty() || products == null){
            return null;
        }
        List<MyProductVo> myProductVos = new ArrayList<>();
        for(int i  = 0 ; i < products.size(); i++){
            MyProductVo myProduct = productPacking.ProductToMyProduct(products.get(i));
            myProductVos.add(myProduct);
        }
        return myProductVos;
    }

    @Override
    public Integer productOffShelf(String number) {
        Product product = getProduct(number);
        if(product == null){
            return 422; // 找不到商品信息
        }
        switch (product.getStatus()){
            case 0: // 商品已上架
            case 4: // 商品已售空
            case 5: // 商品待預約
                product.setStatus(6);
                break;
            case 1: // 商品審核中，仍未上架
            case 2: // 商品審核不通過，仍未上架
            case 3: // 違規商品，系統已予以下架
            case 6: // 該商品已由用戶手動下架
                return 300;
            default:
                return 400; // 商品狀態異常
        }
        if(productDao.updateById(product) > 0){
            return 201; // 商品下架成功
        }
        return 422; // 商品下架失敗
    }

    @Override
    public Integer productReOnShelf(String number) {
        Product product = getProduct(number);
        if(product == null){
            return 422; // 找不到商品信息
        }
        if(product.getStatus() == 6){
            product.setStatus(1);
            if(productDao.updateById(product) > 0){
                return 201; // 商品恢復上架成功
            }
            return 422; // 商品恢復上架失敗
        }
        return 400;   // 商品為不可手動恢復上架狀態
    }

    @Override
    public Integer productDelete(String number) {
        Product product = getProduct(number);
        if(product == null){
            return 422; // 找不到商品信息
        }
        if(product.getStatus() == 3 || product.getStatus() == 6){   // 確認商品是否為可刪除狀態(已下架)
            // 除了數據庫內容要更新，還要刪除磁盤裡的圖片
            if(deleteFile.deleteProductPictures(number)){   // 如果商品圖片檔刪除成功
                if(productDao.deleteById(product) > 0) {    // 在刪除數據庫裡的商品信息
                    return 204; // 數據刪除成功
                }
            }
            return 400; // 刪除失敗
        }
        return 412; // 商品為不可刪除狀態(需先下架)
    }

    @Override
    public Integer productUpdate(ProductUpdateDto productUpdate) {
        Product product = getProduct(productUpdate.getNumber());
        if(product == null){
            return 422; // 商品不存在
        }
        product.setName(transferUTF8.CtoUTF8(productUpdate.getName()));
        product.setPrice(productUpdate.getPrice());
        product.setIntro(transferUTF8.CtoUTF8(productUpdate.getIntro()));
        product.setStorage(productUpdate.getStorage());
        product.setAddress(transferUTF8.CtoUTF8(productUpdate.getAddress()));
        if(productDao.updateById(product) > 0) {
            return 201; // 更新成功
        }
        return 422;    // 更新失敗
    }

    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        Product product = productDao.selectOne(wrapper);
        return product;
    }
}
