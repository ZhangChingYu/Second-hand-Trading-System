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
@Table(name = "exchange_info")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "exchange_info")
public class ExchangeInfo implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private String productNum;

    private String name;

    private Integer ordersNum;

    private Double price;

    private String expressDelivery;

    private String deliveryId;

    private Double freight;

    private Double discounts;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   buildTime;

    private String payment;

    private Integer isDelete;

    private String status;
}
