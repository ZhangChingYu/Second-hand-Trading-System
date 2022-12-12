package dev.silvia.wechattrade.dto.exchangedto;
import lombok.Data;

@Data
public class RefundRequest {
    private String goodsState;
    // 退款原因
    private String refundReason;
    // 补充描述
    private String description;
}
