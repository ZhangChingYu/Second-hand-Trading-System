package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.ProductOutlineDto;
import dev.silvia.wechattrade.entity.FavoriteInfo;

import java.util.List;

public interface ILikeService extends IService<FavoriteInfo> {
    boolean checkLike(String phone, String number);     // 確認該用戶是否已經收藏某商品
    int PressLikeButton(String phone, String number);          // 添加/取消商品收藏
    List<ProductOutlineDto> showAllLike(String phone);      // 顯示所有收藏商品
    List<ProductOutlineDto> showLikeByOrder(String phone, Integer type);      // 按序顯示所有商品
    // (價格排序:高到低/低到高；加入時間:進到遠/遠到近)
    List<ProductOutlineDto> showLikeByCatalog(String phone, String catalog);  // 根據商品分類編號顯示收藏商品
    int deleteLikeBatches(String phone, List<String> numbers);    // 批量刪除收藏商品
}
