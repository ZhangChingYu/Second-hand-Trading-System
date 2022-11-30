package dev.silvia.wechattrade.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "seller_info")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "seller_info")
public class Seller implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String exchangeId;

    private String phone;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shippingTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;
}
