package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.exchangedto.BoReDto;
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

    //getsellerinfo
    @RequestMapping(value ="/appointments/getsellerinfo",method = RequestMethod.GET)
    public ResponseEntity<?> getsellerinfo(@RequestParam String number) {
        return ResponseEntity.ok(service.getsellerinfo(number));
    }
    //新增预约  /appointments/add
    @RequestMapping(value ="/appointments/add",method = RequestMethod.POST)
    public ResponseEntity<?> addappointments(@RequestBody BoReDto request) {
        return ResponseEntity.ok(service.addappointments(request));
    }
    //删除预约  /appointments/delete
    @RequestMapping(value ="/appointments/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteappointments(@RequestParam String number) {
        return ResponseEntity.ok(service.deleteappointments(number));

    }
    //查找全部buyer预约    /appointments/selectallbuyer
    @RequestMapping(value ="/appointments/selectallbuyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectallappointments(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectallbuyer(phone));

    }

    //查找全部seller预约  /appointments/selectallseller
    @RequestMapping(value ="/appointments/selectallseller",method = RequestMethod.GET)
    public ResponseEntity<?> selectallseller(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectallseller(phone));
    }

    //卖家确认预约  /appointments/acquire
    @RequestMapping(value ="/appointments/acquire",method = RequestMethod.PUT)
    public ResponseEntity<?> acquireappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        int count = Integer.parseInt(param.get("number").toString());
        return ResponseEntity.ok(service.acquireappointments(number,count));
    }


    //买方/卖方取消预约   /orders/cancelappointments
    @RequestMapping(value ="/orders/cancelappointments",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer isbuyer= Integer.valueOf(param.get("isbuyer").toString());
        return ResponseEntity.ok(service.cancelappointments(number,isbuyer));
    }

    //生成订单----/orders/build
    @RequestMapping(value ="/orders/build",method = RequestMethod.POST)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok(service.build(request));
    }

    //卖方确认发货-----/orders/tobereceived    输入订单编号number和快递单号deliveryId
    @RequestMapping(value ="/orders/tobereceived",method = RequestMethod.PUT)
    public ResponseEntity<?> tobereceived(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        String deliveryId= param.get("deliveryId").toString();
        return ResponseEntity.ok(service.tobereceived(number,deliveryId));
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
    //退款、售后--/orders/sellerafter    输入订单编号number
    @RequestMapping(value ="/orders/sellerafter",method = RequestMethod.PUT)
    public ResponseEntity<?> sellerafter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer count= Integer.valueOf(param.get("number").toString());
        return ResponseEntity.ok(service.sellerafter(number,count));
    }
    //删除订单   /orders/delete  输入订单编号number
    @RequestMapping(value ="/orders/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam String number) {
        return ResponseEntity.ok(service.delete(number));

    }
    //seller删除订单 /orders/sellerdelete  输入订单编号number
    @RequestMapping(value ="/orders/sellerdelete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.sellerdelete(number));

    }
    //buyer删除订单  输入订单编号number
    @RequestMapping(value ="/orders/buyerdelete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.buyerdelete(number));

    }
    //buyer查找所有订单  /orders/selectbuyerorder  phone
    @RequestMapping(value ="/orders/selectbuyerorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectbuyerorder(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectbuyerorder(phone));
    }
    //seller查找所有订单  /orders/selectsellerorder  phone
    @RequestMapping(value ="/orders/selectsellerorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectsellerorder(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectsellerorder(phone));
    }

    //查找所有订单  /orders/selectallorder  无输入
    @RequestMapping(value ="/orders/selectallorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectallorder() {
        return ResponseEntity.ok(service.selectallorder());
    }

    //查找所有预约  /orders/selectall  phone
    @RequestMapping(value ="/orders/selectall",method = RequestMethod.GET)
    public ResponseEntity<?> selectall() {
        return ResponseEntity.ok(service.selectall());
    }
}
