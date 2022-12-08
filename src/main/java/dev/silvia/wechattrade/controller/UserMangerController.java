package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.service.IUserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class UserMangerController {
    @Autowired
    @Resource
    private IUserMangerService service;
    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    UserRepository userRepository;
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

    //权限更改    普通-->违规用户 violations=0
    @RequestMapping(value ="/manage/user/authority",method = RequestMethod.PUT)
    public ResponseEntity<?> swapAuthority(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(service.swapAuthority(ids,0));
    }
    //权限更改   违规用户-->普通 violations=1
    @RequestMapping(value ="/manage/user/violations",method = RequestMethod.PUT)
    public ResponseEntity<?> swapAuthorityTo(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(service.swapAuthority(ids,1));
    }

    //查看所有用户
    @RequestMapping(value ="/manage/user/all",method = RequestMethod.GET)
    public ResponseEntity<?> userManageAll() {
        return ResponseEntity.ok(service.userManageAll());
    }

    //根据权限、交易、违规筛选
    @RequestMapping(value ="/manage/user/select",method = RequestMethod.GET)
    public ResponseEntity<?> userManageSelect(HttpServletRequest request){
        String type=  request.getParameter("type");    //筛选类型(权限、交易、违规)  筛选权限时type为可买卖/已禁用
        Integer upper= Integer.valueOf(request.getParameter("upper"));   //违规、交易次数上限,
        Integer lower= Integer.valueOf(request.getParameter("lower"));   //违规、交易次数下限,
        return ResponseEntity.ok(service.userManageSelect(type, upper, lower));
    }

    //删除用户
    @RequestMapping(value ="/manage/user/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(service.deleteUser(ids));
    }

    //根据用户名或真实姓名模糊查询
    @RequestMapping(value ="/manage/user/name",method = RequestMethod.GET)
    public ResponseEntity<?> fuzzySelect(@RequestParam String name) {
        String name1=transferUTF8.CtoUTF8(name);
        return ResponseEntity.ok(service.fuzzySelect(name1));
    }

    //修改密码为123456
    @RequestMapping(value ="/manage/user/password",method = RequestMethod.PUT)
    public ResponseEntity<?> swapPassword(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(service.swapPassword(ids));
    }
    //测试
    @RequestMapping(value ="/manage/user/test",method = RequestMethod.GET)
    public ResponseEntity<?> test(@RequestParam Integer phone) {
        List<User> user=userRepository.findByAuthority(phone,Sort.by(Sort.Direction.DESC, "registerDate"));
        return ResponseEntity.ok(user);
    }
}
