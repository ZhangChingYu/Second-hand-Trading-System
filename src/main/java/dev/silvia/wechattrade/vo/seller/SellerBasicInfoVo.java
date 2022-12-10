package dev.silvia.wechattrade.vo.seller;

import lombok.Data;

@Data
public class SellerBasicInfoVo {    // 用戶(商品賣家)主業基本資訊
    private String phone;       // 手機號
    private String email;       // 郵箱(如果有就返回)
    private String userName;    // 用戶名
    private Integer tradeCount; // 歷史成交量
    private String format;      // 頭像格式jpg,png...
    private String headPic;     // 頭像
}
