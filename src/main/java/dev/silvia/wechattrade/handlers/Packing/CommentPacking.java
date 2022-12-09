package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.ProductComment;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.vo.comment.RootCommentVo;
import dev.silvia.wechattrade.vo.comment.SubCommentVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class CommentPacking {   // 將Product包裝成Vo的工具
    private TransferUTF8 transferUTF8 = new TransferUTF8();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SubCommentVo CommentToSubVo(ProductComment comment, User author, User father, String picture){
        SubCommentVo subVo = new SubCommentVo();
        subVo.setDate(sdf.format(comment.getDate()));
        subVo.setId(comment.getId());
        subVo.setUserName(transferUTF8.UTF8toC(author.getUserName()));
        subVo.setContent(transferUTF8.UTF8toC(comment.getContent()));
        subVo.setFather_id(comment.getFatherId());
        subVo.setHeadPic(picture);
        subVo.setFatherName(transferUTF8.UTF8toC(father.getUserName()));
        return subVo;
    }
    // 將ProductComment包裝成RootCommentVo除了回覆沒有設置
    public RootCommentVo CommentToRootVo(ProductComment comment, User user, String picture){
        RootCommentVo rootVo = new RootCommentVo();
        rootVo.setId(comment.getId());
        rootVo.setUserName(transferUTF8.UTF8toC(user.getUserName()));
        rootVo.setDate(sdf.format(comment.getDate()));
        rootVo.setContent(transferUTF8.UTF8toC(comment.getContent()));
        rootVo.setHeadPic(picture);
        return rootVo;
    }
}
