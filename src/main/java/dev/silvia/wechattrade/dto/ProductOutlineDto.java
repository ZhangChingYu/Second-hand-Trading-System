package dev.silvia.wechattrade.dto;

import lombok.Data;

// 用於多商品的顯示，不包含詳細信息
@Data
public class ProductOutlineDto {
    private String number;  // 商品編號
    private String coverPic; // 封面用照片(第一張)，用base64到前端渲染
    private String name;    // 商品名稱
    private Double price;   // 商品價格
}
