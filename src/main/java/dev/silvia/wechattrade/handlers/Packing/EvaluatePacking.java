package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.BuyerEvaluate;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.vo.seller.BuyerInfoPack;
import dev.silvia.wechattrade.vo.seller.ProductEvaluateVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class EvaluatePacking {  // 將BuyerEvaluate包裝成Vo的工具
    private ReadFile readFile = new ReadFile();
    private TransferUTF8 transferUTF8 = new TransferUTF8();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ProductEvaluateVo EvaluateToVo(BuyerEvaluate evaluate, User buyer, Product product){
        ProductEvaluateVo evaluateVo = new ProductEvaluateVo();
        evaluateVo.setNumber(evaluate.getNumber());
        if(product == null){    // 可能商品被賣家刪掉了
            evaluateVo.setProductName("(此商品已下架)");
        } else {
            evaluateVo.setProductName(transferUTF8.UTF8toC(product.getName()));
        }
        evaluateVo.setDate(sdf.format(evaluate.getDate()));
        evaluateVo.setScore1(evaluate.getScore1());
        evaluateVo.setScore2(evaluate.getScore2());
        evaluateVo.setScore3(evaluate.getScore3());
        if(evaluate.getIsAnonymous().equals(0)){    // 需要打包買家信息
            evaluateVo.setIsAnonymous(false);
            BuyerInfoPack buyerInfo = BuyerToPack(buyer);
            evaluateVo.setBuyer(buyerInfo);
        } else {    // 不須打包
            evaluateVo.setIsAnonymous(true);
            evaluateVo.setBuyer(null);
        }
        return evaluateVo;
    }

    private BuyerInfoPack BuyerToPack(User buyer){
        BuyerInfoPack buyerInfoPack = new BuyerInfoPack();
        buyerInfoPack.setBuyerPhone(buyer.getPhone());
        buyerInfoPack.setBuyerName(transferUTF8.UTF8toC(buyer.getUserName()));
        buyerInfoPack.setBuyerHeadPic(readFile.readAvatarPicture(buyer.getPhone()));
        return buyerInfoPack;
    }
}
