package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.address.AddressCreateDto;
import dev.silvia.wechattrade.dto.address.AddressUpdateDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.handlers.fileHandlers.ReadFile;
import dev.silvia.wechattrade.service.IDemoService2;
import dev.silvia.wechattrade.service.IUserSettingService;
import dev.silvia.wechattrade.vo.AuthenticationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserCenterController {
    @Autowired
    @Resource
    private IUserSettingService service;

    Gson gson = new Gson();
    private String h_catalog = null, h_question = null;

    @Autowired
    private ReadFile readFile;

    @Autowired
    private IDemoService2 demo;

    private Result redto;
    //根据id获取个人信息
    @RequestMapping(value ="/setting/acquisition",method = RequestMethod.GET)
    public ResponseEntity<?> acquisition(@RequestParam Integer id){
        Optional<Result> result = this.service.Acquisition(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        else{
            redto=new Result(ResultCode.FAIL);
            return ResponseEntity.ok(redto);
        }
    }

    //修改个人信息
    @RequestMapping(value ="/setting/edit", method = RequestMethod.PUT)
    public ResponseEntity<?> personalinfo(@RequestBody User user){
        return ResponseEntity.ok(service.PersonalInfo(user));
    }

    //实名认证
    @RequestMapping(value ="/setting/authentication")
    @ResponseBody
    public ResponseEntity<?> authentication(HttpServletRequest request) throws ParseException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pictures");
        AuthenticationVo auth=new AuthenticationVo();
        auth.setPhone(params.getParameter("phone"));
        auth.setIdCardPics(files);
        auth.setRealName(params.getParameter("realName"));
        auth.setIdNumber(params.getParameter("idNumber"));
        return ResponseEntity.ok(service.authentication(auth));
    }

    //实名认证
    @RequestMapping(value ="/setting/swapRelatedAvatar")
    @ResponseBody
    public ResponseEntity<?> swapRelatedAvatar(HttpServletRequest request) throws ParseException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("avatar");
        String phone=params.getParameter("phone");
        Result res;
        if(demo.storeravatarPictures("Avatar",phone,files)==808){

            res=new Result(ResultCode.FAIL);
            ResponseEntity.ok(res);
        }
        List<String> pictures = readFile.getPictureBase64("Avatar",phone,files.size());
        String path="";
        for(int i=0;i<pictures.size();i++){
            path=path+"#"+pictures.get(i);
        }
        return ResponseEntity.ok(service.swapRelatedAvatar(phone,path));
    }

    @UserLoginToken
    @RequestMapping(value = "/default/address", method = RequestMethod.GET)
    public String getDefaultAddress(HttpServletRequest request){    // 獲取default address
        String phone = request.getParameter("phone");
        return gson.toJson(service.getDefaultAddress(phone));
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/addresses", method = RequestMethod.GET)
    public String showAllAddress(HttpServletRequest request){   // 顯示用戶所有地址
        String phone = request.getParameter("phone");
        return gson.toJson(service.showAllAddress(phone));
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/address", method = RequestMethod.POST)
    public Integer addAddress(@RequestBody AddressCreateDto addressCreateDto){  // 添加地址
        return service.addAddress(addressCreateDto);
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/address", method = RequestMethod.PUT)
    public Integer editingAddress(@RequestBody AddressUpdateDto addressUpdateDto){  // 編輯地址
        return service.addressEditing(addressUpdateDto);
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/default/address", method = RequestMethod.PUT)
    public Integer setAsDefaultAddress(@RequestBody Map<String, Object> param){   // 將某地址設為default
        String phone = param.get("phone").toString();
        Integer rank = Integer.parseInt(param.get("rank").toString());
        return service.setAsDefaultAddress(phone, rank);
    }
    @UserLoginToken
    @RequestMapping(value = "/setting/address", method = RequestMethod.DELETE)
    public  Integer deleteAddress(@RequestBody Map<String, Object> param){    // 刪除某地址
        String phone = param.get("phone").toString();
        Integer rank = Integer.parseInt(param.get("rank").toString());
        return service.deleteAddress(phone,rank);
    }

    @PassToken
    @RequestMapping(value = "/setting/help", method = RequestMethod.GET)
    public List<String> getHelpCatalogs(){
        return service.getQuestionCatalog();
    }
    @PassToken
    @RequestMapping(value = "/setting/help/catalog", method = RequestMethod.GET)
    public List<String> getHelpQuestions(HttpServletRequest request){
        h_catalog = request.getParameter("catalog");
        return service.getQuestions(h_catalog);
    }
    @PassToken
    @RequestMapping(value = "/setting/help/catalog/question", method = RequestMethod.GET)
    public String getHelpAnswer(HttpServletRequest request){
        h_question = request.getParameter("question");
        return service.getAnswer(h_catalog, h_question);
    }

    @PassToken
    @RequestMapping(value = "setting/feedback", method = RequestMethod.POST)
    public Integer writeFeedback(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        String content = param.get("content").toString();
        return service.sendFeedback(phone, content);
    }
}

