package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.BuyerEvaluateDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.SellerDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.product.EvaluateDto;
import dev.silvia.wechattrade.entity.BuyerEvaluate;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.Seller;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.EvaluatePacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IEvaluateService;
import dev.silvia.wechattrade.vo.seller.ProductEvaluateVo;
import dev.silvia.wechattrade.vo.seller.SellerBasicInfoVo;
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
    private SellerDao sellerDao;
    @Autowired
    private TransferUTF8 transferUTF8;
    @Autowired
    private EvaluatePacking evaluatePacking;
    @Autowired
    private ReadFile readFile;

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

    @Override
    public Double calculateSellerGrade(String phone) {
        // 1. 用戶違規數 : violation_count
        User seller = getUser(phone);
        Integer violation_count = seller.getViolationCount();
        // 2. 用戶商品評價 : score1, score2, score3
        QueryWrapper<BuyerEvaluate> e_wrapper = new QueryWrapper<>();
        e_wrapper.eq("phone", phone);
        List<BuyerEvaluate> evaluates = buyerEvaluateDao.selectList(e_wrapper);
        List<Integer> score1_list = new ArrayList<>();  // 商品描述得分
        List<Integer> score2_list = new ArrayList<>();  // 物流服務得分
        List<Integer> score3_list = new ArrayList<>();  // 服務態度得分
        for(BuyerEvaluate evaluate : evaluates){
            score1_list.add(evaluate.getScore1());
            score2_list.add(evaluate.getScore2());
            score3_list.add(evaluate.getScore3());
        }
        // 3. 商品舉報次數 : report_counts
        QueryWrapper<Product> p_wrapper = new QueryWrapper<>();
        p_wrapper.eq("s_phone", phone);
        List<Product> seller_products = productDao.selectList(p_wrapper);
        List<Integer> report_counts = new ArrayList<>();
        for(Product seller_product : seller_products){
            report_counts.add(seller_product.getReportCount());
        }
        return get_seller_grade(violation_count, score1_list, score2_list, score3_list, report_counts);
    }

    @Override
    public SellerBasicInfoVo getSellerBasicInfo(String phone) {
        User seller = getUser(phone);
        QueryWrapper<Seller> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phone);
        Long trade_count = sellerDao.selectCount(wrapper);
        SellerBasicInfoVo infoVo = new SellerBasicInfoVo();
        infoVo.setUserName(transferUTF8.UTF8toC(seller.getUserName()));
        infoVo.setEmail(seller.getEmail());
        infoVo.setPhone(phone);
        infoVo.setTradeCount(trade_count.intValue());
        infoVo.setHeadPic(readFile.readAvatarPicture(phone));
        infoVo.setFormat("jpg");
        return infoVo;
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

    private Double get_seller_grade(Integer violation_count, List<Integer> score1_list, List<Integer> score2_list, List<Integer> score3_list, List<Integer> report_counts){
        Double init_grade = 10.00;
        Double result_grade;
        Double violation_minus, evaluate_minus;
        Double report_minus = 0.00;
        // 1. 計算違規權重
        if(violation_count > 10){   // 當用戶違規次數超過10後，違規扣分就直接扣滿
            violation_minus = 2.00;
        }else {
            violation_minus = (violation_count/10.00)*2.00;
        }
        // 2. 計算商品評價權重(商品描述: 0.6, 物流服務: 0.1, 服務態度: 0.3)
        Integer total1 = 0, total2 = 0, total3 = 0;
        Integer e_len = score1_list.size();   // 三個評價同時生成，所以等長只須計算一個長度
        for(int i = 0 ; i < e_len ; i ++){
            total1 += score1_list.get(i);
            total2 += score2_list.get(i);
            total3 += score3_list.get(i);
        }
        evaluate_minus = (1.00-(((total1.doubleValue()/(e_len*5))*0.6)+((total2.doubleValue()/(e_len*5))*0.1)+((total3.doubleValue()/(e_len*5))*0.3)))*2.00*3.00;
        // 3. 計算商品檢舉權重
        Integer r_len = report_counts.size();
        Integer report_total = 0;
        for(int i = 0 ; i < r_len ; i ++){
            report_total += report_counts.get(i);
        }
        if(report_total >= r_len){   // 如果總舉報數大於總商品數，直接扣滿
            report_minus = 2.00;
        }else {
            report_minus = (report_total.doubleValue()/r_len)*2.00;
        }
        result_grade = init_grade - violation_minus - evaluate_minus - report_minus;
        return result_grade;
    }

}
