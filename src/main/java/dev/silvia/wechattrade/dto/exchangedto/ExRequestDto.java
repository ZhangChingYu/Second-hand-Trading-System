package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

@Data
public class ExRequestDto {
    private String projectnubmer;  //商品编号
    private String buyerphone;      //买家phone
    private String shippingaddre;  //收货地址
    private String expressDelivery;  //收货方式
    private Integer ordersNum;     //商品数量
    private Double price;          //交易价格
    private String payment;        //付款方式
    private Double freight;          //运费
    private Double discounts;      //优惠
    private String remark;         //备注
}