package dev.silvia.wechattrade.vo.notification;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationOutlineVo {
    private Integer id; // 序號
    private String type; // 消息類型
    private String title; // 標題
    private Date date;  // 發送日期
    private Integer status;     // 已讀狀態
}
