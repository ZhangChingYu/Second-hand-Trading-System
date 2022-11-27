package dev.silvia.wechattrade.dto.exchangedto;

import lombok.Data;

@Data
public class BookingDto {
    private Integer id;

    private Integer sellerId;

    private Integer  buyerId;

    private Integer productId;

    private String status;
}
