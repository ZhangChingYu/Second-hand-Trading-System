package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;

import java.util.List;

public interface IPromoteService extends IService<Product> {
    List<ProductOutlineVo> homepagePromote(String phone);         // 通過用戶信息推薦商品
}
