package dev.silvia.wechattrade.vo.request.product;

import lombok.Data;

import java.util.List;

@Data
public class UploadRequestDetailVo {    // 上架請求的詳情
    private String number;      // 商品編號
    private String name;        // 商品名稱
    private Integer status;     // 商品狀態
    private String catalog;     // 商品分類
    private String sellerName; // 賣家
    private String address;     // 地址
    private String date;        // 發布時間
    private Double price;       // 價格
    private Integer storage;    // 商品庫存
    private String intro;       // 商品介紹
    private Integer picture_count;     // 照片數
    private List<String> pictures;      // 照片的base64編碼表
}
