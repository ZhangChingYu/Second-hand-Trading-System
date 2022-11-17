package dev.silvia.wechattrade.dto.response;

import lombok.Data;

@Data
public class ResponseDto {
    private  String code; //登陆状态标识
    private String msg;  //登陆状态描述
}
