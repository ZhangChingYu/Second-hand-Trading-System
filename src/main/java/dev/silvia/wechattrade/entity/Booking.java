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
@Table(name = "booking")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "booking")
public class Booking implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private String sellerId;

    private String  buyerId;

    private String productId;

    private Integer ordersNum;

    private Double price;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    private Integer status;
}
