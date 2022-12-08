package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.product.EvaluateDto;
import dev.silvia.wechattrade.entity.BuyerEvaluate;
import dev.silvia.wechattrade.vo.seller.ProductEvaluateVo;

import java.util.List;

public interface IEvaluateService extends IService<BuyerEvaluate> {
    Integer evaluateProduct(EvaluateDto dto);   // 用戶購買商品後給商品評分
    List<ProductEvaluateVo> showAllEvaluate(String phone);  // 顯示賣家的所有商品評價(日期新的排前面)
}
