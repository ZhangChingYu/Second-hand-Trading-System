package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class OrdersSearchController {
    @Autowired
    @Resource
    private IOrderService service;

    //退款原因   输入订单编号number
    @RequestMapping(value ="/orders/after/reason",method = RequestMethod.GET)
    public ResponseEntity<?> afterReason(HttpServletRequest request){
        String number = request.getParameter("number");
        return ResponseEntity.ok(service.afterReason(number));
    }

    //查找全部buyer预约
    @RequestMapping(value ="/booking/select/buyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectAllBuyer(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("state");
        return ResponseEntity.ok(service.selectAllBuyer(phone,status));
    }

    //查找全部seller预约
    @RequestMapping(value ="/booking/select/seller",method = RequestMethod.GET)
    public ResponseEntity<?> selectAllSeller(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("state");
        return ResponseEntity.ok(service.selectAllSeller(phone,status));
    }

    //buyer查找所有订单
    @RequestMapping(value ="/orders/select/buyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectBuyerOrder(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("state");
        return ResponseEntity.ok(service.selectBuyerOrder(phone,status));
    }

    //seller查找所有订单
    @RequestMapping(value ="/orders/select/seller",method = RequestMethod.GET)
    public ResponseEntity<?> selectSellerOrder(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("state");
        return ResponseEntity.ok(service.selectSellerOrder(phone,status));
    }

    //查找所有订单
    @RequestMapping(value ="/orders/select/all",method = RequestMethod.GET)
    public ResponseEntity<?> selectAllOrder(HttpServletRequest request){
        String status= request.getParameter("state");
        return ResponseEntity.ok(service.selectAllOrder(status));
    }


    //查找所有预约
    @RequestMapping(value ="/booking/select/all",method = RequestMethod.GET)
    public ResponseEntity<?> selectAll(HttpServletRequest request){
        String status= request.getParameter("state");
        return ResponseEntity.ok(service.selectAll(status));
    }

    //卖方根据商品编号查询预约
    @RequestMapping(value ="/booking/select/bookings",method = RequestMethod.GET)
    public ResponseEntity<?> sellerBookingByName(HttpServletRequest request){
        String number= request.getParameter("number");
        String state= request.getParameter("state");
        return ResponseEntity.ok(service.sellerBookingByName(number,state));
    }

    //在订单中根据商品名称模糊搜索
    @RequestMapping(value ="/orders/fuzzy/name",method = RequestMethod.GET)
    public ResponseEntity<?> selectOrdersByName(HttpServletRequest request){
        String name= request.getParameter("name");
        String phone= request.getParameter("phone");
        Integer isbuyer= Integer.valueOf(request.getParameter("isbuyer"));
        return ResponseEntity.ok(service.selectAllByName(name,phone,1,isbuyer));
    }

    //在预约中根据商品名称模糊搜索
    @RequestMapping(value ="/booking/fuzzy/name",method = RequestMethod.GET)
    public ResponseEntity<?> selectBookingByName(HttpServletRequest request){
        String name= request.getParameter("name");
        String phone= request.getParameter("phone");
        Integer isbuyer= Integer.valueOf(request.getParameter("isbuyer"));
        return ResponseEntity.ok(service.selectAllByName(name,phone,0,isbuyer));
    }

    //根据订单编号获取订单详情
    @RequestMapping(value ="/orders/details",method = RequestMethod.GET)
    public ResponseEntity<?> selectOrdersDetails(HttpServletRequest request){
        String number= request.getParameter("number");
        return ResponseEntity.ok(service.allAllDetails(number));
    }

    //卖方根据买方手机号和商品编号在预约信息里查找
    @RequestMapping(value ="/booking/select/phone",method = RequestMethod.GET)
    public ResponseEntity<?> bookingByPhone(HttpServletRequest request){
        String phone= request.getParameter("phone");
        String number= request.getParameter("number");
        return ResponseEntity.ok(service.bookingByPhone(phone,number));
    }

    //买方根据买方手机号和商品编号在预约信息里查找
    @RequestMapping(value ="/booking/buyer/select",method = RequestMethod.GET)
    public ResponseEntity<?> bookingByBuyer(HttpServletRequest request){
        String phone= request.getParameter("phone");
        String number= request.getParameter("number");
        return ResponseEntity.ok(service.bookingByBuyer(phone,number));
    }

    //买方根据买方手机号和商品编号判断该商品是否已预约
    @RequestMapping(value ="/booking/buyer/judgment",method = RequestMethod.GET)
    public ResponseEntity<?> bookingBuyer(HttpServletRequest request){
        String phone= request.getParameter("phone");
        String number= request.getParameter("number");
        return ResponseEntity.ok(service.bookingBuyer(phone,number));
    }

    //买方根据买方手机号和商品编号判断该商品是否已购买,以及购买次数
    @RequestMapping(value ="/orders/buyer/judgment",method = RequestMethod.GET)
    public ResponseEntity<?> orderByBuyer(HttpServletRequest request){
        String phone= request.getParameter("phone");
        String number= request.getParameter("number");
        return ResponseEntity.ok(service.orderByBuyer(phone,number));
    }

    //根据商品编号获取商品图片
    @RequestMapping(value ="/orders/product/pic",method = RequestMethod.GET)
    public ResponseEntity<?> orderProductPic(HttpServletRequest request){
        String number= request.getParameter("number");
        return ResponseEntity.ok(service.orderProductPic(number));
    }
}
