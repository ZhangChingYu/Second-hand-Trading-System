package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class ProductSearchController {
    @Autowired
    @Resource
    private IProductService service;

    Gson gson = new Gson();

    // 通過商品編號查詢商品詳細信息
    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    public String getProductDetail(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        return gson.toJson(service.getProductDetail(number));
    }

    // 分類查找
    @RequestMapping(value = "/catalog/products", method = RequestMethod.GET)
    public String getProductByCatalog(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        return gson.toJson(service.getProductByCatalog(catalog));
    }

    // 關鍵字查詢商品
    @RequestMapping(value = "/search/products", method = RequestMethod.GET)
    public String getProductByKeyword(@RequestBody Map<String, Object> param){
        String keyword = param.get("keyword").toString();
        return gson.toJson(service.searchProductByKey(keyword));
    }

    // 首頁商品推送(暫時)
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepageProduct(){
        return gson.toJson(service.homepageProductPromote());
    }
}
