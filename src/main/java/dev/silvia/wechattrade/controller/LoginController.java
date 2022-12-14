package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.entity.WXAuth;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.picSize.PicUtil;
import dev.silvia.wechattrade.service.ILoginService;
import dev.silvia.wechattrade.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
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

    //根据手机号查询头像
    @RequestMapping(value ="/user/avatar",method = RequestMethod.GET)
    public ResponseEntity<?> selectAvatar(HttpServletRequest request){
        String phone= request.getParameter("phone");    //权限
        return ResponseEntity.ok(service.selectAvatar(phone));
    }
    //根据手机号查询认证图片
    @RequestMapping(value ="/user/auth",method = RequestMethod.GET)
    public ResponseEntity<?> selectAuth(HttpServletRequest request){
        String phone= request.getParameter("phone");    //权限
        return ResponseEntity.ok(service.selectAuth(phone));
    }
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request1, HttpSession session) {
        LoginResponseDto user1=new LoginResponseDto();
        Optional<LoginResponseDto> user = this.service.login(request1);
        if (user.isPresent()) {
            session.setAttribute("user", user.get().getUser());
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
    public LoginResponseDto authLogin(@RequestBody WXAuth wxAuth, HttpSession session) {
    LoginResponseDto result = weixinService.authLogin(wxAuth);
    if(Objects.equals(result.getCode(), "666")){
        session.setAttribute("user", result.getUser());
    }
    return result;
}

    @RequestMapping(value ="/lost",method = RequestMethod.POST)
    public ResponseEntity<?> lostPassword(@RequestBody LostPasswordDto request){
        if(Objects.equals(request.getCaptcha1(), request.getCaptcha())){
            return ResponseEntity.ok(service.lostPassword(request));
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

    @RequestMapping(value ="/outLogin",method = RequestMethod.PUT)
    public ResponseEntity<?> outLogin(@RequestParam String phone) {
        //修改用户的登录状态
        Result res=service.outLogin(phone);

        return ResponseEntity.ok(res);
    }
    @RequestMapping(value ="/picture",method = RequestMethod.POST)
    public ResponseEntity<?> outLogin1(@RequestBody  String base64) {
        //修改用户的登录状态
        if(PicUtil.GenerateImage(base64,"E:\\ab.jpg")){
            return ResponseEntity.ok("ok");
        }
        else
            return ResponseEntity.ok("false");
    }
}
