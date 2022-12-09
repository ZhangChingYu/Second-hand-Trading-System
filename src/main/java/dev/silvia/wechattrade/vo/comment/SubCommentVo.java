package dev.silvia.wechattrade.vo.comment;

import lombok.Data;

@Data
public class SubCommentVo {
    private Integer id;      // 留言id
    private Integer father_id;  // 父留言id
    private String userName;    // 用戶名
    private String fatherName;  // 父留言用戶名
    private String headPic;     // 用戶頭像
    private String date;    // 日期
    private String content; // 留言內容
}
