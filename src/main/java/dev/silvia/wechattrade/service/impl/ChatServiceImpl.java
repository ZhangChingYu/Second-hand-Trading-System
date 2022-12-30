package dev.silvia.wechattrade.service.impl;

import dev.silvia.wechattrade.dto.chat.ChatListData;
import dev.silvia.wechattrade.dto.chat.ChatMessageData;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.ChatLink;
import dev.silvia.wechattrade.entity.ChatList;
import dev.silvia.wechattrade.entity.ChatMessage;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.ChatLinkRepository;
import dev.silvia.wechattrade.handlers.common.repository.ChatListRepository;
import dev.silvia.wechattrade.handlers.common.repository.ChatMessageRepository;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.handlers.picSize.PicUtil;
import dev.silvia.wechattrade.handlers.picSize.PictureSize;
import dev.silvia.wechattrade.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service("chatService")
public class ChatServiceImpl implements IChatService {
    private  final  static int avatar_width= PictureSize.avatar_width;
    private  final  static  int avatar_height=PictureSize.avatar_height;
    private static final int MESSAGE_SIZE = 6;

    @Autowired
    private ChatLinkRepository chatLink;

    @Autowired
    private ChatListRepository chatList;

    @Autowired
    private ChatMessageRepository chatMessage;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReadFile readFile = new ReadFile();


    private Result res;
    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Override
    public String selectAssociation(String fromUser, String toUser) {
        String linkId;
        ChatLink chat=chatLink.findByFromIdAndToId(fromUser,toUser);
        if(chat==null){
            chat=chatLink.findByFromIdAndToId(toUser,fromUser);
        }
        if(chat!=null){
            linkId=chat.getNumber();
            return linkId;
        }
        else
            return "0";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result isFirstChat(String fromUser, String toUser) {
        //判断两者是否第一次聊天,返回两者的linkId
        String linkId = selectAssociation(fromUser,toUser);
        String msg="不是第一次聊天";
        if (Objects.equals(linkId, "0")) {
            String newLinkId = UUID.randomUUID().toString();
            //添加两者的关系表数据
            ChatLink chatLink1 = new ChatLink();
            chatLink1.setNumber(newLinkId);
            chatLink1.setCreateTime(new Date());
            chatLink1.setFromId(fromUser);
            chatLink1.setToId(toUser);
            //返回插入数据的id主键
            chatLink.save(chatLink1);

            //查询用户对应的图片
            User toUserPicture = userRepository.findByPhone(toUser).get();
            User fromUserPicture = userRepository.findByPhone(fromUser).get();

            //添加两条聊天列表数据（发送方，接收方)
            ChatList fromUserList = new ChatList();
            fromUserList.setFromId(fromUser);
            fromUserList.setToId(toUser);
            fromUserList.setNumber(newLinkId);
            fromUserList.setFromWindow(0);
            fromUserList.setUnread(0);
            fromUserList.setStatus(0);
            fromUserList.setToWindow(0);
            if(fromUserPicture.getAvatar()!=null){
                //默认图片
                fromUserList.setToAvatar(readFile.getAvatarPicture(toUser));
            }
            fromUserList.setToName(transferUTF8.UTF8toC(fromUserPicture.getUserName()));
            chatList.save(fromUserList);

            ChatList toUserList = new ChatList();
            toUserList.setFromId(toUser);
            toUserList.setToId(fromUser);
            toUserList.setNumber(newLinkId);
            toUserList.setFromWindow(0);
            toUserList.setUnread(0);
            toUserList.setStatus(0);
            toUserList.setToWindow(0);
            if(toUserPicture.getAvatar()!=null){
                fromUserList.setToAvatar(readFile.getAvatarPicture(fromUser));
            }
            toUserList.setToName(transferUTF8.UTF8toC(toUserPicture.getUserName()));
            chatList.save(toUserList);

            //插入一条空白消息（为了更好地联表查询数据）
//            ChatMessage chatMessage1 = new ChatMessage();
//            chatMessage1.setFromUser(fromUser);
//            chatMessage1.setLinkId(newLinkId);
//            chatMessage1.setSendTime(new Date());
//            chatMessage1.setType(0);
//            chatMessage1.setContent(" ");
//            chatMessage1.setToUser(toUser);
//            chatMessage1.setIsLatest(1);
//            chatMessage.save(chatMessage1);
            msg="是第一次聊天";
        }
        res=new Result("666",msg, linkId);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMessage(ChatMessage chatMessage1) {

        String linkId = chatMessage1.getNumber();
        String fromUser = chatMessage1.getFromId();
        String toUser = chatMessage1.getToId();

        ChatMessage message=chatMessage.findByNumberAndIsLatest(linkId,1);
        //将一条的信息的状态（最后一条）改为否
        if(message!=null) {
            message.setIsLatest(0);
            chatMessage.save(message);
        }

        //判断聊天双方是否在同一窗口聊天
        ChatList list=chatList.findByNumberAndFromIdAndToId(linkId,fromUser,toUser);
        int flag=list.getFromWindow()+list.getToWindow();

        ChatList list1=chatList.findByNumberAndFromIdAndToId(linkId,toUser,fromUser);
        // 1--只有一方在窗口中 未读数加给接收方，2--两者都在窗口中 清除未读数
        if (flag == 1) {
            //更新未读数,
            list1.setUnread(list1.getUnread()+1);
            chatList.save(list1);
        } else if (flag == 2) {
            //清空所有的未读数
            list1.setUnread(0);
            chatList.save(list1);
        }
        //保存聊天记录
        chatMessage.save(chatMessage1);
    }

    @Override
    public Result getFromUserChatList(String fromUser) {

        //查询获取用户的聊天列表
        String sql;
        List<ChatListData> chatListData ;
        sql=    " select cl.number as number, cl.to_name as toName, cl.to_id as toId" +
                ", cl.to_avatar as toAvatar," +
                " content as lastMessage, unread, send_time as time, from_window" +
                " from chat_list as cl, chat_message as cm where cl.from_id='"
                + fromUser+ "' and cl.number = cm.number and cm.is_latest = 1 " +
                "order by send_time desc";
        chatListData= jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(ChatListData.class));

        for (ChatListData chatListDatum : chatListData) {
            String picture1;
            if (chatListDatum.getToAvatar() == null || chatListDatum.getToAvatar().isEmpty()) {
                //默认图片
                picture1 = PicUtil.resizeImageToSize(FileDirector.AVATAR_URL, avatar_width,avatar_height);
                chatListDatum.setToAvatar(picture1);
            } else {
                picture1 =PicUtil.resizeImageToSize(readFile.getAvatarPicture(chatListDatum.getToId()), avatar_width,avatar_height);
                chatListDatum.setToAvatar(picture1);
            }
        }
        res=new Result(ResultCode.SUCCESS, chatListData);
        return res;
    }

    @Override
    public Result getRecentChatRecords(String fromUser, String toUser, int startIndex, int pageSize) {
        //获取两者之间的关联id
        String linkId = selectAssociation(fromUser,toUser);
        String sql;
        sql=    " select from_id as fromId, content, send_time as time, types from chat_message" +
                " where number = '"
                + linkId+ "' ORDER BY time desc limit " + startIndex +
                ", "+pageSize+"";
        //查询最近的六条聊天信息（包括未读）
        List<ChatMessageData> chatMessageData;
        chatMessageData= jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(ChatMessageData.class));

        //反转list
        Collections.reverse(chatMessageData);

        //清空未读信息
        ChatList chat=chatList.findByNumberAndFromIdAndToId(linkId,fromUser,toUser);
        if(chat!=null){
            chat.setUnread(0);
            chatList.save(chat);
        }
        for (ChatMessageData chatMessageDatum : chatMessageData) {
            if (chatMessageDatum.getTypes() == 1) {
                String path = chatMessageDatum.getContent();
                chatMessageDatum.setContent(ReadFile.getBaseFile(path));
            }
        }
        res=new Result(ResultCode.SUCCESS,chatMessageData);
        return res;
    }

