package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SystemNotePacking {    // 系統統一通知的信息內容包裝

    public final static String SYSTEM_NAME = "閒置重重";
    static String welcome_title = "歡迎你加入閒置重重!";
    static String welcome_content = "您好，這裡是閒置重重系統中心，我們誠摯的歡迎您使用我們的小程序。\n希望本系統能幫助您找到感興趣的二手商品，或協助您出手二手商品。\n祝您使用愉快!";

    private TransferUTF8 transferUTF8 = new TransferUTF8();

    public Notification WelcomeNote(String target){
        Notification note = new Notification();
        note.setStatus(1);  // 未讀
        note.setType(0);    // [new]
        note.setTarget(target);
        note.setSource(transferUTF8.CtoUTF8(SYSTEM_NAME));
        note.setTitle(transferUTF8.CtoUTF8(welcome_title));
        note.setContent(transferUTF8.CtoUTF8(welcome_content));
        note.setDate(new Date());
        return note;
    }
}
