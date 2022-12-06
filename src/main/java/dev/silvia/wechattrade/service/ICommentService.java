package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.comment.CommentDto;
import dev.silvia.wechattrade.dto.comment.CommentReplyDto;
import dev.silvia.wechattrade.entity.ProductComment;
import dev.silvia.wechattrade.vo.product.ProductCommentVo;
import lombok.Data;

import java.util.List;

public interface ICommentService extends IService<ProductComment> {
    List<ProductCommentVo> getComments(String number);  // 顯示商品的留言
    int postComment (CommentDto commentDto); // 發送留言
    int replyComment(CommentReplyDto replyDto); // 回覆留言
    int deleteComments(String comment);   // 刪除違規留言

    @Data
    class CommentMenu{
        public int id;
        public int father_id;
        public String number;
        public String content;
        public String date;
        public String phone;
        public List<CommentMenu> childList;
    }
}
