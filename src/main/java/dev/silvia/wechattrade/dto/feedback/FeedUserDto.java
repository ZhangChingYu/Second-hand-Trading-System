package dev.silvia.wechattrade.dto.feedback;

import lombok.Data;

@Data
public class FeedUserDto {
    private Integer id;

    private String phone;    //用户phone

    private String avatar;    //用户头像

    private String userName;  //用户名

    private String realName;  //用户名

    private String authority;    //用户权限

    private Integer violationCount;    //违规次数

    private Integer buy;

    private Integer sell;

}
