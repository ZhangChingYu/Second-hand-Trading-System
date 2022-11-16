package dev.silvia.wechattrade.dto.logindto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String phone;
    private String password;
    private Integer authority;
}
