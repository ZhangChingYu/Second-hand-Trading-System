package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.booking.BoReDto;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.exchangedto.RefundDto;
import dev.silvia.wechattrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class ExchangeController {
    @Autowired
    @Resource
    private IOrderService service;

    //根据商品编号 getSellerInfo
    @RequestMapping(value ="/booking/seller/info",method = RequestMethod.GET)
    public ResponseEntity<?> getSellerInfo(@RequestParam String number) {
        return ResponseEntity.ok(service.getSellerInfo(number));
    }
    //新增预约
    @RequestMapping(value ="/booking/add",method = RequestMethod.POST)
    public ResponseEntity<?> addAppointments(@RequestBody BoReDto request) {
        return ResponseEntity.ok(service.addAppointments(request));
    }
    //删除预约
    @RequestMapping(value ="/booking/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAppointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.deleteAppointments(number));
    }

    //seller删除预约
    @RequestMapping(value ="/booking/seller/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerDeleteBooking(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.sellerDeleteBooking(number));
    }

    //buyer删除预约
    @RequestMapping(value ="/booking/buyer/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerDeleteBooking(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.buyerDeleteBooking(number));
    }

    //卖家确认预约
    @RequestMapping(value ="/booking/acquire",method = RequestMethod.PUT)
    public ResponseEntity<?> acquireAppointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.acquireAppointments(number));
    }
    //买方取消/卖方拒绝预约
    @RequestMapping(value ="/orders/cancel/booking",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelAppointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer isbuyer= Integer.valueOf(param.get("isbuyer").toString());
        return ResponseEntity.ok(service.cancelAppointments(number,isbuyer));
    }


    //生成订单
    @RequestMapping(value ="/orders/build",method = RequestMethod.POST)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok(service.build(request));
    }
    //卖方确认发货      输入订单编号number和快递单号deliveryId
    @RequestMapping(value ="/orders/receiving",method = RequestMethod.PUT)
    public ResponseEntity<?> toBeReceived(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        String deliveryId= param.get("deliveryId").toString();
        return ResponseEntity.ok(service.toBeReceived(number,deliveryId));
    }
    //买方确认收货      输入订单编号number
    @RequestMapping(value ="/orders/received",method = RequestMethod.PUT)
    public ResponseEntity<?> received(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.received(number));
    }
    //退款、售后     输入订单编号number
    @RequestMapping(value ="/orders/after",method = RequestMethod.PUT)
    public ResponseEntity<?> after(@RequestBody RefundDto refundDto) {
        return ResponseEntity.ok(service.after(refundDto));
    }
    //退款、售后     输入订单编号number
    @RequestMapping(value ="/orders/refund",method = RequestMethod.PUT)
    public ResponseEntity<?> sellerAfter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.sellerAfter(number));
    }
    //取消退款   输入订单编号number
    @RequestMapping(value ="/orders/cancel",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelAfter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.cancelAfter(number));
    }


    //删除订单     输入订单编号number
    @RequestMapping(value ="/orders/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.delete(number));

    }

    //seller删除订单  输入订单编号number
    @RequestMapping(value ="/orders/seller/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerDelete(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.sellerDelete(number));
    }

    //buyer删除订单  输入订单编号number
    @RequestMapping(value ="/orders/buyer/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerDelete(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.buyerDelete(number));
    }

    //seller删除预约或订单
    @RequestMapping(value ="seller/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> Delete(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        if(number.startsWith("YY")){
            return ResponseEntity.ok(service.sellerDeleteBooking(number));
        }
        else{
            return ResponseEntity.ok(service.sellerDelete(number));
        }
    }
}
