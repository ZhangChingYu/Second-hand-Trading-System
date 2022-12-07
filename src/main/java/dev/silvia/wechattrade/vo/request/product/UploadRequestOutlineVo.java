package dev.silvia.wechattrade.vo.request.product;

import lombok.Data;

import java.util.Date;

@Data
public class UploadRequestOutlineVo {   // 商品上架請求概要
    private String number;          // 商品編號
    private Integer status;         // 上架狀態
    private String catalog;      // 商品分類
    private String name;        // 商品名稱
    private String SPhone;    // 賣家手機
    private String date;  // 請求日期
}
