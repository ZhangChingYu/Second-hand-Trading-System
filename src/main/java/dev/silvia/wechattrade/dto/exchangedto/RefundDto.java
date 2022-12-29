package dev.silvia.wechattrade.dto.exchangedto;
import lombok.Data;

@Data
public class RefundDto {
    private String number;  //订单编号
    private String state;
    private RefundRequest refundRequest;  //订单编号
}
