package dev.silvia.wechattrade.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductPictureUploadDto {  // 商品圖片上傳dto
    private String number;  // 商品編號(商品信息成功上傳後返回)
    private MultipartFile picture;
}
