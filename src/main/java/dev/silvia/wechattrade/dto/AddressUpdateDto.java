package dev.silvia.wechattrade.dto;

import lombok.Data;

@Data
public class AddressUpdateDto {
    private String phone;   // 當前用戶手機號
    private Integer rank;
    private String name;
    private String receiverPhone; // 收件者的手機號
    private String region;
    private String addressDetail;
}
