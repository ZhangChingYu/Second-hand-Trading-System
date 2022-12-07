package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDetails {
    private String number;  //订单编号
    private String address;  //收货地址

    private String consignee;  // 收货人姓名
    private String delivery;  //收货方式
    private String deliveryId;  //快递单号
    private String pay;        //付款方式

    private Double discount;  //优惠
    private String phone;    //收货人手机号
    private Double total;    // 成交价格

    private Date payTime;;     // 订单生成时间
    private Date deliveryTime;;    // 发货时间
    private Date confirmTime;;    // 收货时间
    private Date applyTime;;    //时间--根据订单状态获取其时间状态
    private Date refundTime;;    // 卖家同意退款时间
}
