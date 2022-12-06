package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.address.AddressCreateDto;
import dev.silvia.wechattrade.dto.address.AddressUpdateDto;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.INotificationService;
import dev.silvia.wechattrade.service.IUserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class SettingController {
    @Autowired
    @Resource
    private IUserSettingService settingService;
    @Autowired
    @Resource
    private INotificationService notificationService;

    Gson gson = new Gson();
    private String h_catalog = null, h_question = null;

    @UserLoginToken
    @RequestMapping(value = "/default/address", method = RequestMethod.GET)
    public String getDefaultAddress(HttpServletRequest request){    // 獲取default address
        String phone = request.getParameter("phone");
        return gson.toJson(settingService.getDefaultAddress(phone));
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/addresses", method = RequestMethod.GET)
    public String showAllAddress(HttpServletRequest request){   // 顯示用戶所有地址
        String phone = request.getParameter("phone");
        return gson.toJson(settingService.showAllAddress(phone));
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/address", method = RequestMethod.POST)
    public Integer addAddress(@RequestBody AddressCreateDto addressCreateDto){  // 添加地址
        return settingService.addAddress(addressCreateDto);
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/address", method = RequestMethod.PUT)
    public Integer editingAddress(@RequestBody AddressUpdateDto addressUpdateDto){  // 編輯地址
        return settingService.addressEditing(addressUpdateDto);
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/default/address", method = RequestMethod.PUT)
    public Integer setAsDefaultAddress(@RequestBody Map<String, Object> param){   // 將某地址設為default
        String phone = param.get("phone").toString();
        Integer rank = Integer.parseInt(param.get("rank").toString());
        return settingService.setAsDefaultAddress(phone, rank);
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/address", method = RequestMethod.DELETE)
    public  Integer deleteAddress(@RequestBody Map<String, Object> param){    // 刪除某地址
        String phone = param.get("phone").toString();
        Integer rank = Integer.parseInt(param.get("rank").toString());
        return settingService.deleteAddress(phone,rank);
    }

    @PassToken
    @RequestMapping(value = "/setting/help", method = RequestMethod.GET)
    public List<String> getHelpCatalogs(){
        return settingService.getQuestionCatalog();
    }
    @PassToken
    @RequestMapping(value = "/setting/help/catalog", method = RequestMethod.GET)
    public List<String> getHelpQuestions(HttpServletRequest request){
        h_catalog = request.getParameter("catalog");
        return settingService.getQuestions(h_catalog);
    }
    @PassToken
    @RequestMapping(value = "/setting/help/catalog/question", method = RequestMethod.GET)
    public String getHelpAnswer(HttpServletRequest request){
        h_question = request.getParameter("question");
        return settingService.getAnswer(h_catalog, h_question);
    }

    @PassToken
    @RequestMapping(value = "setting/feedback", method = RequestMethod.POST)
    public Integer writeFeedback(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        String content = param.get("content").toString();
        return settingService.sendFeedback(phone, content);
    }

    @RequestMapping(value = "/setting/notifications", method = RequestMethod.GET)
    public String showAllNotification(HttpServletRequest request){
        String phone = request.getParameter("phone");
        return gson.toJson(notificationService.showAllNotification(phone));
    }

    @RequestMapping(value = "/setting/notification/isRead", method = RequestMethod.GET)
    public boolean checkNotificationRead(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        return notificationService.isRead(id);
    }

    @RequestMapping(value = "/setting/notification", method = RequestMethod.GET)
    public String readNotification(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        return gson.toJson(notificationService.readNotification(id));
    }
}
