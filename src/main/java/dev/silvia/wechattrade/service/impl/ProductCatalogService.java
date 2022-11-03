package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.ProductCatalogDao;
import dev.silvia.wechattrade.entity.ProductCatalog;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCatalogService extends ServiceImpl<ProductCatalogDao, ProductCatalog> implements IProductCatalogService {

    @Autowired
    private ProductCatalogDao productCatalogDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();

    private String table_name = "product_catalog";

    @Override
    public int addProductCatalog(ProductCatalog productCatalog) {
        // 將商品分類名轉為UTF8
        productCatalog.setName(transferUTF8.CtoUTF8(productCatalog.getName()));

        String check = "select count(*) from " + table_name + " where number = '" + productCatalog.getNumber()+ "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 806;     // 商品編號分類必須唯一
        }
        else {
            productCatalogDao.insert(productCatalog);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                return 800;
            }
            return  808;
        }
    }

    @Override
    public int deleteProductCatalog(Integer id) {
        Integer check = productCatalogDao.deleteById(id);
        if(check == 1){
            return 800;
        }
        return 808;
    }

    @Override
    public int updateProductCatalog(ProductCatalog productCatalog) {
        Integer id = productCatalog.getId();
        String new_name = transferUTF8.CtoUTF8(productCatalog.getName());
        String new_number = productCatalog.getNumber();
        String sql = "update "+ table_name +" set name='"+ new_name +"', number='"+new_number+ "' where id ="+ id;
        int check = jdbcTemplate.update(sql);
        if(check == 1){
            return 800;     // 更新成功
        }
        return 808;
    }

    @Override
    public ProductCatalog getById(Integer id) {
        ProductCatalog productCatalog = productCatalogDao.selectById(id);
        productCatalog.setName(transferUTF8.UTF8toC(productCatalog.getName()));
        return productCatalog;
    }

    @Override
    public List<ProductCatalog> showAllCatalog() {
        // selectList()中的null表示沒有查詢條件
        List<ProductCatalog> catalogs = productCatalogDao.selectList(null);
        for(int i = 0; i < catalogs.size(); i++){      // 將name進行UTF8的解碼後輸出
            ProductCatalog productCatalog = catalogs.get(i);
            productCatalog.setName(transferUTF8.UTF8toC(productCatalog.getName()));
            catalogs.set(i, productCatalog);
        }
        return catalogs;
    }
}
