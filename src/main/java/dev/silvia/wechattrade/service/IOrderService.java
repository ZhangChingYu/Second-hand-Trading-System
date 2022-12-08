package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.booking.BoReDto;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.Product;

public interface IOrderService extends IService<Product> {

    //getseller
    Result getSellerInfo(String number);

    //生成订单
    Result build(ExRequestDto request);

    //卖方确认发货
    Result toBeReceived(String number, String deliveryId);

    //买方确认收货-----已收货、待评价
    Result received(String number);

    //买家确认退款、售后
    Result after(String number);

    //卖家确认退款、售后
    Result sellerAfter(String number);

    //卖家确认退款、售后
    Result cancelAfter(String number);

    //管理员删除订单
    Result delete(String number);

    //用户删除订单---只能删除已完成的订单
    Result sellerDelete(String number);
    Result buyerDelete(String number);


    //预约相关
    Result addAppointments(BoReDto request);   //买方

    Result deleteAppointments(String number);  //删除预约---只能删除已退款或已取消的预约

    Result cancelAppointments(String number, Integer isbuyer);   //买方、卖方取消预约

    Result acquireAppointments(String number);   //卖方

    //根据订单编号获取订单详情
    Result allAllDetails(String number);

    //查找所有订单
    Result selectAllOrder(String status);
    Result selectSellerOrder(String phone,String status);
    Result selectBuyerOrder(String phone,String status);

    Result selectAllBuyer(String phone, String status);  //买家查询
    Result selectAllSeller(String phone, String status);  //买家查询
    Result selectAll(String status);

    //根据商品名称模糊搜索
    Result selectAllByName(String name, Integer type, Integer isbuyer);  //订单查询

    //卖方根据商品编号查询预约
    Result sellerBookingByName(String number);  //预约查询

    //卖方根据买方手机号和商品编号在预约信息里查找
    Result bookingByPhone(String phone,String number);  //预约查询
}
