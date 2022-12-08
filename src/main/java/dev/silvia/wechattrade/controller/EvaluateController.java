package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.product.EvaluateDto;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.IEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class EvaluateController {
    @Autowired
    @Resource
    private IEvaluateService service;

    private Gson gson = new Gson();

    @UserLoginToken
    @RequestMapping(value = "/evaluation", method = RequestMethod.POST)
    public Integer evaluateProduct(@RequestBody EvaluateDto dto){
        return service.evaluateProduct(dto);
    }

    @UserLoginToken
    @RequestMapping(value = "/evaluations", method = RequestMethod.GET)
    public String showEvaluateBySeller(HttpServletRequest request){
        String seller_phone = request.getParameter("sellerPhone");
        return gson.toJson(service.showAllEvaluate(seller_phone));
    }
}
