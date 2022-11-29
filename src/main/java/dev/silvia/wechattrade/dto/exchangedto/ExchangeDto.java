package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

@Data
public class ExchangeDto {
    private String pronumber;   //商品编号

    private String ordnumber;   //订单编号

    private String name;     //商品名称

    private Integer  coverPic;   //商品图片

    private Double pice;     //商品价格

    private Integer status;    //订单状态
}
