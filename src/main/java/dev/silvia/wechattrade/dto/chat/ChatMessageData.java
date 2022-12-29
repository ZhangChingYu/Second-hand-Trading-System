package dev.silvia.wechattrade.dto.chat;
import lombok.Data;

@Data
//聊天信息
public class ChatMessageData {

    //发送者电话
    private String fromId;

    //信息内容
    private String content;

    //发送时间
    private String time;

    //  信息类型
    //  0:文本
    //  1:图片
    //  2:视频
    private Integer types;
}
