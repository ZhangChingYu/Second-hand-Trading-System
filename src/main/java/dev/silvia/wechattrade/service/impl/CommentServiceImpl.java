package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductCommentDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.comment.CommentDto;
import dev.silvia.wechattrade.dto.comment.CommentReplyDto;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.ProductComment;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
import dev.silvia.wechattrade.handlers.Packing.CommentPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.ICommentService;
import dev.silvia.wechattrade.vo.comment.ProductCommentVo;
import dev.silvia.wechattrade.vo.comment.RootCommentVo;
import dev.silvia.wechattrade.vo.comment.SubCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<ProductCommentDao, ProductComment> implements ICommentService {
    @Autowired
    private CheckUserAuthority CUA;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductCommentDao productCommentDao;
    @Autowired
    private TransferUTF8 transferUTF8;
    @Autowired
    private ReadFile readFile;
    @Autowired
    private CommentPacking commentPacking;

    @Override
    public List<ProductCommentVo> getComments(String number) {
        QueryWrapper<ProductComment> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        List<ProductComment> tree = productCommentDao.selectList(wrapper);
        List<ProductCommentVo> productCommentVos = new ArrayList<>();
        for(ProductComment node : tree){
            ProductCommentVo vo = new ProductCommentVo();
            vo.setId(node.getId());
            vo.setFather_id(node.getFatherId());
            vo.setUserName(transferUTF8.UTF8toC(getUser(node.getPhone()).getUserName()));
            vo.setDate(node.getDate().toString());
            vo.setContent(transferUTF8.UTF8toC(node.getContent()));
            vo.setUserName(node.getPhone());
            vo.setHeadPic(readFile.readAvatarPicture(node.getPhone()));
            if(!node.getFatherId().equals(0)){
                vo.setFatherName(transferUTF8.UTF8toC(getUser(tree.get(node.getFatherId()).getPhone()).getUserName()));
            }
            productCommentVos.add(vo);
        }
        return createTree(0, productCommentVos);
    }

    @Override
    public int postComment(CommentDto commentDto) {
        if(getUser(commentDto.getPhone()).getAuthority()!=0){    // 檢查用戶權限
            return 400; // 用戶須通過實名認證方可留言
        }
        Product product = getProduct(commentDto.getNumber());
        if(product == null){
            return 422; // 商品不存在
        }
        ProductComment comment = new ProductComment();
        comment.setFatherId(0);
        comment.setNumber(commentDto.getNumber());
        comment.setDate(new Date());
        comment.setContent(transferUTF8.CtoUTF8(commentDto.getContent()));
        comment.setPhone(commentDto.getPhone());
        if(productCommentDao.insert(comment) > 0){
            return 201; // 添加成功
        }
        return 404; // 添加失敗
    }

    @Override
    public int replyComment(CommentReplyDto replyDto) {
        if(getUser(replyDto.getPhone()).getAuthority()!=0){    // 檢查用戶權限
            return 400; // 用戶須通過實名認證方可留言
        }
        Product product = getProduct(replyDto.getNumber());
        if(product == null){
            return 422; // 商品不存在
        }
        ProductComment father = productCommentDao.selectById(replyDto.getFather());
        if(father == null){
            return 422; // 父留言不存在(可能因該留言違規已被管理員刪除)
        }
        ProductComment reply = new ProductComment();
        reply.setFatherId(replyDto.getFather());
        reply.setNumber(replyDto.getNumber());
        reply.setPhone(replyDto.getPhone());
        reply.setContent(transferUTF8.CtoUTF8(replyDto.getContent()));
        reply.setDate(new Date());
        if(productCommentDao.insert(reply) > 0){
            return 201; // 添加成功
        }
        return 404; // 添加失敗
    }

    @Override
    public List<RootCommentVo> getProductComments(String number) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<ProductComment> wrapper = new QueryWrapper<>();
        List<ProductComment> tree = productCommentDao.selectList(wrapper);
        wrapper.eq("father_id", 0);
        List<RootCommentVo> rootVos = new ArrayList<>();
        for(ProductComment node : tree){
            if(node.getFatherId()==0){
                User user = getUser(node.getPhone());
                String picture = readFile.readAvatarPicture(user.getPhone());
                RootCommentVo rootVo = commentPacking.CommentToRootVo(node, user, picture);
                List<ProductComment> subComments = new ArrayList<>();
                subComments = getSubComments(node.getId(), tree, subComments);
                List<SubCommentVo> subCommentVos = new ArrayList<>();
                for(ProductComment subComment: subComments){
                    User author = getUser(subComment.getPhone());
                    String headPic = readFile.readAvatarPicture(author.getPhone());
                    ProductComment fatherComment = productCommentDao.selectById(subComment.getFatherId());
                    User father = getUser(fatherComment.getPhone());
                    SubCommentVo subVo = commentPacking.CommentToSubVo(subComment, author, father, headPic);
                    subCommentVos.add(subVo);
                }
                rootVo.setSubComments(subCommentVos);
                rootVos.add(rootVo);
            }
        }
        return rootVos;
    }


    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        return productDao.selectOne(wrapper);
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }

    private List<ProductCommentVo> createTree(Integer father_id, List<ProductCommentVo> comments){
        List<ProductCommentVo> treeComment = new ArrayList<>();
        for(ProductCommentVo comment : comments){
            if(father_id.equals(comment.getFather_id())){
                treeComment.add(comment);
                comment.setReplyVoList(createTree(comment.getId(), comments));
            }
        }
        return treeComment;
    }

    private List<ProductComment> getSubComments(Integer father_id, List<ProductComment> comments, List<ProductComment> temp){
        List<ProductComment> subComments = temp;
        for(ProductComment comment : comments){
            if(father_id.equals(comment.getFatherId())){
                subComments.add(comment);
                subComments = getSubComments(comment.getId(), comments, subComments);
            }
        }
        return subComments ;
    }
}
