package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.handlers.ReadFile;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IDemoService2;
import dev.silvia.wechattrade.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DemoService2Impl extends ServiceImpl<ProductDao, Product> implements IDemoService2 {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();

    private String tableName = "product_manage";

    @Override
    public int createProductDemo(ProductVo productVo) {
        // 用商品分類編號+當前時間，作為每個商品的唯一標示
        String number = productVo.getNumber();
        String check = "select count(*) from "+ tableName +" where number = '" + number + "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 422;     // 該商品編號重複，請間隔一段時間再重複一次
        }
        else {
            if(storePictures(productVo.getCatalog(), productVo.getNumber(), productVo.getPicture()) == 808){
                return 404; // 如果圖片保存失敗
            }
            Product product = new Product();
            product.setName(transferUTF8.CtoUTF8(productVo.getName()));
            product.setSPhone(productVo.getS_phone());
            product.setNumber(productVo.getNumber());
            product.setStorage(productVo.getStorage());
            product.setPicture(productVo.getPicture().size());
            product.setIntro(transferUTF8.CtoUTF8(productVo.getIntro()));
            product.setPrice(productVo.getPrice());
            product.setStatus(0);   // 都設置為已上架
            product.setReportCount(0); // 初始檢舉數為0
            product.setCatalog(productVo.getCatalog());
            product.setAddress(transferUTF8.CtoUTF8(productVo.getAddress()));
            product.setLikeCount(0);   // 初始收藏數為0
            productDao.insert(product);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                return 201; // 商品添加成功
            }
            return 404;     // 商品添加失敗
        }
    }


    // 測試圖片保存到指定路徑
    @Override
    public String imagesUploadDemo(ProductVo productVo) {
        String fileName = productVo.getName();
        // 暫時用商品名稱做目錄，正式使用要換成: 商品分類/商品編號
        String pathName = "C:/Users/Sunny/Desktop/Products/" + fileName;
        Integer length = productVo.getPicture().size();
        // 暫時存在桌面上
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return "路徑創建失敗!!";
            }
        }
        for(int i = 0; i < length; i++){
            String oldName = productVo.getPicture().get(i).getOriginalFilename();
            assert oldName != null;
            // 已馬克杯為例: 馬克杯_0.jpg 馬克杯_1.jpg
            String newName = fileName+ "_" + i + oldName.substring(oldName.lastIndexOf("."));
            try {
                productVo.getPicture().get(i).transferTo(new File(folder, newName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = pathName + "/" + newName;
            System.out.println(path);
        }
        // 使用發布時間作為商品編碼的一部分
        System.out.println(System.currentTimeMillis());
        return pathName;
    }

    @Override
    public String imageBase64Demo(String number) {
        //  course.setImage(ReadFile.getBaseFile(course.getImage()));
        String picture = null;
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        Product product = productDao.selectOne(wrapper);
        if(!product.getPrice().equals("null")){
            String url = "C:/Users/Sunny/Desktop/Products/"+product.getCatalog()+"/"+product.getNumber()+"/"+product.getNumber()+"_0.jpg";
            picture = ReadFile.getBaseFile(url);
        }
        return picture;
    }

    int storePictures(String catalog, String number, List<MultipartFile> pictures){
        // C:/Users/Sunny/Desktop/Products/catalog/number
        String pathName = "C:/Users/Sunny/Desktop/Products/" + catalog + "/" + number;
        Integer length = pictures.size();   // 獲取照片數
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return 808; // 路徑創建失敗
            }
        }
        for(int i = 0; i < length; i++){
            String oldName = pictures.get(i).getOriginalFilename();
            assert oldName != null;
            // 已馬克杯為例: 編號_0.jpg 編號_1.jpg
            String newName = number+ "_" + i + oldName.substring(oldName.lastIndexOf("."));
            try {
                pictures.get(i).transferTo(new File(folder, newName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = pathName + "/" + newName;
            System.out.println(path);
        }
        return 201;
    }
}
