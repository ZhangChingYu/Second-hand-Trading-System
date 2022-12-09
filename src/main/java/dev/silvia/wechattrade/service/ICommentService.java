package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.comment.CommentDto;
import dev.silvia.wechattrade.dto.comment.CommentReplyDto;
import dev.silvia.wechattrade.entity.ProductComment;
import dev.silvia.wechattrade.vo.comment.ProductCommentVo;
import dev.silvia.wechattrade.vo.comment.RootCommentVo;
import lombok.Data;

import java.util.List;

public interface ICommentService extends IService<ProductComment> {
    List<ProductCommentVo> getComments(String number);  // 顯示商品的留言
    int postComment (CommentDto commentDto); // 發送留言
    int replyComment(CommentReplyDto replyDto); // 回覆留言
    List<RootCommentVo> getProductComments(String number);  // 顯示商品留言(改良版)
}
