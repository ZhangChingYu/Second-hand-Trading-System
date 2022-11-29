package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.IProductUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @PassToken
    @RequestMapping(value = "/my/products", method = RequestMethod.GET)
    public String showAllMyProduct(HttpServletRequest request){
        String phone = request.getParameter("phone");
        return gson.toJson(service.showAllMyProduct(phone));
    }


}
