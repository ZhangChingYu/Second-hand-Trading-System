package dev.silvia.wechattrade.dto.evaluate;

import lombok.Data;

@Data
public class EvaluateDto {
    private String number;  // 商品編號
    private String phone;   // 買家手機
    private Evaluation oneEvaluate;   // 評價信息
}
