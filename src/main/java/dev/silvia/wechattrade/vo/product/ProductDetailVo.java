package dev.silvia.wechattrade.vo.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailVo {     // 商品信息顯示用
    private String name;        // 商品名稱
    private String seller_name; // 賣家
    private String seller_pic;  // 賣家的頭像
    private String address;     // 地址
    private String date;        // 發布時間
    private Double price;       // 價格
    private Integer storage;    // 商品庫存
    private String intro;       // 商品介紹
    private Integer like_count;       // 收藏數
    private Integer picture_count;     // 照片數
    private List<String> pictures;      // 照片的base64編碼表
}
