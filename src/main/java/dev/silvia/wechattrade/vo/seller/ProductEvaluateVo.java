package dev.silvia.wechattrade.vo.seller;

import lombok.Data;

@Data
public class ProductEvaluateVo {    // 用戶的商品評價顯示
    private String number;          // 商品編號
    private String productName;     // 商品名稱
    private String date;            // 評價日期
    private Integer score1;         // 描述相符得分
    private Integer score2;         // 物流服務得分
    private Integer score3;         // 服務態度得分
    private String evaluate;        // 文字描述
    private Boolean isAnonymous;    // 評價是否匿名
    private BuyerInfoPack buyer;    // 買家信息(匿名不返回)
}
