package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.report.ProductReportDto;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.ProductReport;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import dev.silvia.wechattrade.vo.report.ProductReportDetailVo;
import dev.silvia.wechattrade.vo.report.ProductReportOutlineVo;

import java.util.List;

public interface IProductReportService extends IService<ProductReport> {
    /** 用戶的功能 */
    Integer userPostReport(ProductReportDto dto);    // 用戶舉報商品
    /** 管理員的功能: 查詢 */
    List<ProductReportOutlineVo> showAllProductReport();       // 顯示所有商品的舉報
    List<ProductReportOutlineVo> showProductReportByNumber(String number); // 根據商品編號顯示舉報
    List<ProductReportOutlineVo> showProductReportByStatus(Integer status);    // 根據舉報狀態顯示舉報
    ProductReportDetailVo readProductReportDetail(Integer id);
    /** 管理員的功能: 處理 */
    Integer processProductReport(Integer id, String decision, String explain);  // 判定舉報是否成立(decision: pass, reject)
    Integer sendNotification(Notification notification);    // 發送通知


}
