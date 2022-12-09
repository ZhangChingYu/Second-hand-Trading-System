package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.vo.report.comment.CommentPack;
import dev.silvia.wechattrade.vo.report.comment.CommentReportDetailVo;
import dev.silvia.wechattrade.vo.report.comment.CommentReportOutlineVo;
import dev.silvia.wechattrade.vo.report.product.ProductReportDetailVo;
import dev.silvia.wechattrade.vo.report.product.ProductReportOutlineVo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReportPacking {
    private TransferUTF8 transferUTF8 = new TransferUTF8();

    public ProductReportOutlineVo ReportToOutlineVo(ProductReport report){
        ProductReportOutlineVo outlineVo = new ProductReportOutlineVo();
        outlineVo.setId(report.getId());
        outlineVo.setReporterPhone(report.getPhone());
        outlineVo.setStatus(report.getStatus());
        outlineVo.setNumber(report.getNumber());
        outlineVo.setDate(report.getDate());
        return outlineVo;
    }

    public CommentReportOutlineVo ReportToOutlineVo(CommentReport report){
        CommentReportOutlineVo outlineVo = new CommentReportOutlineVo();
        outlineVo.setId(report.getId());
        outlineVo.setReporterPhone(report.getPhone());
        outlineVo.setStatus(report.getStatus());
        outlineVo.setCommentId(report.getCommentId());
        outlineVo.setDate(report.getDate());
        return outlineVo;
    }

    public ProductReportDetailVo ReportToDetailVo(ProductReport report, Product product, User user){
        ProductReportDetailVo reportVo = new ProductReportDetailVo();
        reportVo.setId(report.getId());
        reportVo.setReporterPhone(report.getPhone());
        reportVo.setStatus(report.getStatus());
        reportVo.setReportName(transferUTF8.UTF8toC(user.getRealName()));
        reportVo.setDate(report.getDate());
        reportVo.setContent(transferUTF8.UTF8toC(report.getContent()));
        reportVo.setReportCount(product.getReportCount());
        reportVo.setProductName(transferUTF8.UTF8toC(product.getName()));
        reportVo.setProductNumber(product.getNumber());
        return reportVo;
    }
    public CommentReportDetailVo ReportToDetailVo(CommentReport report, ProductComment comment, User user){
        CommentReportDetailVo detailVo = new CommentReportDetailVo();
        detailVo.setId(report.getId());
        detailVo.setReporterPhone(report.getPhone());
        detailVo.setStatus(report.getStatus());
        detailVo.setReporterName(transferUTF8.UTF8toC(user.getRealName()));
        detailVo.setDate(report.getDate());
        detailVo.setContent(transferUTF8.UTF8toC(report.getContent()));
        detailVo.setComment(CommentToCommentPack(comment));
        return detailVo;
    }

    public CommentPack CommentToCommentPack(ProductComment comment){
        CommentPack commentPack = new CommentPack();
        commentPack.setId(comment.getId());
        commentPack.setFatherId(comment.getFatherId());
        commentPack.setPhone(comment.getPhone());
        commentPack.setDate(comment.getDate());
        commentPack.setNumber(comment.getNumber());
        commentPack.setContent(transferUTF8.UTF8toC(comment.getContent()));
        return commentPack;
    }

    public Notification autoCommentReport(String title, String to, boolean isViolator, String result, String comment){
        Notification note = new Notification();
        String content = "";
        note.setStatus(1);  // 設置為未讀
        note.setSource(transferUTF8.CtoUTF8("系统管理员"));
        note.setTitle(transferUTF8.CtoUTF8(title));
        note.setTarget(to);
        note.setDate(new Date());
        if(isViolator){ // 發送給違規用戶
            if(result == "pass"){
                note.setType(2);    // [warn]
                content = "本系統非常遺憾的通知您，您的留言 [" + comment + "] 被系统管理员判定為惡意留言，並已將其刪除。";
            }
        } else {    // 發給檢舉者
            note.setType(1);    // [common]
            if(result == "pass"){
                content = "您對留言 [" + comment + "] 的檢舉已被系统管理员判定檢舉成立，本系統已將該留言刪除。\n非常感謝您的舉報，系統將為創造友善的交易環境而努力，祝您生活愉快。";
            } else if (result == "reject") {
                content = "您對留言 [" + comment + "] 的檢舉已被系統管理員判定檢舉失敗，非常感謝您的檢舉，祝您生活愉快。";
            }
        }
        note.setContent(transferUTF8.CtoUTF8(content));
        return note;
    }

    public Notification autoProductReport(String explain, String title, String to, boolean isSeller, String productName, String result){ // 自動生成商品舉報通知
        Notification note = new Notification();
        String content = "";
        note.setStatus(1);  // 設置為未讀
        note.setSource(transferUTF8.CtoUTF8("系统管理员"));
        note.setTitle(transferUTF8.CtoUTF8(title));
        note.setTarget(to);
        note.setDate(new Date());
        if(isSeller){   // 發送給賣家
            note.setType(2);    // [warn]
            if(result == "off shelf"){
                content = "本系統非常遺憾的通知您，您的商品 ["+productName+"] 存在違規行為已被系统管理员下架。\n"+explain;
            } else if (result == "add report count") {
                content = "本系統非常遺憾的通知您，您的商品 ["+productName+"] 已被系統管理確認存在違規行為，請及時處理以免商品被迫下架。\n"+explain+"\n謝謝您的配合。";
            }
        }else { // 發送給檢舉者
            note.setType(1);    // [common]
            if(result == "off shelf"){
                content = "您對商品 ["+productName+"] 的舉報已被系统管理员處理，該商品已被下架。\n" + explain + "\n非常感謝您的檢舉，祝您生活愉快!";
            } else if (result == "add report count") {
                content = "您對商品 ["+productName+"] 的舉報判定成立，系统管理员已對該賣家發出警告要求整改。\n" + explain + "\n非常感謝您的檢舉，祝您生活愉快!";
            } else if (result == "no violation") {
                content = "您對商品 ["+productName+"] 的舉報已被系统管理员判定不成立。\n" + explain + "\n非常感謝您的檢舉，祝您生活愉快!";
            } else if (result == "already off shelf") {
                content = "您對商品 ["+productName+"] 的舉報已失效，該商品已為下架商品。\n" + explain + "\n非常感謝您的檢舉，祝您生活愉快!";
            }
        }
        note.setContent(transferUTF8.CtoUTF8(content));
        return note;
    }
}
