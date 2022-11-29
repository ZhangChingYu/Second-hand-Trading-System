package dev.silvia.wechattrade.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductVo {
    private String name;
    private String s_phone;
    private String number;
    private Integer storage;
    private List<MultipartFile> picture;
    private String intro;
    private Double price;
    private String catalog;
    private String address;
}
