package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.vo.ProductVo;

public interface IDemoService2 extends IService<Product> {
    int createProductDemo(ProductVo productVo);     // 模擬商品上架，不涉及審查直接完成
    String imagesUploadDemo(ProductVo productVo);       // 測試多文件上傳
    String imageBase64Demo(String number);     // 根據商品編號獲得第一章圖片的位置，測試將圖片轉為base64形式傳給前端
}
