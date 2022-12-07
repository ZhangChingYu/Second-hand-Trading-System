package dev.silvia.wechattrade.dto.booking;

import lombok.Data;

@Data
public class BookDetails {
    private String number;  //预约编号
    private String avatar;  //买方头像
    private String phone;  //买方phone
    private String nickName;  //买方昵称
}
