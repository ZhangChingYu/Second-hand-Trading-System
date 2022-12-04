package dev.silvia.wechattrade.vo.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductCommentVo {
    private String userName;    // 用戶名
    private String headPic;     // 用戶頭像
    private String date;    // 日期
    private String content; // 留言內容
    private List<ProductCommentReplyVo> replyVoList;    // 針對該留言的回覆
}
