package dev.silvia.wechattrade.dto.logindto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private Integer id;
    private String userName;
    private Integer authority;
    private String msg;   //登陆状态标识
    private String token;
}
