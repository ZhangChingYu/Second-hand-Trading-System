package dev.silvia.wechattrade.vo.notification;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDetailVo {
    private Integer id;
    private String type;
    private String title;
    private String to;  // 接收者姓名+手機號
    private String content; // 內容
    private Date date;
    private String from;
}
