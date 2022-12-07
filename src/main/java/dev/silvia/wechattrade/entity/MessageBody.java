package dev.silvia.wechattrade.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MessageBody {

    /**
     * 发送人姓名
     */
    private String fromName;

    /**
     * 接收人姓名
     */
    private String toName;


    /**
     * 消息内容
     */
    private String content;

    /**
     *
     * 发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
}
