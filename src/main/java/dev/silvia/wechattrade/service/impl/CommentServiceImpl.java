package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dto.product.CommentDto;
import dev.silvia.wechattrade.dto.product.CommentReplyDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.service.ICommentService;
import dev.silvia.wechattrade.vo.product.ProductCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<ProductDao, Product> implements ICommentService {
    @Autowired
    private CheckUserAuthority CUA;
    @Autowired
    private ProductDao productDao;

    private String product_url = FileDirector.PRODUCT_URL;

    @Override
    public List<ProductCommentVo> getComments(String number) {
        return null;
    }

    @Override
    public int postComment(CommentDto commentDto) {
        if(!CUA.isAuthorized(commentDto.getPhone())){    // 檢查用戶權限
            return 400; // 用戶須通過實名認證方可留言
        }
        Product product = getProduct(commentDto.getNumber());
        if(product == null){
            return 422; // 商品不存在
        }
        String catalog = product.getCatalog();
        String comment_url = product_url+catalog+"/"+product.getNumber()+"/Comments";
        return 0;
    }

    @Override
    public int replyComment(CommentReplyDto replyDto) {
        return 0;
    }

    @Override
    public int deleteComments(String comment) {
        return 0;
    }

    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        return productDao.selectOne(wrapper);
    }
}
