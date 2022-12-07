package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

@Data
public class ExchangeDto {
    private String proNumber;   //商品编号

    private String ordNumber;   //订单编号

    private String name;     //商品名称

    private Integer count;     //商品数量

    private String  coverPic;   //商品图片

    private Double price;     //商品价格

    private String state;    //订单状态
}
