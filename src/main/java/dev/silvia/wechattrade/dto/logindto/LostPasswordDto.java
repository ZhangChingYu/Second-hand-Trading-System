package dev.silvia.wechattrade.dto.logindto;
import lombok.Data;

@Data
public class LostPasswordDto {
    private String phone;
    private String password;
    private String captcha;
    private String captcha1;
}
