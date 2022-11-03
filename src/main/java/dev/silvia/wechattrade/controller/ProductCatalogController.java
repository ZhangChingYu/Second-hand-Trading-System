package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.entity.ProductCatalog;
import dev.silvia.wechattrade.service.IProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/catalog")
public class ProductCatalogController {
    @Autowired
    @Resource
    private IProductCatalogService service;

    Gson gson = new Gson();

    @RequestMapping(value = "/get")
    public String getById(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        return gson.toJson(service.getById(id));
    }

    @RequestMapping(value = "/getAll")
    public String getAll(){
        return gson.toJson(service.showAllCatalog());
    }

    @RequestMapping(value = "/addC")
    public Integer addCatalog(@RequestBody Map<String, Object> param){
        String name = param.get("name").toString();
        String number = param.get("number").toString();
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setName(name);
        productCatalog.setNumber(number);
        return service.addProductCatalog(productCatalog);
    }

    @RequestMapping(value = "/updateC")
    public Integer updateCatalog(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        String name = param.get("name").toString();
        String number = param.get("number").toString();
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setId(id);
        productCatalog.setName(name);
        productCatalog.setNumber(number);
        return service.updateProductCatalog(productCatalog);
    }

    @RequestMapping(value = "/deleteC")
    public Integer deleteCatalog(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        return service.deleteProductCatalog(id);
    }
}
