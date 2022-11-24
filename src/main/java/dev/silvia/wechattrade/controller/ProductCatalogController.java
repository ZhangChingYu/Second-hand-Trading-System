package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.entity.ProductCatalog;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/catalog")
public class ProductCatalogController {
    @Autowired
    @Resource
    private ICatalogService service;

    Gson gson = new Gson();

    // 根據分類id顯示對應的分類(測試用)
    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String getById(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        return gson.toJson(service.getById(id));
    }
    // 顯示所有分類，一個分類的結構為{"id":int, "number":"分類編碼", "name":"分類名稱"}
    @PassToken
    @RequestMapping(value = "/catalogs", method = RequestMethod.GET)
    public String getAll(){
        return gson.toJson(service.showAllCatalog());
    }


    // 添加分類，請求報文body的json格式為{"name":"分類名稱", "number":"分類編碼"}
    @RequestMapping(value = "/catalog", method = RequestMethod.POST)
    public Integer addCatalog(@RequestBody Map<String, Object> param){
        String name = param.get("name").toString();
        String number = param.get("number").toString();
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setName(name);
        productCatalog.setNumber(number);
        return service.addProductCatalog(productCatalog);
    }
    // 更新分類，請求報文body的json格式為{"id":int, "name":"分類名稱", "number":"分類編碼"}
    @RequestMapping(value = "/catalog", method = RequestMethod.PUT)
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
    // 刪除分類，請求報文body的json格式為{"id":int}
    @RequestMapping(value = "/catalog", method = RequestMethod.DELETE)
    public Integer deleteCatalog(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        return service.deleteProductCatalog(id);
    }
}
