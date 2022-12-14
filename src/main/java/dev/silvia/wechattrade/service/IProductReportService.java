package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.report.ProductReportDto;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.ProductReport;
import dev.silvia.wechattrade.vo.report.my.MyReportCommentVo;
import dev.silvia.wechattrade.vo.report.my.MyReportProductVo;
import dev.silvia.wechattrade.vo.report.product.ProductReportDetailVo;
import dev.silvia.wechattrade.vo.report.product.ProductReportOutlineVo;

import java.util.List;

public interface IProductReportService extends IService<ProductReport> {
    /** 用戶的功能 */
    Integer userPostReport(ProductReportDto dto);    // 用戶舉報商品
    List<MyReportProductVo> showAllMyReport(String phone);  // 顯示所有用戶的商品舉報
    List<MyReportProductVo> showMyReportByStatus(String phone, Integer status); // 根據處理狀態顯示用戶的商品舉報
    Integer deleteMyReport(Integer id); // 用戶刪除某一商品舉報(已處理的就是刪除數據庫數據，未處理的視為撤回檢舉)
    /** 管理員的功能: 查詢 */
    List<ProductReportOutlineVo> showAllProductReport();       // 顯示所有商品的舉報
    List<ProductReportOutlineVo> showProductReportByNumber(String number); // 根據商品編號顯示舉報
    List<ProductReportOutlineVo> showProductReportByStatus(Integer status);    // 根據舉報狀態顯示舉報
    ProductReportDetailVo readProductReportDetail(Integer id);
    /** 管理員的功能: 處理 */
    Integer processProductReport(Integer id, String decision, String explain);  // 判定舉報是否成立(decision: pass, reject)
    Integer sendNotification(Notification notification);    // 發送通知


}
