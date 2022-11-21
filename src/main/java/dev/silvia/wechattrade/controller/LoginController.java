package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.dto.response.ResponseDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Optional<User> user;

    private static  String captcha = "404";

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

    @RequestMapping(value ="/lost",method = RequestMethod.POST)
    public ResponseEntity<?> lostpassword(@RequestBody LostPasswordDto request){
        ResponseDto redto=new ResponseDto();
        if(Objects.equals(captcha, request.getCaptcha())){
            return ResponseEntity.ok(service.lostPassward(request));
        }
        else{
            redto.setCode("555");
            redto.setMsg("验证码错误");
            return ResponseEntity.ok(redto);
        }
    }

    //获取验证码

    @RequestMapping(value ="/captcha",method = RequestMethod.GET)
    public ResponseEntity<?> captcha(@RequestParam String phone){
        ResponseDto redto=new ResponseDto();
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
            redto.setCode("555");
            redto.setMsg("手机号或邮箱格式错误");
            return ResponseEntity.ok(redto);
        }
        if(user.isPresent()){
            captcha=String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
            redto.setCode(captcha);
            redto.setMsg("验证码发送成功");
        }
        else{
            redto.setCode("444");
            redto.setMsg("用户不存在");
        }
        return ResponseEntity.ok(redto);
    }
}
