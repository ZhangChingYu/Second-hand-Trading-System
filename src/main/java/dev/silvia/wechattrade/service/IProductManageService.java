package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.vo.request.product.UploadRequestDetailVo;
import dev.silvia.wechattrade.vo.request.product.UploadRequestOutlineVo;

import java.util.List;

public interface IProductManageService extends IService<Product> { // 服務端商品管理
    /** 商品上架審核 */
    List<UploadRequestOutlineVo> showAllRequest();  // 顯示所有商品上架請求(status:1)
    List<UploadRequestOutlineVo> showAllRequestByCatalog(String catalog);   // 根據商品分類顯示上架請求
    UploadRequestDetailVo readUploadRequest(String number);     // 閱讀上架請求詳情
    Integer processUploadRequest(String number, String decision, String explain);   // 處理上架請求
    Integer sendNotification(String target, String decision, String explain, String productName);        // 發送通知給賣家
    /** 商品管理 */
}
