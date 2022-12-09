package dev.silvia.wechattrade.vo.request.auth;

import lombok.Data;

@Data
public class AuthRequestOutlineVo { // 用戶實名認證請求概要
    private Integer id;      // 請求序號
    private String phone;   // 用戶手機號
    private String realName;    // 用戶真名
    private String date;        // 請求日期
    private Integer status;     // 處理狀態
}
