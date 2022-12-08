package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.entity.WXAuth;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.service.ILoginService;
import dev.silvia.wechattrade.service.IWeixinService;
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
    @Autowired
    private UserRepository accountRepository;

    private Optional<User> user;

    @Autowired
    private static IWeixinService weixinService;

    private Result redto;


    private final String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private final String ph = "^[1][3578]\\d{9}$";

    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto user1=new LoginResponseDto();
        Optional<LoginResponseDto> user = this.service.login(request);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        else{
            user1.setMsg("用户不存在");//用户不存在
            user1.setCode("555");//用户不存在
            return ResponseEntity.ok(user1);
        }
    }

    //输入code获取 sessionId
    @RequestMapping(value ="/weixin/sessionId",method = RequestMethod.GET)
    public String getSessionId(@RequestParam String code){
       // String code = param.get("code").toString();
//        System.out.println("ssucceed");
//        System.out.println(code);
        return  weixinService.getSessionId(code);
    }

//   输入  {String encryptedData;String iv;String sessionId;}
//   返回LoginResponseDto{code: "666" （成功）;mag:  ;User:user}
    @RequestMapping(value ="/weixin/authLogin", method = RequestMethod.POST)
    public LoginResponseDto authLogin(@RequestBody WXAuth wxAuth) {
        LoginResponseDto result = weixinService.authLogin(wxAuth);
        return result;
    }

    @RequestMapping(value ="/lost",method = RequestMethod.POST)
    public ResponseEntity<?> lostpassword(@RequestBody LostPasswordDto request){
        if(Objects.equals(request.getCaptcha1(), request.getCaptcha())){
            return ResponseEntity.ok(service.lostPassward(request));
        }
        else{
            redto=new Result(ResultCode.AUTH_CODE_ERROR);
            return ResponseEntity.ok(redto);
        }
    }

    //获取验证码
    @RequestMapping(value ="/captcha",method = RequestMethod.GET)
    public ResponseEntity<?> captcha(@RequestParam String phone){
        String captcha;
        // 如果用户输入的用户名，格式符合邮箱，为邮箱登陆
        if (phone.matches(em)) {
            // 通过邮箱查询数据库用户
            user=accountRepository.findByEmail(phone);
        }
        else if (phone.matches(ph)){
            // 如果用户输入的用户名，格式符合手机号，为手机号登陆
            user=accountRepository.findByPhone(phone);
        }
        else{
            redto=new Result(ResultCode.PARAM_TYPE_BIND_ERROR);
            return ResponseEntity.ok(redto);
        }
        if(user.isPresent()){
            captcha=String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
            redto=new Result(ResultCode.SUCCESS,captcha);
        }
        else{
            redto=new Result(ResultCode.USER_NOT_EXIST);
        }
        return ResponseEntity.ok(redto);
    }
}
