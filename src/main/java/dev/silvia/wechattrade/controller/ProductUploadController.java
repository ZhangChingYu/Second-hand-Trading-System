package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.product.ProductUploadDto;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IProductUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProductUploadController {
    @Autowired
    @Resource
    private IProductUploadService service;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public int productUploadRequest(ProductUploadDto productUploadDto){
        productUploadDto.setNumber(productUploadDto.getCatalog()+System.currentTimeMillis());
        return service.uploadProductRequest(productUploadDto);
    }
}
