package dev.silvia.wechattrade.dto.feedback;

import lombok.Data;

@Data
public class FeedUserDto {
    private String name;  //用户名

    private String phone;    //用户电话

    private String avatar;    //用户头像

    private Integer violationCount;    //违规次数
}
