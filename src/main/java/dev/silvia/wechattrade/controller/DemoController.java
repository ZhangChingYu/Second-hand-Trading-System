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

    // 將UTF8數據進行解碼在發送給前端
    @RequestMapping(value = "/showUI")
    public String dataShowUI(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        User user = service.dataShowUI(id);
        return gson.toJson(user);
    }

    // 將數據庫中可能含有中文的數據都轉換為UTF-8編碼
    // (user_name, real_name, default_addr, addr_one, addr_two, addr_three)
    @RequestMapping(value = "/data")
    public String dataTransUTF8(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        User user = service.dataTransUTF8(id);
        return gson.toJson(user);
    }

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
