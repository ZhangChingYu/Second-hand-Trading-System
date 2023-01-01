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
    public ResponseEntity<?> checkIsFirstChat(@RequestParam String fromId,@RequestParam String toId){

        //获取httpSession中的user值
        System.out.println(fromId+"和"+toId+"建立连接");
        return ResponseEntity.ok(chatService.isFirstChat(fromId, toId));
    }

    @RequestMapping(value ="/chat/getChatList",method = RequestMethod.GET)
    public ResponseEntity<?> chatList(@RequestParam String fromId) {
        //获取httpSession中的user值
        System.out.println(fromId+"当前用户的聊天列表");
        //获取当前用户的聊天列表返回
        return ResponseEntity.ok(chatService.getFromUserChatList(fromId));
    }

    @RequestMapping(value ="/chat/getChatRecords",method = RequestMethod.GET)
    public ResponseEntity<?> recentChatRecords(@RequestParam String fromId, @RequestParam String toId, @RequestParam int startIndex, @RequestParam int pageSize){
        System.out.println(fromId+"和"+toId+"的最近的聊天记录");
        //获取最近的聊天记录
        return  ResponseEntity.ok(chatService.getRecentChatRecords(fromId, toId, startIndex,pageSize));

    }

    @RequestMapping(value ="/chat/getPageNumber",method = RequestMethod.GET)
    public ResponseEntity<?> chatRecordsPageNumber(String number){
        return ResponseEntity.ok(chatService.getPageNumber(number));
    }

    @RequestMapping(value ="/chat/inWindows",method = RequestMethod.GET)
    public ResponseEntity<?> updateIsSaveWindows(@RequestParam String fromId,@RequestParam String toId) {
        Result result=chatService.updateWindows(fromId, toId);
        System.out.println(fromId+"inWindows");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value ="/chat/unread",method = RequestMethod.GET)
    public ResponseEntity<?> unreadTotalNumber(@RequestParam String fromId) {

        // 获取httpSession中的user值
        System.out.println(fromId+"unread");
        return ResponseEntity.ok(chatService.getUnreadTotalNumber(fromId));
    }

    @RequestMapping(value ="/chat/isLogin",method = RequestMethod.GET)
    public ResponseEntity<?> checkJsessionid(@RequestParam String fromId) {

        //检验是否存在user值来判断是否以及登录
        if (fromId != null) {
            //将已登录的用户的头像和用户名返回
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userPicture",  "/images/picture/picture1.jpg");
            resultMap.put("username", fromId);
            System.out.println(fromId+"已登录");
            return ResponseEntity.ok(new Result("666","已登录！", resultMap));
        } else {
            return ResponseEntity.ok(Result.FAIL("请先登录"));
        }

    }
    @RequestMapping(value ="/chat/resetWindows",method = RequestMethod.GET)
    public ResponseEntity<?> resetWindows(@RequestParam String fromId){

        // 获取httpSession中的user值

        chatService.resetWindows(fromId);
        System.out.println(fromId+"重置成功");
        return ResponseEntity.ok(Result.SUCCESS("重置成功！"));
    }
}
