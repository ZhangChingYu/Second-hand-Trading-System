package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.FavoriteInfoDao;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
import dev.silvia.wechattrade.vo.product.ProductLikeVo;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import dev.silvia.wechattrade.entity.FavoriteInfo;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.ProductPacking;
import dev.silvia.wechattrade.handlers.ReadFile;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LikeServiceImpl extends ServiceImpl<FavoriteInfoDao, FavoriteInfo> implements ILikeService {

    @Autowired
    private FavoriteInfoDao favoriteInfoDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    private ProductPacking productPacking;
    @Autowired
    private CheckUserAuthority CUA;


    @Override
    public boolean checkLike(String phone, String number) {
        QueryWrapper<FavoriteInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        wrapper.eq("number", number);
        boolean result = favoriteInfoDao.exists(wrapper);
        return result;
    }

    @Override
    public int PressLikeButton(String phone, String number) {

        if(CUA.isAuthorized(phone)){
            if(!checkLike(phone, number)){  // 如果未收藏該商品
                FavoriteInfo favoriteInfo = new FavoriteInfo();
                favoriteInfo.setNumber(number);
                favoriteInfo.setPhone(phone);
                favoriteInfoDao.insert(favoriteInfo);
                if(checkLike(phone, number)){
                    changeProductLikeCount(number, "ADD");
                    return 201;     // 收藏成功
                }
                return 404;     // 收藏失敗
            }
            else{   // 取消收藏
                QueryWrapper<FavoriteInfo> wrapper = new QueryWrapper<>();
                wrapper.eq("number", number);
                wrapper.eq("phone", phone);
                favoriteInfoDao.delete(wrapper);
                changeProductLikeCount(number, "DELETE");
                return 204;     // 取消收藏成功
            }
        }
        else{
            return 403;     // 該用戶無權限使用此功能
        }
    }

    @Override
    public List<ProductLikeVo> showAllLike(String phone) {
        if(!CUA.isAuthorized(phone)){ // 檢測用戶使否有權限
            return null;
        }
        QueryWrapper<FavoriteInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        List<FavoriteInfo> favoriteList = favoriteInfoDao.selectList(wrapper);
        // 將順序由最新到最舊進行排序
        List<ProductLikeVo> likeVos = new ArrayList<>();
        for(int i = favoriteList.size()-1; i >= 0; i--){
            Product product = getProductByNumber(favoriteList.get(i).getNumber());
            ProductLikeVo productLikeVo = productPacking.ProductToProductLike(product);
            likeVos.add(productLikeVo);
        }
        return likeVos;
    }

    @Override
    public List<ProductLikeVo> showLikeByOrder(String phone, Integer type) {
        List<ProductLikeVo> likeVos = showAllLike(phone);
        switch (type){
            case 0: // 添加日期:新-->舊
                return likeVos;
            case 1: // 添加日期:舊-->新
                List<ProductLikeVo> reverseLikeVos = new ArrayList<>();
                for(int i = likeVos.size()-1; i >= 0; i--){
                    reverseLikeVos.add(likeVos.get(i));
                }
                return reverseLikeVos;
            case 2: // 商品價格:低-->高
                List<ProductLikeVo> LowToHighLikeVos = new ArrayList<>();
                LowToHighLikeVos = likeVos;
                LowToHighLikeVos.sort(Comparator.comparingDouble(ProductLikeVo::getPrice));
                return LowToHighLikeVos;
            case 3: // 商品價格:高-->低
                List<ProductLikeVo> HighToLowLikeVos = new ArrayList<>();
                HighToLowLikeVos = likeVos;
                HighToLowLikeVos.sort(Comparator.comparingDouble(ProductLikeVo::getPrice).reversed());
                return HighToLowLikeVos;
            default: break;
        }
        return null;
    }

    @Override
    public List<ProductLikeVo> showLikeByCatalog(String phone, String catalog) {
        List<ProductLikeVo> allLikes = showAllLike(phone);
        List<ProductLikeVo> filtered = new ArrayList<>();
        for(int i = 0; i < allLikes.size(); i++){
            if(allLikes.get(i).getNumber().contains(catalog)){
                filtered.add(allLikes.get(i));
            }
        }
        return filtered;
    }

    @Override
    public int deleteLikeBatches(String phone, List<String> numbers) {
        if(numbers.isEmpty()){
            return 400;     // 沒有選中的數據，數據不做更新
        }
        QueryWrapper<FavoriteInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        wrapper.in("number", numbers);
        int check = favoriteInfoDao.delete(wrapper);
        for(int i = 0; i < numbers.size(); i++){    // 同時更新product_manage表中的數據
            changeProductLikeCount(numbers.get(i), "DELETE");
        }
        if(check == numbers.size()){
            return 204; // 取消收藏完成
        }
        return 404;
    }

    Product getProductByNumber(String number){
        String sql = "select * from product_manage where number='"+number+"'";
        Product product = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class));
        return product;
    }

    void changeProductLikeCount(String number, String type){
        // 添加收藏則該商品收藏數+1，反之則-1
        Product product = getProductByNumber(number);
        int likeCount = product.getLikeCount();
        switch (type){
            case "ADD":
                likeCount++;
                String addSql = "update product_manage set like_count="+likeCount+" where number='"+number+"'";
                int add = jdbcTemplate.update(addSql);
                System.out.println("table 'product_manage' has "+ add +" data updated.");
                return;
            case "DELETE":
                likeCount--;
                String minusSql = "update product_manage set like_count="+likeCount+" where number='"+number+"'";
                int minus = jdbcTemplate.update(minusSql);
                System.out.println("table 'product_manage' has "+ minus +" data updated.");
                return;
            default: return;
        }
    }


}
