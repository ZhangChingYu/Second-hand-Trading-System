package dev.silvia.wechattrade.controller;

import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {
    @Autowired
    private IChatService chatService;

    @RequestMapping(value ="/chat/checkIsFirstChat",method = RequestMethod.GET)
    public ResponseEntity<?> checkIsFirstChat(HttpSession httpSession,@RequestParam String toId){

        //获取httpSession中的user值

        User user = (User) httpSession.getAttribute("user");
        String fromUser = user.getPhone();
        System.out.println(fromUser+"和"+toId+"建立连接");
        return ResponseEntity.ok(chatService.isFirstChat(fromUser, toId));
    }

    @RequestMapping(value ="/chat/getChatList",method = RequestMethod.GET)
    public ResponseEntity<?> chatList(HttpSession httpSession) {
        //获取httpSession中的user值
        User user = (User) httpSession.getAttribute("user");
        String fromUser = user.getPhone();
        System.out.println(fromUser+"当前用户的聊天列表");
        //获取当前用户的聊天列表返回
        return ResponseEntity.ok(chatService.getFromUserChatList(fromUser));
    }

    @RequestMapping(value ="/chat/getChatRecords",method = RequestMethod.GET)
    public ResponseEntity<?> recentChatRecords( @RequestParam String toId, @RequestParam int startIndex, @RequestParam int pageSize, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        String fromUser = user.getPhone();
        System.out.println(fromUser+"和"+toId+"的最近的聊天记录");
        //获取最近的聊天记录
        return  ResponseEntity.ok(chatService.getRecentChatRecords(fromUser, toId, startIndex,pageSize));

    }

    @RequestMapping(value ="/chat/getPageNumber",method = RequestMethod.GET)
    public ResponseEntity<?> chatRecordsPageNumber(String number){
        return ResponseEntity.ok(chatService.getPageNumber(number));
    }

    @RequestMapping(value ="/chat/inWindows",method = RequestMethod.GET)
    public ResponseEntity<?> updateIsSaveWindows(@RequestParam String toId, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        String fromUser = user.getPhone();
        Result result=chatService.updateWindows(fromUser, toId);
        System.out.println(fromUser+"inWindows");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value ="/chat/unread",method = RequestMethod.GET)
    public ResponseEntity<?> unreadTotalNumber(HttpSession httpSession) {

        // 获取httpSession中的user值
        User user = (User) httpSession.getAttribute("user");
        String phone = user.getPhone();
        System.out.println(phone+"unread");
        return ResponseEntity.ok(chatService.getUnreadTotalNumber(phone));
    }

    @RequestMapping(value ="/chat/isLogin",method = RequestMethod.GET)
    public ResponseEntity<?> checkJsessionid(HttpSession session) {

        User user = (User) session.getAttribute("user");

        //检验是否存在user值来判断是否以及登录
        if (user != null) {
            //将已登录的用户的头像和用户名返回
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userPicture",  "/images/picture/picture1.jpg");
            resultMap.put("username", user.getPhone());
            System.out.println(user.getPhone()+"已登录");
            return ResponseEntity.ok(new Result("666","已登录！", resultMap));
        } else {
            return ResponseEntity.ok(Result.FAIL("请先登录"));
        }

    }
    @RequestMapping(value ="/chat/resetWindows",method = RequestMethod.GET)
    public ResponseEntity<?> resetWindows(HttpSession httpSession){

        // 获取httpSession中的user值
        User user = (User) httpSession.getAttribute("user");

        String phone = user.getPhone();
        chatService.resetWindows(phone);
        System.out.println(user.getPhone()+"重置成功");
        return ResponseEntity.ok(Result.SUCCESS("重置成功！"));
    }
}
