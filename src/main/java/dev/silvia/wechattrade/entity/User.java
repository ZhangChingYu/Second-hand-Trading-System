package dev.silvia.wechattrade.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "user_info")
public class User implements Serializable {
    private static final long serialVersionUID=1L;

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


}
