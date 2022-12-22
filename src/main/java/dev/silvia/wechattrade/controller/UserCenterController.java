package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.handlers.fileHandler.WriteFile;
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
    private WriteFile writeFile;

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
    public ResponseEntity<?> personalInfo(@RequestBody User user){
        return ResponseEntity.ok(service.PersonalInfo(user));
    }
   /**
    //实名认证
    @RequestMapping(value ="/setting/authentication")
    @ResponseBody
    public ResponseEntity<?> authentication(HttpServletRequest request) throws ParseException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile files = ((MultipartHttpServletRequest) request).getFile("pictures");
        AuthenticationVo auth=new AuthenticationVo();
        auth.setPhone(params.getParameter("phone"));
        auth.setIdCardPic(files);
        auth.setRealName(params.getParameter("realName"));
        auth.setIdNumber(params.getParameter("idNumber"));
        return ResponseEntity.ok(service.authentication(auth));
    }
*/
    //修改头像
    @RequestMapping(value ="/setting/swapRelatedAvatar")
    @ResponseBody
    public ResponseEntity<?> swapRelatedAvatar(HttpServletRequest request) throws ParseException {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile files = ((MultipartHttpServletRequest) request).getFile("avatar");
        String phone=params.getParameter("phone");
        Result res;
        if(writeFile.storeAvatarPicture(phone,files)==808){
            res=new Result(ResultCode.FAIL);
            ResponseEntity.ok(res);
        }
        String pictures = readFile.getAvatarPicture(phone);
        String path=pictures;
        return ResponseEntity.ok(service.swapRelatedAvatar(phone,path));
    }


}

