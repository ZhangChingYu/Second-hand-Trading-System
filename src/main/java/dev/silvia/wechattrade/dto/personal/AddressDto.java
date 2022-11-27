package dev.silvia.wechattrade.dto.personal;

import lombok.Data;

@Data
public class AddressDto {
    private Integer id;    //user id

    private String aid;    //地址序号

    private String phone;   //收货人

    private String name;   //手机号

    private String city;      //市

    private String province;   //省

    private String county;    //县

    private String detail;

}
