package dev.silvia.wechattrade.vo.report;

import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import lombok.Data;

import java.util.Date;

@Data
public class ProductReportDetailVo {
    private Integer id;
    private Integer status;         // 舉報的處理狀態(0: 未處理, 1: 舉報成立, 2: 舉報不成立)
    private String reporterPhone;    // 舉報者手機
    private String reportName;      // 舉報者姓名(真名)
    private Date date;            // 舉報日期
    private String content;         // 舉報原因
    private Integer reportCount;    // 該商品被舉報的次數
    private ProductDetailVo productDetail;  // 商品詳細信息
}
