package dev.silvia.wechattrade.dto.product;

import lombok.Data;

@Data
public class CommentReplyDto {
    private String number;  // 商品編號
    private String phone;   // 用戶手機號
    private Integer father;  // 回覆的留言編號
    private String content; // 回覆的內容
}
