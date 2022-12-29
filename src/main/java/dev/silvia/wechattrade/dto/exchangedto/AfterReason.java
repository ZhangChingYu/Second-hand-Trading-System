package dev.silvia.wechattrade.dto.exchangedto;
import lombok.Data;

@Data
public class AfterReason {
    private String state;

    private String goodsState;
    // 退款原因
    private String refundReason;
    // 补充描述
    private String description;

    private Double total;

    private String refundTime;
}
