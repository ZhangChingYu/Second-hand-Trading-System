package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.service.ISystemManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class SystemManagerController {
    @Autowired
    @Resource
    private ISystemManageService service;

    Gson gson = new Gson();

    @RequestMapping(value = "/help/catalog", method = RequestMethod.POST)
    public Integer createHelpCatalog(@RequestBody Map<String, Object> param) {
        String name = param.get("name").toString();
        return service.createHelpCatalog(name);
    }

    @RequestMapping(value = "/help/help", method = RequestMethod.POST)
    public Integer createHelp(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        String question = param.get("question").toString();
        String answer = param.get("answer").toString();
        return service.createHelp(catalog, question, answer);
    }

    @RequestMapping(value = "/help/help", method = RequestMethod.DELETE)
    public Integer deleteHelp(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        String question = param.get("question").toString();
        return service.deleteHelp(catalog, question);
    }

    @RequestMapping(value = "/help/catalog", method = RequestMethod.DELETE)
    public Integer deleteHelpCatalog(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        return service.deleteHelpCatalog(catalog);
    }

    @RequestMapping(value = "/help/help", method = RequestMethod.PUT)
    public Integer updateHelp(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        String question = param.get("question").toString();
        String newAnswer = param.get("newAnswer").toString();
        return service.updateHelp(catalog, question, newAnswer);
    }

    @RequestMapping(value = "/help/catalog", method = RequestMethod.PUT)
    public Integer updateHelpCatalog(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        String newCatalog = param.get("newCatalog").toString();
        return service.updateHelpCatalog(catalog,newCatalog);
    }

    @RequestMapping(value = "/help/catalogs", method = RequestMethod.GET)
    public String showAllHelpCatalog(){
        return gson.toJson(service.showHelpCatalog());
    }

    @RequestMapping(value = "/help/catalog/questions", method = RequestMethod.GET)
    public String showAllHelpQuestion(HttpServletRequest request){
        String catalog = request.getParameter("catalog");
        return gson.toJson(service.showHelpQuestion(catalog));
    }

    @RequestMapping(value = "/help/catalog/question/answer", method = RequestMethod.GET)
    public String showHelpAnswer(HttpServletRequest request){
        String catalog = request.getParameter("catalog");
        String question = request.getParameter("question");
        return gson.toJson(service.showHelpAnswer(catalog, question));
    }

    @RequestMapping(value = "/principle", method = RequestMethod.POST)
    public Integer createUserPrinciple(@RequestBody Map<String, Object> param){
        String version = param.get("version").toString();
        String content = param.get("content").toString();
        return service.createUserPrinciple(version, content);
    }

    @RequestMapping(value = "/principle", method = RequestMethod.DELETE)
    public Integer deleteUserPrinciple(@RequestBody Map<String, Object> param){
        String version = param.get("version").toString();
        return service.deleteUserPrinciple(version);
    }

    @RequestMapping(value = "/principle", method = RequestMethod.PUT)
    public Integer updateUserPrinciple(@RequestBody Map<String, Object> param){
        String version = param.get("version").toString();
        String content = param.get("content").toString();
        return service.updateUserPrinciple(version, content);
    }

    @RequestMapping(value = "/principle", method = RequestMethod.GET)
    public String showPrinciple(HttpServletRequest request){
        String version = request.getParameter("version");
        return gson.toJson(service.showPrinciple(version));
    }

    @RequestMapping(value = "/principles", method = RequestMethod.GET)
    public String showAllPrinciple(){
        return gson.toJson(service.showAllPrinciple());
    }

    @PassToken
    @RequestMapping(value = "/principle/new", method = RequestMethod.GET)
    public String showNewestPrinciple(){
        return gson.toJson(service.showNewestPrinciple());
    }
}
