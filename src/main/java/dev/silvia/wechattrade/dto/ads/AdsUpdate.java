package dev.silvia.wechattrade.dto.ads;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdsUpdate {
    Integer index;
    MultipartFile picture;
}
