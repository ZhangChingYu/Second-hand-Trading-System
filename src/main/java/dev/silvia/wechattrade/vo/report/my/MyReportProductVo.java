package dev.silvia.wechattrade.vo.report.my;

import lombok.Data;

@Data
public class MyReportProductVo {    // 顯示用戶的商品舉報信息
    // (商品的卖家头像，昵称 商品图片，名称，价格  我举报的原因 举报的状态)
    private Integer id; // 商品舉報的序號id
    private String sellerName;  // 賣家的用戶名
    private String headPicFormat;      // 頭像的圖片格式
    private String sellerHeadPic;   // 賣家的頭像
    private String productName; // 商品名
    private String coverPicFormat;  // 封面的圖片格式
    private String productCover;    // 商品封面圖片
    private Double price;       // 商品價格
    private String content;     // 舉報原因
    private Integer status;     // 舉報狀態
}
