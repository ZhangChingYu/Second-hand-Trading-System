package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

@Data
public class ExRequestDto {
    private String sellerphone;     //卖家phone
    private String projectnubmer;  //商品编号
    private String buyerphone;      //买家phone
    private String shippingaddre;  //发货地址
    private String expressDelivery;  //收货方式
    private Integer ordersNum;     //商品数量
    private Double price;          //商品价格
    private String payment;        //付款方式
    private String remark;         //备注
}
