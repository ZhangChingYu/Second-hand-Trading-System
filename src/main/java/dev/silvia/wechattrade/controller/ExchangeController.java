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


    //查找全部buyer预约
    @RequestMapping(value ="/appointments/getsellerinfo",method = RequestMethod.GET)
    public ResponseEntity<?> getsellerinfo(@RequestParam String number) {
        return ResponseEntity.ok(service.getsellerinfo(number));

    }

    //新增预约
    @RequestMapping(value ="/appointments/add",method = RequestMethod.POST)
    public ResponseEntity<?> addappointments(@RequestBody BoReDto request) {
        return ResponseEntity.ok(service.addappointments(request));

    }
    //删除预约
    @RequestMapping(value ="/appointments/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteappointments(@RequestParam String number) {
        return ResponseEntity.ok(service.deleteappointments(number));

    }
    //查找全部buyer预约
    @RequestMapping(value ="/appointments/selectallbuyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectallappointments(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectallbuyer(phone));

    }

    //查找全部seller预约
    @RequestMapping(value ="/appointments/selectallseller",method = RequestMethod.GET)
    public ResponseEntity<?> selectallseller(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectallseller(phone));

    }

    //卖家确认预约
    @RequestMapping(value ="/appointments/acquire",method = RequestMethod.POST)
    public ResponseEntity<?> acquireappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        int count = Integer.parseInt(param.get("count").toString());
        return ResponseEntity.ok(service.acquireappointments(number,count));

    }

    //买方/卖方取消
    @RequestMapping(value ="/orders/cancelappointments",method = RequestMethod.POST)
    public ResponseEntity<?> cancelappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.cancelappointments(number));
    }

    //生成订单----待付款
    @RequestMapping(value ="/orders/build",method = RequestMethod.POST)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok(service.build(request));
    }

    //卖方确认发货-----待收货
    @RequestMapping(value ="/orders/tobereceived",method = RequestMethod.POST)
    public ResponseEntity<?> tobereceived(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        String deliveryId= param.get("deliveryId").toString();
        return ResponseEntity.ok(service.tobereceived(number,deliveryId));
    }
    //买方确认收货-----已收货、待评价
    @RequestMapping(value ="/orders/received",method = RequestMethod.POST)
    public ResponseEntity<?> received(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.received(number));

    }
    //退款、售后--买家退款
    @RequestMapping(value ="/orders/after",method = RequestMethod.POST)
    public ResponseEntity<?> after(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.after(number));

    }
    //退款、售后--卖家退款
    @RequestMapping(value ="/orders/sellerafter",method = RequestMethod.POST)
    public ResponseEntity<?> sellerafter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.sellerafter(number));
    }
    //删除订单
    @RequestMapping(value ="/orders/delete",method = RequestMethod.POST)
    public ResponseEntity<?> delete(@RequestParam String number) {
        return ResponseEntity.ok(service.delete(number));

    }
    //删除订单
    @RequestMapping(value ="/orders/sellerdelete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.sellerdelete(number));

    }
    //删除订单
    @RequestMapping(value ="/orders/buyerdelete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.buyerdelete(number));

    }
    //查找所有buyer订单
    @RequestMapping(value ="/orders/selectbuyerorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectbuyerorder(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectbuyerorder(phone));
    }
    //查找所有seller订单
    @RequestMapping(value ="/orders/selectsellerorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectsellerorder(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectsellerorder(phone));
    }
}
