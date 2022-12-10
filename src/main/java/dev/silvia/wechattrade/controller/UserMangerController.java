package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.dto.feedback.ViolationDto;
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

    //权限更改    普通-->违规用户 number=0  违规用户-->普通 number=1
    @RequestMapping(value ="/manage/user/authority",method = RequestMethod.PUT)
    public ResponseEntity<?> swapAuthority(@RequestBody ViolationDto numbers) {
        return ResponseEntity.ok(service.swapAuthority(numbers.getIds(),numbers.getNumber()));
    }

    //查看所有用户
    @RequestMapping(value ="/manage/user/all",method = RequestMethod.GET)
    public ResponseEntity<?> userManageAll() {
        return ResponseEntity.ok(service.userManageAll());
    }

    //根据权限、交易、违规筛选
    @RequestMapping(value ="/manage/user/select",method = RequestMethod.GET)
    public ResponseEntity<?> userManageSelect(HttpServletRequest request){
        Integer number1= Integer.valueOf(request.getParameter("number1"));    //权限
        Integer number2= Integer.valueOf(request.getParameter("number2"));   //违规
        Integer number3= Integer.valueOf(request.getParameter("number3"));   //交易
        return ResponseEntity.ok(service.userManageSelect(number1, number2, number3));
    }

    //删除用户
    @RequestMapping(value ="/manage/user/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(service.deleteUser(numbers));
    }

    //根据用户名或真实姓名模糊查询
    @RequestMapping(value ="/manage/user/name",method = RequestMethod.GET)
    public ResponseEntity<?> fuzzySelect(@RequestParam String name) {
        String name1=transferUTF8.CtoUTF8(name);
        return ResponseEntity.ok(service.fuzzySelect(name1));
    }

    //修改密码为123456
    @RequestMapping(value ="/manage/user/password",method = RequestMethod.PUT)
    public ResponseEntity<?> swapPassword(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(service.swapPassword(numbers));
    }

    //根据手机号查询个人信息
    @RequestMapping(value ="/manage/user",method = RequestMethod.GET)
    public ResponseEntity<?> selectUser(HttpServletRequest request){
        String phone= request.getParameter("phone");    //权限
        return ResponseEntity.ok(service.selectUser(phone));
    }



    //获取权限目录
    @RequestMapping(value ="/manage/select/authority",method = RequestMethod.GET)
    public ResponseEntity<?> selectAuth() {
        return ResponseEntity.ok(service.selectAuth());
    }

    //获取交易目录
    @RequestMapping(value ="/manage/select/trade",method = RequestMethod.GET)
    public ResponseEntity<?> selectTrade() {
        return ResponseEntity.ok(service.selectTrade());
    }

    //获取违规目录
    @RequestMapping(value ="/manage/select/violation",method = RequestMethod.GET)
    public ResponseEntity<?> selectViolation() {
        return ResponseEntity.ok(service.selectViolation());
    }


    //测试
    @RequestMapping(value ="/manage/user/test",method = RequestMethod.GET)
    public ResponseEntity<?> test(@RequestParam String phone) {
        String s = " ";
//        User user=new User();
//        user.setPhone("1512344712");
//        user.setPassword("1321323");
//        user.setAuthority(1);
//        user.setRegisterDate(new Date());
//        user.setId(19);
//        userRepository.save(user);
        //List<User> user=userRepository.findByAuthority(phone,Sort.by(Sort.Direction.DESC, "registerDate"));
        return ResponseEntity.ok(s);
    }
}
