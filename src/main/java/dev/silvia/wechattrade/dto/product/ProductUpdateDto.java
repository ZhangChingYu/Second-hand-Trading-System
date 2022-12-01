package dev.silvia.wechattrade.dto.product;

import lombok.Data;

@Data
public class ProductUpdateDto {
    private String name;    // 商品名稱
    private String number;      // 商品編號
    private Integer storage;    // 庫存量
    private String intro;       // 商品描述
    private Double price;       // 商品價格
    private String address;     // 發貨地
}
