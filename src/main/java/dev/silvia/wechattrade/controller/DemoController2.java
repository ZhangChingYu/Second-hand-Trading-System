package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.service.IDemoService2;
import dev.silvia.wechattrade.vo.ProductVo;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import net.sf.jsqlparser.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController2 {
    @Autowired
    @Resource
    private IDemoService2 service;

    Gson gson = new Gson();

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImagesTest(HttpServletRequest request) throws ParseException{
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("images");
        String test = params.getParameter("test");
        ProductVo productVo = new ProductVo();
        productVo.setName(test);
        productVo.setPicture(files);
        return service.imagesUploadDemo(productVo);
        //return test+": "+files.get(0).getOriginalFilename();
    }

    @RequestMapping(value = "/create/product")
    @ResponseBody
    public int createProductDemo(HttpServletRequest request) throws ParseException{
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pictures");
        ProductVo productVo = new ProductVo();
        productVo.setName(params.getParameter("name"));
        productVo.setS_phone(params.getParameter("phone"));
        // 用商品分類編號+當前時間，作為每個商品的唯一編號
        productVo.setNumber(params.getParameter("catalog")+System.currentTimeMillis());
        productVo.setStorage(Integer.parseInt(params.getParameter("storage")));
        productVo.setPicture(files);
        productVo.setIntro(params.getParameter("intro"));
        productVo.setPrice(Double.parseDouble(params.getParameter("price")));
        productVo.setCatalog(params.getParameter("catalog"));
        productVo.setAddress(params.getParameter("address"));
        return service.createProductDemo(productVo);
    }

    @RequestMapping(value = "/picture/base64")
    public String showBase64Pic(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        String picture = service.imageBase64Demo(number);
        ProductOutlineVo productOutline = new ProductOutlineVo();
        productOutline.setCoverPic(picture);
        productOutline.setNumber(number);
        productOutline.setPrice(1.00);
        return gson.toJson(productOutline);
    }
}
