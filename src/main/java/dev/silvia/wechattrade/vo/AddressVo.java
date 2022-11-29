package dev.silvia.wechattrade.vo;

import lombok.Data;

@Data
public class AddressVo {
    private String receiverName;
    private String receiverPhone;
    private String region;
    private String addressDetail;
    private Integer rank;   // 排序(0:default, 1, 2...)
}
