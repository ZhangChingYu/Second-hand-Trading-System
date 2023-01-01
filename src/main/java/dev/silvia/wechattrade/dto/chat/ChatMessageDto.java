package dev.silvia.wechattrade.dto.chat;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDto {
    //发送者电话
    private String toId;

    //信息内容
    private String content;

    //发送number
    private String number;

    //发送number
    private String time;

    private String fileName;
    //  信息类型
    //  0:文本
    //  1:图片
    //  2:视频
    private Integer types;
}
