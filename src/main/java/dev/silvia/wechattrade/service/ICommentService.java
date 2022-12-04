package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.product.CommentDto;
import dev.silvia.wechattrade.dto.product.CommentReplyDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.vo.product.ProductCommentVo;

import java.util.List;

public interface ICommentService extends IService<Product> {
    List<ProductCommentVo> getComments(String number);  // 顯示商品的留言
    int postComment (CommentDto commentDto); // 發送留言
    int replyComment(CommentReplyDto replyDto); // 回覆留言
    int deleteComments(String comment);   // 刪除違規留言
}
