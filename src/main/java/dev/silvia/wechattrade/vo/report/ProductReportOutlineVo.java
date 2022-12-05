package dev.silvia.wechattrade.vo.report;

import lombok.Data;

import java.util.Date;

@Data
public class ProductReportOutlineVo {
    private Integer id;
    private Integer status;         // 舉報的處理狀態(0: 未處理, 1: 舉報成立, 2: 舉報不成立)
    private String reporterPhone;    // 舉報者手機
    private String number;  // 商品編號
    private Date date;  // 舉報日期

}
