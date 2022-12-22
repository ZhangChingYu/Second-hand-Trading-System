package dev.silvia.wechattrade.dto.booking;

import lombok.Data;

@Data
public class BookingDto {
    private String bookNum;   //预约 number

    private String number;   //product number

    private String name;  //product name

    private Integer count;  //product count

    private String  coverPic;   //product coverPic

    private Double price;  //product price

    private String state;  //product state
}