    @Override
    public Result getPageNumber(String linkId) {
        int MessagesTotalNumber = chatMessage.findByNumber(linkId).size();
        int pageNumber = (MessagesTotalNumber % MESSAGE_SIZE == 0) ? (MessagesTotalNumber / MESSAGE_SIZE) : (MessagesTotalNumber / MESSAGE_SIZE) + 1;
        return Result.SUCCESS(pageNumber);
    }

    @Override
    public Result updateWindows(String fromUser, String toUser) {

        //获取两者之间的关联id
        String linkId = selectAssociation(fromUser,toUser);
        String sql;
        //更新点击了聊天框的同一窗口值
        sql=    "update chat_list set from_window = 1 where number = '"
                + linkId+ "' and from_id = '" + fromUser +"'";
        jdbcTemplate.update(sql);
        sql=    "update chat_list set to_window = 1 where number = '"
                + linkId+ "' and to_id = '" + fromUser +"'";
        jdbcTemplate.update(sql);

        //清除当前fromUser的未读数
        sql=    "update chat_list set unread = 0" +
                " where number = '"
                + linkId+ "' and from_id = '" + fromUser +"' and to_id = '" + toUser+"'";
        jdbcTemplate.update(sql);

        //更新其他窗口的值
        sql=    "update chat_list set from_window = 0 where number !='"
                + linkId+ "' and from_id = '" + fromUser +"'";
        jdbcTemplate.update(sql);
        sql=    "update chat_list set to_window = 0 where number !='"
                + linkId+ "' and to_id = '" + fromUser +"'";
        jdbcTemplate.update(sql);
        return userRepository.findByPhone(toUser).map(
                value -> Result.SUCCESS(
                        value.getIsOnline()
                )).orElseGet(
                        () -> Result.FAIL("用户不存在")
        );
    }

    @Override
    public Result getUnreadTotalNumber(String username) {
        String sql;
        //更新点击了聊天框的同一窗口值
        sql=  "select sum(unread) from chat_list where from_id = '"+ username +"'";
        //查询用户的所有的未读数
        Integer unreadTotalNumber = jdbcTemplate.queryForObject(sql, Integer.class);

        if (unreadTotalNumber != null) {
            return Result.SUCCESS(unreadTotalNumber);
        } else {
            return Result.SUCCESS(0);
        }
    }

    @Override
    public void resetWindows(String username) {
        //退出websocket连接时，重置窗口值
        String sql;
        //更新点击了聊天框的同一窗口值
        System.out.println(username);
        sql= "update chat_list set from_window = 0 where from_id = '" + username +"'";
        jdbcTemplate.update(sql);
        //更新点击了聊天框的同一窗口值
        sql= "update chat_list set to_window = 0 where to_id = '" + username +"'";
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteMessage(String id, Integer isFromUser) {
        //退出websocket连接时，重置窗口值
//        Optional<ChatMessage> message=chatMessage.findById(id);
//        if(isFromUser==1){
//
//        }
    }
}
