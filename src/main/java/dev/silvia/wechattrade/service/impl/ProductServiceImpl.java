package dev.silvia.wechattrade.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dto.ProductDetailDto;
import dev.silvia.wechattrade.dto.ProductOutlineDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.ProductPacking;
import dev.silvia.wechattrade.handlers.ReadFile;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Gson gson = new Gson();
    @Autowired
    private TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    private ProductPacking productPacking = new ProductPacking();
    @Autowired
    private ReadFile readFile = new ReadFile();


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
        List<String> pictures = readFile.getPicturesBase64(product.getNumber(), product.getPicture());
        detail = productPacking.ProductUserToDetail(product, seller, pictures);
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
        List<ProductOutlineDto> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }

    @Override
    public List<ProductOutlineDto> searchProductByKey(String keyword) {
        String key = transferUTF8.CtoUTF8(keyword);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like("name", key);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineDto> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }

    @Override
    public List<ProductOutlineDto> homepageProductPromote() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.between("id", 10, 20);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineDto> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }
}
