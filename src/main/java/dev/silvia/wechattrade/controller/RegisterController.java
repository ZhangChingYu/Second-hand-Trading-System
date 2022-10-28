package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.Param;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    @Resource
    private IRegisterService service;

    Gson gson = new Gson();

    @RequestMapping("/test")
    public int test_connection(){
        return 800;
    }

    @RequestMapping(value = "userInfo")
    public String userInfo(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        User user = service.getUserById(id);
        return gson.toJson(user);
    }

    @RequestMapping(value = "register")
    public Integer register(@RequestBody Map<String, Object> param){
        // 用戶輸入手機號&密碼即可完成註冊，email可選
        String phone = param.get("phone").toString();
        String password = param.get("password").toString();
        String email = param.get("email").toString();
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setEmail(email);
        return service.Register(user);
    }
}
