package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.vo.product.MyProductVo;

import java.util.List;

public interface IProductUploadService extends IService<Product> {
    // 需要通過管理員審核才可上架
    int uploadProductRequest(ProductUploadDto productUploadDto);    // 商品上架請求
    // 顯示用戶上傳的商品(狀態、分類、日期、名稱)
    List<MyProductVo> showAllMyProduct(String phone);   // 顯示所有上傳的商品
    List<MyProductVo> showByStatus(String phone, Integer status);   // 根據狀態顯示商品
    List<MyProductVo> showByCatalog(String phone, String catalog);  // 根據分類顯示商品
    List<MyProductVo> showByKey(String phone, String keyword);  // 根據關鍵字模糊查詢商品
}
