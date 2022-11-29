package dev.silvia.wechattrade.vo;


import lombok.Data;

import java.util.List;

@Data
public class DeleteBatchVo {
    private String phone;
    private List<String> numbers;
}
