package dev.silvia.wechattrade.dto.feedback;
import lombok.Data;

import java.util.List;

@Data
public class ViolationDto {
    private List<Integer> ids;  //用户ids
    private Integer number;    //权限名称
}
