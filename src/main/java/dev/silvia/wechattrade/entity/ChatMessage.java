package dev.silvia.wechattrade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@TableName(value = "chat_manage")
public class ChatMessage implements Serializable {
    //文本
    public static final int MESSAGE_TYPE_TEXT = 0;

    //图片
    public static final int MESSAGE_TYPE_IMAGE = 1;

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //列表id（自增）
    private Integer id;

    //关系表id
    private String number;

    //发送者
    private String fromId;

    //接收者
    private String toId;

    //内容
    private String content;

    //发送时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    //消息类型  0--普通文本（默认）
    private Integer types = MESSAGE_TYPE_TEXT;

    //是否为最后一条
    private Integer isLatest;

    private Integer isDelete;
}
