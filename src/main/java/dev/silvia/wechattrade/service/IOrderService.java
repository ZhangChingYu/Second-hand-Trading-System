package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.exchangedto.ExchangeDto;
import dev.silvia.wechattrade.entity.User;

import java.util.Optional;

public interface IOrderService extends IService<User> {
    //生成订单----待付款
    Optional<ExchangeDto> build(ExRequestDto request);
    //买方确认付款-----待发货

    //卖方确认发货-----待收货

    //买方确认收货-----已收货、待评价

    //退款、售后

    //删除订单


    //预约相关
    Optional<ExchangeDto> addappointments(ExRequestDto request);
}
