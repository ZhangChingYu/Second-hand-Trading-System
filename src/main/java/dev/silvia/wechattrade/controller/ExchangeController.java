package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.booking.BoReDto;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
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

    //getSellerInfo
    @RequestMapping(value ="/booking/seller/info",method = RequestMethod.GET)
    public ResponseEntity<?> getSellerInfo(@RequestParam String number) {
        return ResponseEntity.ok(service.getSellerInfo(number));
    }
    //新增预约  /booking/add
    @RequestMapping(value ="/booking/add",method = RequestMethod.POST)
    public ResponseEntity<?> addAppointments(@RequestBody BoReDto request) {
        return ResponseEntity.ok(service.addAppointments(request));
    }
    //删除预约  /booking/delete
    @RequestMapping(value ="/booking/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAppointments(@RequestParam String number) {
        return ResponseEntity.ok(service.deleteAppointments(number));

    }
    //卖家确认预约
    @RequestMapping(value ="/booking/acquire",method = RequestMethod.PUT)
    public ResponseEntity<?> acquireAppointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.acquireAppointments(number));
    }
    //买方/卖方取消预约
    @RequestMapping(value ="/orders/cancel/booking",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelAppointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer isbuyer= Integer.valueOf(param.get("isbuyer").toString());
        return ResponseEntity.ok(service.cancelAppointments(number,isbuyer));
    }



    //生成订单----/orders/build
    @RequestMapping(value ="/orders/build",method = RequestMethod.POST)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok(service.build(request));
    }
    //卖方确认发货-----/orders/receiving    输入订单编号number和快递单号deliveryId
    @RequestMapping(value ="/orders/receiving",method = RequestMethod.PUT)
    public ResponseEntity<?> toBeReceived(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        String deliveryId= param.get("deliveryId").toString();
        return ResponseEntity.ok(service.toBeReceived(number,deliveryId));
    }
    //买方确认收货-----/orders/received    输入订单编号number
    @RequestMapping(value ="/orders/received",method = RequestMethod.PUT)
    public ResponseEntity<?> received(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.received(number));

    }
    //退款、售后--/orders/after    输入订单编号number
    @RequestMapping(value ="/orders/after",method = RequestMethod.PUT)
    public ResponseEntity<?> after(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.after(number));

    }
    //退款、售后--/orders/refund    输入订单编号number
    @RequestMapping(value ="/orders/refund",method = RequestMethod.PUT)
    public ResponseEntity<?> sellerAfter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.sellerAfter(number));
    }
    //取消退款--/orders/cancel    输入订单编号number
    @RequestMapping(value ="/orders/cancel",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelAfter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.cancelAfter(number));
    }


    //删除订单   /orders/delete  输入订单编号number
    @RequestMapping(value ="/orders/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam String number) {
        return ResponseEntity.ok(service.delete(number));

    }
    //seller删除订单 /orders/seller/delete  输入订单编号number
    @RequestMapping(value ="/orders/seller/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerDelete(@RequestParam String number) {
        return ResponseEntity.ok(service.sellerDelete(number));

    }
    //buyer删除订单  输入订单编号number
    @RequestMapping(value ="/orders/buyer/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerDelete(@RequestParam String number) {
        return ResponseEntity.ok(service.buyerDelete(number));

    }
}
