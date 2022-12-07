package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.service.IUserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserMangerController {
    @Autowired
    @Resource
    private IUserMangerService service;

    //新增反馈  /feedback/add
    @RequestMapping(value ="/feedback/add",method = RequestMethod.POST)
    public ResponseEntity<?> addFeedBack(@RequestBody Map<String, Object> param) {
        FeedbackDto feed=new FeedbackDto();
        feed.setPhone(param.get("phone").toString());
        feed.setContent(param.get("content").toString());
        return ResponseEntity.ok(service.addFeedBack(feed));
    }

    //删除反馈  /feedback/delete
    @RequestMapping(value ="/feedback/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFeedback(@RequestParam String number) {
        return ResponseEntity.ok(service.deleteFeedback(number));
    }

    //查找所有反馈  /feedback/all
    @RequestMapping(value ="/feedback/all",method = RequestMethod.GET)
    public ResponseEntity<?> selectFeedback() {
        return ResponseEntity.ok(service.selectFeedback());
    }

    //权限更改   违规用户-->普通 violations=1  普通-->违规用户 violations=0
    @RequestMapping(value ="/feedback/authority",method = RequestMethod.PUT)
    public ResponseEntity<?> swapAuthority(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        Integer violations = Integer.valueOf(request.getParameter("violations"));
        return ResponseEntity.ok(service.swapAuthority(phone,violations));
    }

    //查看所有有违规记录且暂未设置成违规的用户
    @RequestMapping(value ="/feedback/user",method = RequestMethod.GET)
    public ResponseEntity<?> userFeedback() {
        return ResponseEntity.ok(service.userFeedback());
    }

}
