package dev.silvia.wechattrade.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AuthenticationVo {   // 實名認證類
    private String phone;
    private String realName;
    private String idNumber;
    private MultipartFile idCardPic;
}
