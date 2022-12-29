package dev.silvia.wechattrade.entity;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "chat_list")
@Data
public class ChatList implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //列表id（自增）
    private Integer id;

    //关系表主键
    private String number;

    //发送者
    private String fromId;

    //接收者
    private String toId;

    //接收者昵称
    private String toName;

    //接收者头像
    private String toAvatar;

    //发送者是否在窗口
    private Integer fromWindow;

    //接收者是否在窗口
    private Integer toWindow;

    //未读数 fromUser的未读数
    private Integer unread;

    //是否被删除
    private Integer status;
}
