package dev.silvia.wechattrade.dto.logindto;
import lombok.Data;

@Data
public class LoginDto {
    private Integer id;
    private String userName;
    private Integer authority;
    private String msg;
}
