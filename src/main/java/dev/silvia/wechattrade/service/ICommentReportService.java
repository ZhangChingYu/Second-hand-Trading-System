package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.report.CommentReportDto;
import dev.silvia.wechattrade.entity.CommentReport;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.vo.report.comment.CommentReportDetailVo;
import dev.silvia.wechattrade.vo.report.comment.CommentReportOutlineVo;

import java.util.List;


public interface ICommentReportService extends IService<CommentReport> {
    /** 用戶的功能 */
    Integer userPostReport(CommentReportDto dto);    // 用戶發送檢舉請求
    /** 管理員的功能: 查詢 */
    List<CommentReportOutlineVo> showAllCommentReport();    // 顯示所有留言檢舉
    List<CommentReportOutlineVo> showCommentReportByCommentId(Integer comment_id);  // 顯示檢舉相同留言的請求
    List<CommentReportOutlineVo> showCommentReportByStatus(Integer status);     // 根據檢舉處理狀態顯示檢舉請求
    CommentReportDetailVo readCommentReportDetail(Integer id);  // 檢視檢舉詳情
    /** 管理員的功能: 處理 */
    Integer processCommentReport(Integer id, String decision);     // 處理留言檢舉
    Integer sendNotification(Notification notification);    // 發送通知
}
