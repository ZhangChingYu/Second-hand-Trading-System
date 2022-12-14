package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.vo.HotKeyVo;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import dev.silvia.wechattrade.entity.Product;

import java.util.List;

public interface IProductService extends IService<Product> {
    ProductDetailVo getProductDetail(String number);     // 通過商品編號查詢商品詳細信息
    int ProductStatus(String number);          // 通過商品編號查詢商品狀態
    // (0: 已上架，審核通過, 1: 未上架，提交審核中, 2: 未上架，審核不通過, 3: 未上架，檢舉違規的商品)
    List<ProductOutlineVo> getProductByCatalog(String c_number);    // 通過商品分類編號返回所有同種類商品(簡化)
    List<ProductOutlineVo> homepageProducts();     // 系統通過用戶收藏商品、瀏覽歷史、查詢紀錄為用戶推送商品(簡化)
    List<ProductOutlineVo> homepageProductOrder(String type);     // 首頁商品排序
    List<ProductOutlineVo> homepageProductPromote(String number);    // 首頁促銷商品
    List<ProductOutlineVo> homepageProductNew();          // 最新商品顯示
    List<ProductOutlineVo> homepageProductLike();         // 最多收藏商品顯示
    List<ProductOutlineVo> homepagePromote(String number);         // 通過用戶收藏內容推薦商品

    /** 關鍵字查詢功能 */
    List<ProductOutlineVo> searchProductByKey(String keyword);   // 通過輸入商品名查詢相關商品(簡化)
    List<HotKeyVo> showHotKey();    // 通過系統排序返回熱門搜索關鍵詞(最多5個)
    List<ProductOutlineVo> clickHotKey(Integer id);   // 點擊熱門搜索詞

}