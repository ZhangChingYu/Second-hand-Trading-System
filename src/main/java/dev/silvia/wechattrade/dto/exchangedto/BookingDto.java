package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

@Data
public class BookingDto {
    private String number;   //product number

    private String name;  //product name

    private String  coverPic;   //product coverPic

    private Double pice;  //product pice

    private Integer status;  //product status
}
