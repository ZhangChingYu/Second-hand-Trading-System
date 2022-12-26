package dev.silvia.wechattrade.dto.ads;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdsPost {
    MultipartFile picture;
}
