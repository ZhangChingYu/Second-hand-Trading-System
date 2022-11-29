package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.FavoriteInfo;
import dev.silvia.wechattrade.vo.product.ProductLikeVo;

import java.util.List;

public interface ILikeService extends IService<FavoriteInfo> {
    boolean checkLike(String phone, String number);     // 確認該用戶是否已經收藏某商品
    int PressLikeButton(String phone, String number);          // 添加/取消商品收藏
    List<ProductLikeVo> showAllLike(String phone);      // 顯示所有收藏商品
    List<ProductLikeVo> showLikeByOrder(String phone, Integer type);      // 按序顯示所有商品
    // (價格排序:高到低/低到高；加入時間:進到遠/遠到近)
    List<ProductLikeVo> showLikeByCatalog(String phone, String catalog);  // 根據商品分類編號顯示收藏商品
    int deleteLikeBatches(String phone, List<String> numbers);    // 批量刪除收藏商品
}
