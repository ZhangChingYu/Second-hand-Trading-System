package dev.silvia.wechattrade.dto.product;

import lombok.Data;

@Data
public class EvaluateDto {
    private String number;  // 商品編號
    private String phone;   // 買家手機
    private Integer score1; // 描述相符得分
    private Integer score2; // 物流服務得分
    private Integer score3; // 態度服務得分
    private Boolean isAnonymous;    // 評價是否匿名(true:是，false:否)
    private String evaluate;     // 文字評價
}
