package dev.silvia.wechattrade.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.ProductDetailDto;
import dev.silvia.wechattrade.dto.ProductOutlineDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.ReadFile;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();

    @Override
    public ProductDetailDto getProductDetail(String number) {
        ProductDetailDto detail = new ProductDetailDto();
        // 查詢product_manage表
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product product = productDao.selectOne(productWrapper);
        // 查詢user表
        String sql = "select * from user_info where phone='"+product.getSPhone()+"'";
        User seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
        // 開始準備商品信息
        detail.setName(transferUTF8.UTF8toC(product.getName()));
        detail.setSeller_name(transferUTF8.UTF8toC(seller.getUserName()));
        detail.setAddress(transferUTF8.UTF8toC(product.getAddress()));
        detail.setDate(getDate(product.getNumber()));     // 通過商品編號可解析出時間信息
        detail.setPrice(product.getPrice());
        detail.setIntro(transferUTF8.UTF8toC(product.getIntro()));
        detail.setLike_count(product.getLikeCount());
        detail.setPicture_count(Integer.parseInt(product.getPicture()));
        detail.setPictures(getPicturesBase64(product.getNumber(), Integer.parseInt(product.getPicture())));
        return detail;
    }

    @Override
    public int ProductStatus(String number) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        Product product = productDao.selectOne(wrapper);
        return product.getStatus();
    }

    @Override
    public List<ProductOutlineDto> getProductByCatalog(String c_number) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog", c_number);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineDto> productOutlines = transCodeOutline(products);
        return productOutlines;
    }

    @Override
    public List<ProductOutlineDto> searchProductByKey(String keyword) {
        String key = transferUTF8.CtoUTF8(keyword);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like("name", key);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineDto> productOutlines = transCodeOutline(products);
        return productOutlines;
    }

    @Override
    public List<ProductOutlineDto> homepageProductPromote() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.between("id", 10, 20);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineDto> productOutlines = transCodeOutline(products);
        return productOutlines;
    }

    String getDate(String number){  // 通過商品編碼解析出發布時間
        long time = Long.parseLong(number.substring(1));    // 將類型編號去掉
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormat = sdf.format(time);
        return dateFormat;
    }

    List<String> getPicturesBase64(String number, Integer size){
        // 通過商品編碼和照片數返回圖片的base64編碼表
        List<String> pictures = new ArrayList<>();
        Character catalog = number.charAt(0);   // 商品類型編碼
        for(int i = 0; i < size; i++){
            String url = "C:/Users/Sunny/Desktop/Products/"+catalog+"/"+number+"/"+number+"_"+i+".jpg";
            String base64 = ReadFile.getBaseFile(url);
            pictures.add(base64);
        }
        return pictures;
    }

    List<ProductOutlineDto> transCodeOutline(List<Product> products){
        List<ProductOutlineDto> productOutlines = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            ProductOutlineDto p = new ProductOutlineDto();
            Product product = products.get(i);
            p.setName(transferUTF8.UTF8toC(product.getName()));
            p.setNumber(product.getNumber());
            p.setPrice(product.getPrice());
            if(!product.getPrice().equals("null")){ // 用第一張照片做封面
                String url = "C:/Users/Sunny/Desktop/Products/"+product.getCatalog()+"/"+product.getNumber()+"/"+product.getNumber()+"_0.jpg";
                p.setCoverPic(ReadFile.getBaseFile(url));
            }
            productOutlines.add(p);
        }
        return productOutlines;
    }
}
