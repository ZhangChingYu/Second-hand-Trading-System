package dev.silvia.wechattrade.handlers.promote;

import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.keyword.SimilarityFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Grading {  // 對所有商品進行評分並排序後顯示
    // 對打分商品進行排序後返回商品列表
    public static List<Product> ProductPromoteList(List<GradeProduct> gradeProducts){
        List<Product> promoteList = new ArrayList<>();
        Collections.shuffle(gradeProducts);
        gradeProducts = gradeProducts.stream().sorted(Comparator.comparingDouble(GradeProduct::getGrade).reversed()).collect(Collectors.toList());
        for(GradeProduct gradeProduct : gradeProducts){
            Product product = gradeProduct.getProduct();
            promoteList.add(product);
        }
        return promoteList;
    }
    // 獲取商品類型分數
    public static Double getCatalogGrade(List<Product> like_products, List<ProductCatalog> catalogs, Product target){
        if(like_products==null || like_products.isEmpty()){
            return 0.0;
        }
        Double grade;
        Integer like_size = like_products.size();   // 計算用戶喜歡某類商品的程度分母
        List<CatalogWeight> weights = new ArrayList<>();
        for(ProductCatalog catalog : catalogs){ // 將所有商品類型映射至類中
            Double count = 0.0;
            CatalogWeight weight = new CatalogWeight();
            weight.setCatalog(catalog);
            for(Product like : like_products){  // 計算用戶收藏中的商品種類數量
                if(like.getCatalog().equals(catalog.getNumber())){
                    count += 1.0;
                }
            } // 計算用戶對應每個商品類型的喜好程度[0,1]
            count = count/(double) like_size;
            weight.setWeight(count);
            weights.add(weight);
        }
        // 計算目標(target)商品的類型得分，總分為20分
        Double t_weight = 0.0;
        for(CatalogWeight weight : weights){
            if(weight.getCatalog().getNumber().equals(target.getCatalog())){
                t_weight = weight.getWeight();
            }
        }// 若已經為收藏商品則分數減半
        for(Product like_product : like_products){
            if(like_product.equals(target)){
                t_weight = t_weight/2.0;
            }
        }
        grade = t_weight * 20.0;
        return grade;
    }
    // 獲取商品名稱分數
    public static Double getNameGrade(List<Product> like_products, Product target){
        if(like_products == null || like_products.isEmpty()){
            return 0.0;
        }
        Double grade;
        SimilarityFilter sf = new SimilarityFilter();   // 用裡面的公式計算相似度[0,1]
        // 取跟所有收藏商品比較後，相似度最大值
        Float similarity = 0f;
        for(Product like : like_products){
            if(sf.levenshtein(like.getName(), target.getName()) > similarity){
                similarity = sf.levenshtein(like.getName(), target.getName());
            }
        }
        // 計算商品名稱相似度得分，總分為30分
        grade = similarity.doubleValue() * 30.0;
        return grade;
    }
    // 獲取商品收藏分數
    public static Double getLikesGrade(List<Product> products, Product target){
        Double grade;
        Double all_likes = 0.0;
        for(Product product : products){    // 計算總like_counts
            all_likes += product.getLikeCount();
        }
        // 計算商品收藏數得分，總分為10分
        grade = (target.getLikeCount()/all_likes) * 10.0;
        return grade;
    }
    // 獲取商品留言分數
    public static Double getCommentGrade(List<ProductComment> comments){
        Double grade;
        // 只需判定用戶是否在該商品下留言過，沒有就是0分
        if(comments == null || comments.isEmpty()){
            grade = 0.0;
        }else { // 留言總分為30分
            grade = 30.0;
        }
        return grade;
    }
    // 獲取商品賣家分數
    public static Double getSellerGrade(List<User> sellers, Product target){
        if(sellers == null || sellers.isEmpty()){
            return 0.0;
        }
        Double grade = 0.0;
        // 只需判斷是否與某一訂單的賣家是相同賣家(賣家從evaluate獲取)
        for(User seller : sellers){
            if(target.getSPhone().equals(seller.getPhone())){
                grade = 10.0;
            }
        }
        return grade;
    }
}
