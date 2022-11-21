package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ProductSearchController {
    @Autowired
    @Resource
    private IProductService service;

    Gson gson = new Gson();

    // 通過商品編號查詢商品詳細信息
    @UserLoginToken
    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    public String getProductDetail(HttpServletRequest request){
        String number = request.getParameter("number");
        return gson.toJson(service.getProductDetail(number));
    }

    // 分類查找
    @UserLoginToken
    @RequestMapping(value = "/catalog/products", method = RequestMethod.GET)
    public String getProductByCatalog(HttpServletRequest request){
        String catalog = request.getParameter("catalog");
        return gson.toJson(service.getProductByCatalog(catalog));
    }

    // 關鍵字查詢商品
    @UserLoginToken
    @RequestMapping(value = "/search/products", method = RequestMethod.GET)
    public String getProductByKeyword(HttpServletRequest request){
        String keyword = request.getParameter("keyword");
        return gson.toJson(service.searchProductByKey(keyword));
    }

    // 首頁商品推送(暫時)
    @UserLoginToken
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepageProduct(){
        return gson.toJson(service.homepageProducts());
    }

    // 最新商品(top 10)
    @PassToken
    @RequestMapping(value = "/homepage/new/products", method = RequestMethod.GET)
    public String homepageProductNew(){
        return gson.toJson(service.homepageProductNew());
    }

    // 最多商品收藏(top 10)
    @PassToken
    @RequestMapping(value = "/homepage/like/products", method = RequestMethod.GET)
    public String homepageProductsLike(){
        return gson.toJson(service.homepageProductLike());
    }

    // 根據用戶數據推薦的商品(top 10)
    @UserLoginToken
    @RequestMapping(value = "/homepage/promote/products", method = RequestMethod.GET)
    public String homepageProductsPromote(HttpServletRequest request){
        String number = request.getParameter("number");
        return gson.toJson(service.homepageProductPromote(number));
    }
}
