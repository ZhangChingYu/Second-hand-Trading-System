package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    @Resource
    private ILoginService service;

    Gson gson = new Gson();


    @RequestMapping(value = "/login")
    public String login(@RequestParam String phone,@RequestParam String password){
        User user = service.Login(phone,password);
        return gson.toJson(user);
    }

    @GetMapping(value = "/lost")
    public int lostpassword(@RequestParam String phone,@RequestParam String password){
        return service.LostPasswaod(phone,password);
    }
}
