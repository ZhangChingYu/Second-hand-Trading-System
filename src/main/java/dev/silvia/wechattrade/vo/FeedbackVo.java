package dev.silvia.wechattrade.vo;

import lombok.Data;

@Data
public class FeedbackVo {   // 用戶反饋信息(給管理員看的)
    private String from;
    private String phone;
    private String date;
    private String year;
    private String month;
    private String time;
    private String content;
}
