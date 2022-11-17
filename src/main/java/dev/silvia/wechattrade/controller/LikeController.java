package dev.silvia.wechattrade.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.ILikeService;
import dev.silvia.wechattrade.vo.DeleteBatchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    @Resource
    private ILikeService service;

    Gson gson = new Gson();

    // 查看該商品是否已被收藏，True(以收藏)/False(未收藏)
    @UserLoginToken
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public boolean checkLikeHistory(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        String number = param.get("number").toString();
        return service.checkLike(phone, number);
    }
    // 添加/取消收藏
    @UserLoginToken
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public int PressLikeButton(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        String phone = param.get("phone").toString();
        return service.PressLikeButton(phone, number);
    }
    // 顯示所有收藏商品(默認排序:收藏日期新到舊)
    @UserLoginToken
    @RequestMapping(value = "/all/likes", method = RequestMethod.GET)
    public String showAllLikes(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        return gson.toJson(service.showAllLike(phone));
    }
    // 照切換不同的排序方式
    @PassToken
    @RequestMapping(value = "/all/likes/order", method = RequestMethod.GET)
    public String showLikeByOrder(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        Integer type = Integer.parseInt(param.get("type").toString());
        return gson.toJson(service.showLikeByOrder(phone, type));
    }
    // 依照商品分類顯示收藏商品
    @PassToken
    @RequestMapping(value = "/catalog/likes", method = RequestMethod.GET)
    public String showLikeByCatalog(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        String catalog = param.get("catalog").toString();
        return gson.toJson(service.showLikeByCatalog(phone, catalog));
    }
    // 批量取消收藏
    @UserLoginToken
    @RequestMapping(value = "/likes", method = RequestMethod.DELETE)
    public int deleteLikeBatches(@RequestBody DeleteBatchVo deleteBatchVo){
        String phone = deleteBatchVo.getPhone();
        List<String> numbers = deleteBatchVo.getNumbers();
        return service.deleteLikeBatches(phone, numbers);
    }
}
