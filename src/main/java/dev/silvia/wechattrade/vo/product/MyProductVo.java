package dev.silvia.wechattrade.vo.product;

import lombok.Data;

@Data
public class MyProductVo {
    private String number;  // 商品編號
    private String status;  // 狀態
    private String name;    // 名稱
    private String catalog; // 分類
    private Integer storage;// 庫存
    private String date;    // 上傳日期(yyyy-MM-dd, hh:mm:ss)
}
