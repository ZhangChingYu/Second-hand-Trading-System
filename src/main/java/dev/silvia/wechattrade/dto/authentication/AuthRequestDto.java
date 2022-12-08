package dev.silvia.wechattrade.dto.authentication;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AuthRequestDto {   // 實名認證請求
    private String phone;
    private String realName;
    private String idNumber;
    private MultipartFile idCardPic;
}
