package dev.silvia.wechattrade.dto;

import lombok.Data;

@Data
public class AddressCreateDto {
    private boolean isDefaultAddress = false;
    private String name;
    private String phone;
    private String region;
    private String addressDetail;
}
