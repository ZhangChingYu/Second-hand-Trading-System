package dev.silvia.wechattrade.dto.chat;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
//聊天信息
public class ChatMessageData {

    private Integer id;
    //发送者电话
    private String fromId;

    //信息内容
    private String content;

    //发送时间
    private String time;

    private boolean isShowTime =true;
    //  信息类型
    //  0:文本
    //  1:图片
    //  2:视频
    private Integer types;
}
