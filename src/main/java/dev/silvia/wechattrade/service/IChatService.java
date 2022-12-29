package dev.silvia.wechattrade.service;

import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.ChatMessage;

public interface IChatService {

    //查询聊天双方的关联id
    String selectAssociation(String fromUser, String toUser);

    //是否第一次聊天
    Result isFirstChat(String fromUser, String toUser);

    //保存聊天记录
    void saveMessage(ChatMessage chatMessage);

    //获取当前用户的聊天列表
    Result getFromUserChatList(String fromUser);

    //获取发送者与接收者的最近的聊天记录
    Result getRecentChatRecords(String fromUser, String toUser, int startIndex, int pagesSize);

    //获取发送者与接收者聊天记录的总页数
    Result getPageNumber(String linkId);

    //更新是否在同一窗口值
    Result updateWindows(String fromUser, String toUser);


    //获取当前用户的未读数
    Result getUnreadTotalNumber(String username);

    //重置窗口值
    void resetWindows(String username);
}
