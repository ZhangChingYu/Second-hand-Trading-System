package dev.silvia.wechattrade.dto.feedback;
import lombok.Data;

import java.util.List;

@Data
public class DirectoryDto {
    private Integer number;  //编号
    private List<Integer> range;   //范围
}
