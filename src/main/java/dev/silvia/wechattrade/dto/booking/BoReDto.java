package dev.silvia.wechattrade.dto.booking;
import lombok.Data;

@Data
public class BoReDto {
    private String sellerId;   //seller phone

    private String  buyerId;    //buy phone

    private String productId;   //product number

    private String productName;  //product name

    private Integer ordersNum;   //product 数量

    private Double price;   //product price
}
