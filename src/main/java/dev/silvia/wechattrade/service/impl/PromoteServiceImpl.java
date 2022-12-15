package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.*;
import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.Packing.ProductPacking;
import dev.silvia.wechattrade.handlers.promote.GradeProduct;
import dev.silvia.wechattrade.handlers.promote.Grading;
import dev.silvia.wechattrade.service.IPromoteService;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromoteServiceImpl extends ServiceImpl<ProductDao, Product> implements IPromoteService {
    @Autowired
    ProductDao productDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ProductCatalogDao catalogDao;
    @Autowired
    FavoriteInfoDao favoriteInfoDao;
    @Autowired
    ProductCommentDao commentDao;
    @Autowired
    BuyerEvaluateDao evaluateDao;
    @Autowired
    ProductPacking productPacking;

    @Override
    public List<ProductOutlineVo> homepagePromote(String phone) {
        // 1. 全選
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        List<Product> products = productDao.selectList(wrapper);
        // 2. 準備數據
        List<ProductCatalog> catalogs = getAllCatalog();
        List<Product> like_products = getLikeProduct(getLikeInfo(phone));

        // 3. 打分
        List<GradeProduct> gradeProducts = new ArrayList<>();
        for(Product product : products){
            GradeProduct gradeProduct = new GradeProduct();
            Double catalog_grade = Grading.getCatalogGrade(like_products, catalogs, product);
            Double name_grade = Grading.getNameGrade(like_products, product);
            Double like_grade = Grading.getLikesGrade(products, product);
            Double comment_grade = Grading.getCommentGrade(getProductComment(product.getNumber(),phone));
            Double seller_grade = Grading.getSellerGrade(getSeller(getEvaluations(phone)), product);
            Double total_grade = catalog_grade + name_grade + like_grade + comment_grade + seller_grade;
            gradeProduct.setProduct(product);
            gradeProduct.setGrade(total_grade);
            //System.out.println(product.getNumber()+" : "+total_grade);
            gradeProducts.add(gradeProduct);
        }
        // 4. 排序
        List<Product> promoteList = Grading.ProductPromoteList(gradeProducts);
        // 5. 打包
        List<ProductOutlineVo> outlineVos = productPacking.ProductToOutline(promoteList);
        // 6. 輸出
        return outlineVos;
    }
    // 獲取用戶收藏表
    private List<FavoriteInfo> getLikeInfo(String phone){
        QueryWrapper<FavoriteInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        List<FavoriteInfo> favoriteInfoList = favoriteInfoDao.selectList(wrapper);
        if(favoriteInfoList == null || favoriteInfoList.isEmpty()){
            return null;
        }
        return favoriteInfoList;
    }
    // 根據用戶收藏表返回收藏Product
    private List<Product> getLikeProduct(List<FavoriteInfo> favoriteInfoList){
        List<Product> like_products = new ArrayList<>();
        if(favoriteInfoList == null || favoriteInfoList.isEmpty()){
            return null;
        }
        for(FavoriteInfo favoriteInfo : favoriteInfoList){
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.eq("number", favoriteInfo.getNumber());
            Product product = productDao.selectOne(wrapper);
            like_products.add(product);
        }
        return like_products;
    }
    // 獲取所有Catalog
    private List<ProductCatalog> getAllCatalog(){
        QueryWrapper<ProductCatalog> wrapper = new QueryWrapper<>();
        List<ProductCatalog> catalogs = catalogDao.selectList(wrapper);
        return catalogs;
    }
    // 獲取商品對應的留言
    private List<ProductComment> getProductComment(String number, String phone){
        QueryWrapper<ProductComment> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        wrapper.eq("phone", phone);
        List<ProductComment> comments = commentDao.selectList(wrapper);
        return comments;
    }
    // 獲取用戶所有評價
    private List<BuyerEvaluate> getEvaluations(String phone){
        QueryWrapper<BuyerEvaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_phone", phone);
        List<BuyerEvaluate> evaluates = evaluateDao.selectList(wrapper);
        return evaluates;
    }
    // 根據用戶評價表獲取對應賣家
    private List<User> getSeller(List<BuyerEvaluate> evaluates){
        if(evaluates == null || evaluates.isEmpty()){
            return null;
        }
        List<User> sellers = new ArrayList<>();
        for(BuyerEvaluate evaluate : evaluates){
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", evaluate.getSellerPhone());
            User seller = userDao.selectOne(wrapper);
            sellers.add(seller);
        }
        return sellers;
    }
}
