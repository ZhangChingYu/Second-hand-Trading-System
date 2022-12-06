package dev.silvia.wechattrade.vo.product;

import lombok.Data;

@Data
public class ProductUploadResponseVo {  // 商品信息成功添加進數據庫後返回給前端的信息
    private Integer status; // 狀態碼
    private String number;  // 商品編號(上傳圖片用)
}
