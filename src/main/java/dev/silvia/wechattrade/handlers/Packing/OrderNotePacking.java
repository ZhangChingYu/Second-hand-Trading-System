package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.Booking;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderNotePacking {
    private TransferUTF8 transferUTF8 = new TransferUTF8();
    // 1. 賣家不同意退款
    public Notification disagreeRefund(String seller_phone, String buyer_phone, Product product, Booking booking){
        Notification notification = new Notification();
        notification.setSource(transferUTF8.CtoUTF8("賣家"+seller_phone));
        notification.setTarget(buyer_phone);
        notification.setType(1);
        notification.setDate(new Date());
        notification.setStatus(1);
        notification.setTitle(transferUTF8.CtoUTF8("退款申請被拒通知"));
        String product_name = transferUTF8.UTF8toC(product.getName());
        notification.setContent(transferUTF8.CtoUTF8("很遺憾地通知您，您向賣家請求的["+booking.getNumber()+"]訂單，申請商品 "+product_name+" 退款遭賣家拒絕。"));
        return notification;
    }
    // 2. 賣家同意預約
    public Notification agreeOrder(String seller_phone, String buyer_phone, Product product){
        Notification notification = new Notification();
        notification.setSource(transferUTF8.CtoUTF8("賣家"+seller_phone));
        notification.setTarget(buyer_phone);
        notification.setType(1);
        notification.setDate(new Date());
        notification.setStatus(1);
        notification.setTitle(transferUTF8.CtoUTF8("商品預約成功"));
        String product_name = transferUTF8.UTF8toC(product.getName());
        notification.setContent(transferUTF8.CtoUTF8("恭喜您，您預約的商品["+product_name+"]已被賣家同意，請進一步完成下單動作。"));
        return notification;
    }
    // 3. 買家預約通知賣家
    public Notification newOrder(String seller_phone, String buyer_phone, Product product, Booking booking){
        Notification notification = new Notification();
        notification.setSource(transferUTF8.CtoUTF8("買家"+buyer_phone));
        notification.setTarget(seller_phone);
        notification.setType(1);
        notification.setDate(new Date());
        notification.setStatus(1);
        notification.setTitle(transferUTF8.CtoUTF8("您有新的商品預約"));
        String product_name = transferUTF8.UTF8toC(product.getName());
        notification.setContent(transferUTF8.CtoUTF8("恭喜您，您的商品["+product_name+"]有了買家預約，新生成的訂單編號為: "+booking.getNumber()+"，請賣佳進一步同意/拒絕預約。"));
        return notification;
    }
    // 4. 買家已下單通知賣家發貨
    public Notification deliverAwait(String seller_phone, String buyer_phone, Product product, Booking booking){
        Notification notification = new Notification();
        notification.setSource(transferUTF8.CtoUTF8("買家"+buyer_phone));
        notification.setTarget(seller_phone);
        notification.setType(1);
        notification.setDate(new Date());
        notification.setStatus(1);
        notification.setTitle(transferUTF8.CtoUTF8("新的商品下單"));
        String product_name = transferUTF8.UTF8toC(product.getName());
        notification.setContent(transferUTF8.CtoUTF8("訂單號["+booking.getNumber()+"]已完成付款，請賣家盡快完成商品 "+product_name+" 的發貨。"));
        return notification;
    }
    // 5. 買家請求退款通知賣家
    public Notification refundRequest(String seller_phone, String buyer_phone, Product product, Booking booking){
        Notification notification = new Notification();
        notification.setSource(transferUTF8.CtoUTF8("買家"+buyer_phone));
        notification.setTarget(seller_phone);
        notification.setType(1);
        notification.setDate(new Date());
        notification.setStatus(1);
        notification.setTitle(transferUTF8.CtoUTF8("退款請求"));
        String product_name = transferUTF8.UTF8toC(product.getName());
        notification.setContent(transferUTF8.CtoUTF8("訂單號["+booking.getNumber()+"]要求退款，請賣家進一步處理商品 "+product_name+" 的退款"));
        return notification;
    }
    // 6. 買家成功收到退款通知
    public Notification refundSuccess(String seller_phone, String buyer_phone, Booking booking){
        Notification notification = new Notification();
        notification.setSource(transferUTF8.CtoUTF8("賣家"+seller_phone));
        notification.setTarget(buyer_phone);
        notification.setType(1);
        notification.setDate(new Date());
        notification.setStatus(1);
        notification.setTitle(transferUTF8.CtoUTF8("訂單退款成功"));
        notification.setContent(transferUTF8.CtoUTF8("恭喜您，您的訂單["+booking.getNumber()+"]已成功退款"+booking.getPrice()+"元。"));
        return notification;
    }
}
