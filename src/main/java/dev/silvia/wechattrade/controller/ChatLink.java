package dev.silvia.wechattrade.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "chat_link")
@Data
public class ChatLink implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //列表id（自增）
    private Integer id;

    //关系表id
    private String linkId;

    //发送者
    private String fromUser;

    //接收者
    private String toUser;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
