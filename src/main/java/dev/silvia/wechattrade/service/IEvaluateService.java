package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.evaluate.EvaluateDto;
import dev.silvia.wechattrade.entity.BuyerEvaluate;
import dev.silvia.wechattrade.vo.seller.ProductEvaluateVo;
import dev.silvia.wechattrade.vo.seller.SellerBasicInfoVo;

import java.util.List;

public interface IEvaluateService extends IService<BuyerEvaluate> {
    Integer evaluateProduct(EvaluateDto dto);   // 用戶購買商品後給商品評分
    List<ProductEvaluateVo> showAllEvaluate(String phone);  // 顯示賣家的所有商品評價(日期新的排前面)
    Double calculateSellerGrade(String phone);   // 系統根據用戶歷史信息進行評分(滿分10分)
    SellerBasicInfoVo getSellerBasicInfo(String phone); // 獲取賣家基本信息
}
