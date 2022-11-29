package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userInfo(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        //Integer id = Integer.parseInt(param.get("id").toString());
        User user = service.getUserById(id);
        return gson.toJson(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Integer register(@RequestBody Map<String, Object> param){
        // 用戶輸入手機號&密碼即可完成註冊，email可選
        String phone = param.get("phone").toString();
        String password = param.get("password").toString();
        String email = param.get("email").toString();
        String userName = param.get("userName").toString();
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserName(userName);
        return service.Register(user);
    }
}
