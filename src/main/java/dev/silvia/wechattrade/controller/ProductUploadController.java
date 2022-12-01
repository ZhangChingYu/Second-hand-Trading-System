package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.product.ProductUpdateDto;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.IProductUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ProductUploadController {
    @Autowired
    @Resource
    private IProductUploadService service;

    @Autowired
    private Gson gson;

    @UserLoginToken
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public int productUploadRequest(ProductUploadDto productUploadDto){
        productUploadDto.setNumber(productUploadDto.getCatalog()+System.currentTimeMillis());
        return service.uploadProductRequest(productUploadDto);
    }

    @UserLoginToken
    @RequestMapping(value = "/my/products", method = RequestMethod.GET)
    public String showAllMyProduct(HttpServletRequest request){
        String phone = request.getParameter("phone");
        return gson.toJson(service.showAllMyProduct(phone));
    }

    @UserLoginToken
    @RequestMapping(value = "/my/products/key", method = RequestMethod.GET)
    public String showMyProductByKey(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String keyword = request.getParameter("keyword");;
        return gson.toJson(service.showByKey(phone,keyword));
    }

    @UserLoginToken
    @RequestMapping(value = "/my/products/catalog", method = RequestMethod.GET)
    public String showMyProductByCatalog(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String catalog = request.getParameter("catalog");
        return gson.toJson(service.showByCatalog(phone, catalog));
    }
    @UserLoginToken
    @RequestMapping(value = "/my/products/status", method = RequestMethod.GET)
    public String showMyProductByStatus(HttpServletRequest request){
        String phone = request.getParameter("phone");
        Integer status = Integer.parseInt(request.getParameter("status"));
        return gson.toJson(service.showByStatus(phone,status));
    }
    @UserLoginToken
    @RequestMapping(value = "/my/product/off", method = RequestMethod.PUT)
    public Integer offShelfMyProduct(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        return service.productOffShelf(number);
    }
    @UserLoginToken
    @RequestMapping(value = "/my/product", method = RequestMethod.DELETE)
    public Integer deleteMyProduct(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        return service.productDelete(number);
    }
    @UserLoginToken
    @RequestMapping(value = "/my/product", method = RequestMethod.PUT)
    public Integer updateMyProduct(@RequestBody ProductUpdateDto updateDto){
        return service.productUpdate(updateDto);
    }
}
