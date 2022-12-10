package dev.silvia.wechattrade.dto.logindto;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private Integer id;

    private String userName;

    private String phone;

    private String password;

    private String authority;

    private String realName;

    private String idCard;

    private String stuNum;

    private Date registerDate;

    private Integer violationCount;

    private String email;

    private String avatar;

    private Integer buy;

    private Integer sell;
}
