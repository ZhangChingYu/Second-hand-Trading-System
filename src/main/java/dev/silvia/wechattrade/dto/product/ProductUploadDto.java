package dev.silvia.wechattrade.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductUploadDto {
    private String name;    // 商品名稱
    private String phone;    // 用戶(賣家)手機號
    private String number;      // 商品編號
    private Integer storage;    // 庫存量
    private List<MultipartFile> pictures;    // 商品展示照片
    private String intro;       // 商品描述
    private Double price;       // 商品價格
    private String catalog;     // 商品分類(編碼)
    private String address;     // 發貨地
}
