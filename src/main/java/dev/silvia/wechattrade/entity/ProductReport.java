package dev.silvia.wechattrade.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "product_report")
public class ProductReport {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String number;

    private String phone;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Integer status;
}
