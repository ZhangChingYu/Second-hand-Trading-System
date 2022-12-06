package dev.silvia.wechattrade.vo.report.comment;

import lombok.Data;

import java.util.Date;

@Data
public class CommentPack {  // 用來展示留言信息(管理員判斷檢舉成立用)
    private Integer id;
    private Integer fatherId;
    private String number;  // 商品編號
    private String content; // 留言內容
    private Date date;  // 留言日期
    private String phone;   // 留言者的手機
}
