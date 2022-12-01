package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.product.ProductUpdateDto;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.vo.product.MyProductVo;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;

import java.util.List;

public interface IProductUploadService extends IService<Product> {
    // 需要通過管理員審核才可上架
    int uploadProductRequest(ProductUploadDto productUploadDto);    // 商品上架請求
    // 顯示用戶上傳的商品(狀態、分類、日期、名稱)
    List<MyProductVo> showAllMyProduct(String phone);   // 顯示所有上傳的商品
    List<MyProductVo> showByStatus(String phone, Integer status);   // 根據狀態顯示商品
    List<MyProductVo> showByCatalog(String phone, String catalog);  // 根據分類顯示商品
    List<MyProductVo> showByKey(String phone, String keyword);  // 根據關鍵字模糊查詢商品
    Integer productOffShelf(String number);     // 商品下架
    Integer productDelete(String number);       // 刪除商品信息(完全刪除數據庫中的商品信息)
    Integer productUpdate(ProductUpdateDto productUpdate);  // 商品更新

}
