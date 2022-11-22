package dev.silvia.wechattrade.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "product_manage")
public class Product implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String sPhone;

    private String number;

    private Integer storage;

    private Integer picture;

    private  String intro;

    private Double price;

    private Integer status;

    private Integer reportCount;

    private String catalog;

    private String address;

    private Integer likeCount;
}
