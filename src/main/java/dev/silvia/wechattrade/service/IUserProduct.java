package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.Product;

public interface IUserProduct extends IService<Product> {
    int ProductApplyOnShelve(Product product);   // 商品上架申請
}
