package dev.silvia.wechattrade.vo.comment;

import lombok.Data;

import java.util.List;

@Data
public class RootCommentVo {
    private Integer id;      // 留言id
    private String userName;    // 用戶名
    private String headPic;     // 用戶頭像
    private String date;    // 日期
    private String content; // 留言內容
    private List<SubCommentVo> subComments;    // 留言的回覆
}
