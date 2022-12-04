package dev.silvia.wechattrade.vo.product;

import lombok.Data;

@Data
public class ProductCommentReplyVo {
    private String userName;    // 用戶名
    private String headPic;     // 用戶頭像
    private String date;    // 日期
    private String content; // 留言內容
    private String replyTarget; // 回覆目標的用戶名
}
