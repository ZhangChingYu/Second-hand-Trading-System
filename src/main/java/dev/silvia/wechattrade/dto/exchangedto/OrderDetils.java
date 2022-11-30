package dev.silvia.wechattrade.dto.exchangedto;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDetils {
    private String ordNumber;   //订单编号

    private String projectNubmer;  //商品编号
    private Integer ordersNum;     //商品数量
    private Double price;          //商品价格
    private String name;           //商品名称

    private String sellerPhone;     //卖家phone
    private String buyerPhone;      //买家phone
    private String sellerName;     //卖家name

    private String shippingAddre;  //发货地址
    private String receiptAddre;  //收货地址
    private String intro;    // 商品簡介

    private Date  brefundTime;;    //buyer退款时间
    private Date  srefundTime;;    //seller退款时间

    private String expressDelivery;  //收货方式
    private String payment;        //付款方式

    private Integer status;    //订单状态

    private String remark;         //备注
}
