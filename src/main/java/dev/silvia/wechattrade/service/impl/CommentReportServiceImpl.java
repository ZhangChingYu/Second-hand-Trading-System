package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.CommentReportDao;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.ProductCommentDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.report.CommentReportDto;
import dev.silvia.wechattrade.entity.CommentReport;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.ProductComment;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.ReportPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.ICommentReportService;
import dev.silvia.wechattrade.vo.report.comment.CommentReportDetailVo;
import dev.silvia.wechattrade.vo.report.comment.CommentReportOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentReportServiceImpl extends ServiceImpl<CommentReportDao, CommentReport> implements ICommentReportService {
    @Autowired
    CommentReportDao commentReportDao;
    @Autowired
    NotificationDao notificationDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ProductCommentDao productCommentDao;
    @Autowired
    ReportPacking reportPacking;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer userPostReport(CommentReportDto dto) {
        if(getUser(dto.getPhone()).getAuthority() != 0){
            return 400; // 用戶無權限
        }
        CommentReport report = new CommentReport();
        report.setDate(new Date());
        report.setPhone(dto.getPhone());
        report.setCommentId(dto.getCommentId());
        report.setContent(transferUTF8.CtoUTF8(dto.getContent()));
        report.setStatus(0);
        if(commentReportDao.insert(report) > 0){    // 待審核通過後用戶的violation_count再+1
            return 201;     // 舉報已發送
        }
        return 404;     // 舉報發送失敗
    }

    @Override
    public List<CommentReportOutlineVo> showAllCommentReport() {
        QueryWrapper<CommentReport> wrapper = new QueryWrapper<>();
        List<CommentReport> reports = commentReportDao.selectList(wrapper);
        List<CommentReportOutlineVo> outlineVos = new ArrayList<>();
        for(CommentReport report : reports){
            CommentReportOutlineVo outlineVo = reportPacking.ReportToOutlineVo(report);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public List<CommentReportOutlineVo> showCommentReportByCommentId(Integer commentId) {
        QueryWrapper<CommentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commentId);
        List<CommentReport> reports = commentReportDao.selectList(wrapper);
        List<CommentReportOutlineVo> outlineVos = new ArrayList<>();
        for(CommentReport report : reports){
            CommentReportOutlineVo outlineVo = reportPacking.ReportToOutlineVo(report);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public List<CommentReportOutlineVo> showCommentReportByStatus(Integer status) {
        QueryWrapper<CommentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        List<CommentReport> reports = commentReportDao.selectList(wrapper);
        List<CommentReportOutlineVo> outlineVos = new ArrayList<>();
        for(CommentReport report : reports){
            CommentReportOutlineVo outlineVo = reportPacking.ReportToOutlineVo(report);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public CommentReportDetailVo readCommentReportDetail(Integer id) {
        CommentReport report = commentReportDao.selectById(id);
        ProductComment comment = productCommentDao.selectById(report.getCommentId());
        User user = getUser(report.getPhone());
        CommentReportDetailVo detailVo = reportPacking.ReportToDetailVo(report, comment, user);
        return detailVo;
    }

    @Override
    public Integer processCommentReport(Integer id, String decision) {
        CommentReport report = commentReportDao.selectById(id);
        ProductComment comment = productCommentDao.selectById(report.getCommentId());
        switch (decision){
            case "pass":    // 舉報通過:
                report.setStatus(1);
                // 1.被舉報的用戶violation_count要+1
                User violator = getUser(comment.getPhone());
                violator.setViolationCount(violator.getViolationCount()+1);
                if(userDao.updateById(violator) > 0){
                    System.out.println("user_info update success.");
                }else {
                    System.out.println("user_info update failed.");
                }
                // 2. 刪除該留言並更新其所有子留言
                QueryWrapper<ProductComment> wrapper = new QueryWrapper<>();
                wrapper.eq("father_id", comment.getId());
                List<ProductComment> subComments = productCommentDao.selectList(wrapper);
                // 2-1.處理子留言
                for(ProductComment subComment: subComments){
                    subComment.setFatherId(comment.getFatherId());  // 將父留言id往上提一級，如果舉報留言為根留言，子留言全提為根留言
                    productCommentDao.updateById(subComment);
                }
                // 2-2. 刪除舉報留言
                if(productCommentDao.deleteById(comment) > 0){
                    System.out.println("comment delete success.");
                } else {
                    System.out.println("comment delete failed.");
                }
                // 3. 發送通知給用戶(違規者、檢舉者、相關檢舉者)
                sendNotification(reportPacking.autoCommentReport("舉報成功通知", report.getPhone(),false, decision, transferUTF8.UTF8toC( comment.getContent())));
                sendNotification(reportPacking.autoCommentReport("留言違規通知", comment.getPhone(), true, decision, transferUTF8.UTF8toC(comment.getContent())));
                spreadNotification(id, comment.getId(), transferUTF8.UTF8toC(comment.getContent()));
                break;
            case "reject":  // 只須給舉報者發通知
                report.setStatus(2);
                sendNotification(reportPacking.autoCommentReport("舉報失敗通知", report.getPhone(), false, decision, transferUTF8.UTF8toC(comment.getContent())));
                break;
            default:
                return 404; // 請求不合法
        }
        if(commentReportDao.updateById(report) > 0){
            return 201;
        }
        return 400; // 數據庫更新失敗
    }

    @Override
    public Integer sendNotification(Notification notification) {
        if(getUser(notification.getTarget())==null){
            return 422; // 接收者不存在
        }
        notification.setId(0);
        notificationDao.insert(notification);
        return 201;
    }

    public void spreadNotification(Integer id, Integer commentId, String comment){
        QueryWrapper<CommentReport> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commentId);
        wrapper.ne("id", id);   // 排除當前閱讀的舉報
        List<CommentReport> reports = commentReportDao.selectList(wrapper);
        if(reports == null || reports.isEmpty()){
            System.out.println("no other related report found.");
        } else {
            for(CommentReport report : reports){
                report.setStatus(1); // [pass]
                sendNotification(reportPacking.autoCommentReport("舉報成功通知", report.getPhone(), false, "pass", comment));
                commentReportDao.updateById(report);
            }
        }
    }
    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }
}
