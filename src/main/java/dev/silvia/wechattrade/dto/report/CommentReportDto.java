package dev.silvia.wechattrade.dto.report;

import lombok.Data;

@Data
public class CommentReportDto {
    private Integer commentId;  // 留言id
    private String phone;   // 用戶手機號
    private String content; // 舉報原因
}
