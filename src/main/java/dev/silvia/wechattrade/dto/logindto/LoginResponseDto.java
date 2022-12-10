package dev.silvia.wechattrade.dto.logindto;
import dev.silvia.wechattrade.entity.User;
import lombok.Data;

@Data
public class LoginResponseDto {
    private  String code; //登陆状态标识
    private String msg;  //登陆状态描述
    private User user;
    private String token;
}
