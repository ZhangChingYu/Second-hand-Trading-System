package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExchangeController {
    @Autowired
    @Resource
    private IOrderService service;

    //新增预约
    @RequestMapping(value ="/appointments/add",method = RequestMethod.POST)
    public ResponseEntity<?> addappointments(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //删除预约
    @RequestMapping(value ="/appointments/delete",method = RequestMethod.POST)
    public ResponseEntity<?> deleteappointments(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //查找全部预约
    @RequestMapping(value ="/appointments/selectall",method = RequestMethod.GET)
    public ResponseEntity<?> selectallappointments(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //查找单个预约
    @RequestMapping(value ="/appointments/select",method = RequestMethod.GET)
    public ResponseEntity<?> selectappointments(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //生成订单----待付款
    @RequestMapping(value ="/orders/build",method = RequestMethod.POST)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
            return ResponseEntity.ok("cds");

    }
    //买方确认付款-----待发货
    @RequestMapping(value ="/orders/tobeshipped",method = RequestMethod.POST)
    public ResponseEntity<?> tobeshipped(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //卖方确认发货-----待收货
    @RequestMapping(value ="/orders/tobereceived",method = RequestMethod.POST)
    public ResponseEntity<?> tobereceived(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //买方确认收货-----已收货、待评价
    @RequestMapping(value ="/orders/received",method = RequestMethod.POST)
    public ResponseEntity<?> received(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //退款、售后
    @RequestMapping(value ="/orders/after",method = RequestMethod.POST)
    public ResponseEntity<?> after(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //删除订单
    @RequestMapping(value ="/orders/delete",method = RequestMethod.POST)
    public ResponseEntity<?> delete(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");

    }
    //查找所有订单
    @RequestMapping(value ="/orders/selectall",method = RequestMethod.POST)
    public ResponseEntity<?> selectall(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok("cds");
    }
}
