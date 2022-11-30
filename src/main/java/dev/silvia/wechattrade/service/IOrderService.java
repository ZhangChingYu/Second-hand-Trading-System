package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.exchangedto.BoReDto;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.Product;

public interface IOrderService extends IService<Product> {

    //getseller
    Result getsellerinfo(String number);

    //生成订单
    Result build(ExRequestDto request);

    //卖方确认发货
    Result tobereceived(String number,String deliveryId);

    //买方确认收货-----已收货、待评价
    Result received(String number);

    //买家确认退款、售后
    Result after(String number);

    //卖家确认退款、售后
    Result sellerafter(String number,Integer count);

    //管理员删除订单
    Result delete(String number);

    //用户删除订单---只能删除已完成的订单
    Result sellerdelete(String number);
    Result buyerdelete(String number);

    //查找所有订单
    Result selectallorder();
    Result selectsellerorder(String phone);
    Result selectbuyerorder(String phone);

    //预约相关
    Result addappointments(BoReDto request);   //买方

    Result deleteappointments(String number);  //删除预约---只能删除已退款或已取消的预约

    Result cancelappointments(String number,Integer isbuyer);

    Result selectallbuyer(String phone);  //买家查询
    Result selectallseller(String phone);  //买家查询
    Result selectall();  //管理员查看

    Result acquireappointments(String number,Integer count);   //卖方

    //根据订单编号获取订单详情
    Result allordersdetils(String number);   //卖方
}
