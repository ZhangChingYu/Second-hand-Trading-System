package dev.silvia.wechattrade.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.silvia.wechattrade.entity.ChatMessage;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.config.WebSocketConfig;
import dev.silvia.wechattrade.service.IChatService;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
@ServerEndpoint(value = "/websocket",configurator= WebSocketConfig.class)
@Component
@SuppressWarnings("all")
public class MyWebSocket {

    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    //用来记录username和该session进行绑定
    private static Map<String, Session> map = new HashMap<String, Session>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //用户名--电话
    private String username;
    //获取全局容器
    private ApplicationContext applicationContext;
    //聊天逻辑层service
    private IChatService chatService;

    private JdbcTemplate jdbcTemplate;


    //连接建立成功调用的方法，初始化昵称、session
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        //获取登录时存放httpSession的用户数据
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());

        User user = (User) httpSession.getAttribute("user");

        //初始化数据
        this.applicationContext = applicationContext;
        this.session = session;
        this.username = user.getPhone();
        this.chatService = (IChatService) applicationContext.getBean("chatService");

        //绑定username与session
        map.put(username, session);

        //加入set中
        webSocketSet.add(this);
    }

   //连接关闭调用的方法
    @OnClose
    public void onClose() {

        //将当前的session删除
        webSocketSet.remove(this);

    }

    // 收到客户端消息后调用的方法
    // message 客户端发送过来的消息
    @OnMessage
    public void onMessage(String message) {

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为chatMsg对象，
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage chatMsg;

        try {
            chatMsg = objectMapper.readValue(message, ChatMessage.class);
            System.out.println(message);
            //对chatMsg进行装箱
            chatMsg.setFromId(username);
            chatMsg.setSendTime(new Date());
            chatMsg.setIsLatest(1);
            chatMsg.setIsDelete(0);

            Session fromSession = map.get(chatMsg.getFromId());
            Session toSession = map.get(chatMsg.getToId());


            //声明一个map，封装直接发送信息数据返回前端
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("fromId", username);
            resultMap.put("content", chatMsg.getContent());
            resultMap.put("time", chatMsg.getSendTime());
            resultMap.put("types", chatMsg.getTypes());

            JSONObject json = new JSONObject(resultMap);

            //发送给接收者.
            if(chatMsg.getTypes()==0||chatMsg.getTypes().toString()=="0"){
                fromSession.getAsyncRemote().sendText(json.toString());
            }
            // 1.判断接收方的toSession是否为null
            // 2.判断在聊天页面 ==> 直接发送 其他都是存储在数据库中
//             String sql;
//            //更新点击了聊天框的同一窗口值
//            sql=    "select from_window + to_window from chat_list where link_id = '"
//                    + chatMsg.getLinkId() +"' and from_user = '"
//                    + chatMsg.getFromUser()+"' and to_user = '"+chatMsg.getToUser()+"'";
//            //查询用户的所有的未读数
//            Integer flag = jdbcTemplate.queryForObject(sql,Integer.class);
            if (toSession != null && toSession.isOpen()) {
                //发送给发送者.
                toSession.getAsyncRemote().sendText(json.toString());
            }
            //保存聊天记录信息
            chatService.saveMessage(chatMsg);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //群发自定义消息
    public void broadcast(String message) {
        for (MyWebSocket item : webSocketSet) {
            //异步发送消息.
            item.session.getAsyncRemote().sendText(message);
        }
    }
}
