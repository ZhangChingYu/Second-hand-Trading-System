package dev.silvia.wechattrade.vo.product;


import lombok.Data;

@Data
public class ProductLikeVo {
    private String number;  // 商品編號
    private String coverPicFormat;  // 封面圖片的格式
    private String coverPic; // 封面用照片(第一張)，用base64到前端渲染
    private String name;    // 商品名稱
    private Double price;   // 商品價格
    private String intro;    // 商品簡介
}
