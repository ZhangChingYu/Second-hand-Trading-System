package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @Autowired
    @Resource
    private IDemoService service;


    Gson gson = new Gson();

    // 測試轉換成UTF-8的字串是否能正常存入數據庫中
    @RequestMapping(value = "/test")
    public String chineseOutput(@RequestBody Map<String, Object> param){
        String user_name = param.get("user_name").toString();
        User user = service.chineseOutput(user_name);
        String name = service.testUTF8toC(user.getUserName());
        return gson.toJson(user)+name;
    }

    // 測試中文字串轉換成UTF-8字串
    @RequestMapping("/CtoU")
    public String ChineseToUTF8(@RequestBody Map<String, Object> param){
        String user_name = param.get("user_name").toString();
        return service.testCtoUTF8(user_name);
    }

    // 測試UTF-8字串轉換成中文字串
    @RequestMapping(value = "/UtoC")
    public String UTF8ToChinese(@RequestBody Map<String, Object> param){
        String user_name = param.get("user_name").toString();
        String utf8 = service.testCtoUTF8(user_name);
        return service.testUTF8toC(utf8);
    }

}
