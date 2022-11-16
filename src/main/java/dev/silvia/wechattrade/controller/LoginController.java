package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class LoginController {
    @Autowired
    @Resource
    private ILoginService service;

    private static  String captcha = "123456";
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto user1=new LoginResponseDto();
        Optional<LoginResponseDto> user = this.service.login(request.getPhone(), request.getPassword(),request.getAuthority());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        else{
            user1.setMsg("555");//用户不存在
            return ResponseEntity.ok(user1);
        }
    }

    @UserLoginToken
    @RequestMapping(value ="/lost",method = RequestMethod.POST)
    public int lostpassword(@RequestBody LostPasswordDto request){
        if(Objects.equals(captcha, request.getCaptcha())){
            return service.lostPassward(request.getPhone(),request.getPassword());
        }
        else{
            return 3;//验证码错误
        }
    }

    //获取验证码
    @RequestMapping(value ="/captcha",method = RequestMethod.GET)
    public String captcha(){
        captcha=String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        return captcha;
    }
}
