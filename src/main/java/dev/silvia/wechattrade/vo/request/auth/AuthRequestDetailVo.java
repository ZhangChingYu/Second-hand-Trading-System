package dev.silvia.wechattrade.vo.request.auth;

import lombok.Data;

@Data
public class AuthRequestDetailVo {
    private Integer id;     // 請求序號
    private String phone;   // 用戶手機號
    private String date;        // 請求日期
    private String realName;    // 用戶真名
    private String idNumber;    // 身分證號
    private String format;  // 圖片格式
    private String idCardPic;   // 圖片base64編碼
}
