package dev.silvia.wechattrade.handlers.websocket;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

//定义会话存储
public class SocketStorage {

    private SocketStorage(){}

    // 根据name存储的Socket会话Map
    // key:name, value:session
    public static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    //根据Socket会话ID存储的name的Map
    //key:sessionId, value:name
    public static ConcurrentHashMap<String, String> nameMap = new ConcurrentHashMap<>();
}
