package dev.silvia.wechattrade.dto.address;

import lombok.Data;

@Data
public class AddressCreateDto {
    private String phone;   // 當前用戶手機號
    private Integer isDefaultAddress;   // 0: 不需要, 1: 需要
    private String name;
    private String receiverPhone; // 收件者的手機號
    private String region;
    private String addressDetail;
}
