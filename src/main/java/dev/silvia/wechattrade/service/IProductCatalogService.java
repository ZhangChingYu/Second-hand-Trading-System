package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.ProductCatalog;

import java.util.List;

public interface IProductCatalogService extends IService<ProductCatalog> {
    int addProductCatalog(ProductCatalog productCatalog);   // 新增商品分類
    int deleteProductCatalog(Integer id);       // 通過商品分類id刪除特定商品分類
    int updateProductCatalog(ProductCatalog productCatalog);    // 更新商品分類
    ProductCatalog getById(Integer id);     // 通過分類id查詢商品分類
    List<ProductCatalog> showAllCatalog();
}
