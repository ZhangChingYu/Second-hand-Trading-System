package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.service.IUserManageService;
import org.hibernate.annotations.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class UserManageController {
    @Autowired
    @Source
    private IUserManageService service;

    Gson gson = new Gson();

    @RequestMapping(value = "/authentication/requests", method = RequestMethod.GET)
    public String showAllAuthRequest(){
        return gson.toJson(service.showAllAuthRequest());
    }
    @RequestMapping(value = "/authentication/requests/status", method = RequestMethod.GET)
    public String showAuthRequestByStatus(HttpServletRequest request){
        Integer status = Integer.parseInt(request.getParameter("status"));
        return gson.toJson(service.showAuthRequestByStatus(status));
    }
    @RequestMapping(value = "/authentication/request", method = RequestMethod.GET)
    public String readAuthRequest(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        return gson.toJson(service.readAuthRequest(id));
    }
    @RequestMapping(value = "/authentication/request", method = RequestMethod.PUT)
    public Integer processAuthRequest(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        String decision = param.get("decision").toString();
        String explain = param.get("explain").toString();
        return service.processAuthRequest(id, decision, explain);
    }
}
