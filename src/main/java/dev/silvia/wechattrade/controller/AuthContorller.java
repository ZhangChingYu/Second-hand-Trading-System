package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.personal.AddressDto;
import dev.silvia.wechattrade.dto.personal.AuthDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class AuthContorller {
    @Autowired
    @Resource
    private IAuthService service;

    private  Result redto;
    //根据id获取个人信息
    @RequestMapping(value ="/personal/acquisition",method = RequestMethod.GET)
    public ResponseEntity<?> acquisition(@RequestParam Integer id){
        Optional<Result> result = this.service.Acquisition(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        else{
            redto=new Result(ResultCode.FAIL);
            return ResponseEntity.ok(redto);
        }
    }

    //修改个人信息
    @RequestMapping(value ="/personal/edit",method = RequestMethod.POST)
    public ResponseEntity<?> personalinfo(@RequestBody User request) {
        return ResponseEntity.ok(service.PersonalInfo(request));
    }

    //实名认证
    @RequestMapping(value ="/personal/authentication",method = RequestMethod.POST)
    public ResponseEntity<?> authentication(@RequestBody AuthDto request) {
        return ResponseEntity.ok(service.authentication(request));
    }

    //获取地址
    @RequestMapping(value ="/address/get",method = RequestMethod.GET)
    public ResponseEntity<?> getaddress(@RequestParam Integer id) {
        redto=new Result<>(ResultCode.SUCCESS,service.getaddress(id));
        return ResponseEntity.ok(redto);
    }

    //新增地址
    @RequestMapping(value ="/address/add",method = RequestMethod.POST)
    public ResponseEntity<?> addaddress(@RequestBody AddressDto request) {
        return ResponseEntity.ok(service.address(request));
    }

    //修改地址
    @RequestMapping(value ="/address/edit",method = RequestMethod.POST)
    public ResponseEntity<?> editaddress(@RequestBody AddressDto request) {
        return ResponseEntity.ok(service.editaddress(request));
    }

    //删除地址
    @RequestMapping(value ="/address/delete",method = RequestMethod.POST)
    public ResponseEntity<?> deleteaddress(@RequestBody AddressDto request) {
        return ResponseEntity.ok(service.deleteaddress(request));
    }

    //默认地址
    @RequestMapping(value ="/address/default",method = RequestMethod.POST)
    public ResponseEntity<?> defaulteaddress(@RequestBody AddressDto request) {
        return ResponseEntity.ok(service.defaulteaddress(request));
    }
}
