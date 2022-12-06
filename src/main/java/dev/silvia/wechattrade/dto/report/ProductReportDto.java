package dev.silvia.wechattrade.dto.report;

import lombok.Data;

@Data
public class ProductReportDto {
    private String number;  // 商品編號
    private String phone;   // 用戶手機號
    private String content; // 舉報原因
}
