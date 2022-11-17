package dev.silvia.wechattrade.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_info")
@TableName(value="user_info")
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String phone;

    private String password;

    private Integer authority;

    private String realName;

    private String idCard;

    private String stuNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerDate;

    private Integer violationCount;

    private String defaultAddr;

    private String addrOne;

    private String addrTwo;

    private String addrThree;

    private String email;

}
