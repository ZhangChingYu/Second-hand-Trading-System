package dev.silvia.wechattrade.vo.product;

import lombok.Data;


@Data
public class MyProductVo {
    private String number;  // 商品編號
    private Integer status;  // 狀態
    private String name;    // 名稱
    private Double price;   // 價格
    private String pictureFormat;   // 圖片格式jpg,png...
    private String coverPic;// 封面圖片
}
