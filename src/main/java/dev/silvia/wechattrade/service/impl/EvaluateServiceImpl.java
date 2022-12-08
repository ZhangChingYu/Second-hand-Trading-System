package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.BuyerEvaluateDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.product.EvaluateDto;
import dev.silvia.wechattrade.entity.BuyerEvaluate;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.EvaluatePacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IEvaluateService;
import dev.silvia.wechattrade.vo.seller.ProductEvaluateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EvaluateServiceImpl extends ServiceImpl<BuyerEvaluateDao, BuyerEvaluate> implements IEvaluateService {
    @Autowired
    private BuyerEvaluateDao buyerEvaluateDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private TransferUTF8 transferUTF8;
    @Autowired
    private EvaluatePacking evaluatePacking;

    @Override
    public Integer evaluateProduct(EvaluateDto dto) {
        Product product = getProduct(dto.getNumber());
        BuyerEvaluate evaluate = new BuyerEvaluate();
        evaluate.setNumber(dto.getNumber());
        evaluate.setBuyerPhone(dto.getPhone());
        evaluate.setSellerPhone(product.getSPhone());
        evaluate.setScore1(dto.getScore1());
        evaluate.setScore2(dto.getScore2());
        evaluate.setScore3(dto.getScore3());
        evaluate.setEvaluate(transferUTF8.CtoUTF8(dto.getEvaluate()));
        evaluate.setDate(new Date());
        if(dto.getIsAnonymous() == true){
            evaluate.setIsAnonymous(1); // 匿名
        }else {
            evaluate.setIsAnonymous(0); // 不匿名
        }
        if(buyerEvaluateDao.insert(evaluate) > 0){
            return 201; // 添加成功
        }
        return 404; // 添加失敗
    }

    @Override
    public List<ProductEvaluateVo> showAllEvaluate(String seller_phone) {
        QueryWrapper<BuyerEvaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", seller_phone);  // 在所有評價中找出相同賣家的
        List<BuyerEvaluate> evaluates = buyerEvaluateDao.selectList(wrapper);
        List<ProductEvaluateVo> evaluateVos = new ArrayList<>();
        for(BuyerEvaluate evaluate : evaluates){
            User buyer = getUser(evaluate.getBuyerPhone());
            Product product = getProduct(evaluate.getNumber());
            ProductEvaluateVo evaluateVo = evaluatePacking.EvaluateToVo(evaluate, buyer, product);
            evaluateVos.add(evaluateVo);
        }
        return evaluateVos;
    }

    private Product getProduct(String number){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        if(productDao.selectOne(wrapper) == null){
            return null;
        }
        return productDao.selectOne(wrapper);
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }
}
