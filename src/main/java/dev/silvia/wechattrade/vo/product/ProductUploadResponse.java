package dev.silvia.wechattrade.vo.product;

import lombok.Data;

@Data
public class ProductUploadResponse {
    private Integer status;
    private String msg;
    private String content;
}
