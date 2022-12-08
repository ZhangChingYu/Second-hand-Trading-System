package dev.silvia.wechattrade.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName(value="authentication")
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthenticationRequest implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String phone;

    private String realName;

    private String idNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Integer status;
}
