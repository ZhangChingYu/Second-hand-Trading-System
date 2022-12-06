package dev.silvia.wechattrade.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
    private String number; // 商品編號
    private String phone;   // 用戶手機號
    private String content; // 留言內容
}
