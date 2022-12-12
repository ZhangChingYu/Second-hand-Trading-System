package dev.silvia.wechattrade.vo.report.my;

import lombok.Data;

@Data
public class MyReportCommentVo {    // 顯示用戶舉報的留言信息
    private Integer id; // 留言舉報的序號id
    private String targetName;  // 被舉報者的用戶名
    private String headPicFormat;      // 頭像的圖片格式
    private String targetHeadPic;   // 賣家的頭像
    private String commentContent; // 舉報留言的內容
    private String content;     // 舉報原因
    private Integer status;     // 舉報狀態
}
