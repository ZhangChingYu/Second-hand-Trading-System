package dev.silvia.wechattrade.dto.exchangedto;
import lombok.Data;

@Data
public class BoReDto {
    private String sellerId;   //seller phone

    private String  buyerId;    //buy phone

    private String productId;   //product number

    private Integer ordersNum;   //product 数量

    private Double price;   //product price
}
