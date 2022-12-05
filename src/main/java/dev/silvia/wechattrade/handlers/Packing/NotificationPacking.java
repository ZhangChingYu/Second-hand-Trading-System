package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.vo.notification.NotificationDetailVo;
import dev.silvia.wechattrade.vo.notification.NotificationOutlineVo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationPacking {
    private TransferUTF8 transferUTF8 = new TransferUTF8();

    private static String[] TYPE = {"new", "common", "warn"};

    public NotificationOutlineVo NoteToOutlineVo(Notification note){
        NotificationOutlineVo outlineVo = new NotificationOutlineVo();
        outlineVo.setId(note.getId());
        outlineVo.setType(TYPE[note.getType()]);
        outlineVo.setTitle(transferUTF8.UTF8toC(note.getTitle()));
        outlineVo.setDate(note.getDate());
        outlineVo.setStatus(note.getStatus());
        return outlineVo;
    }

    public NotificationDetailVo NoteToDetailVo(Notification note, User to){
        NotificationDetailVo detailVo = new NotificationDetailVo();
        detailVo.setId(note.getId());
        detailVo.setType(TYPE[note.getType()]);
        detailVo.setTitle(transferUTF8.UTF8toC(note.getTitle()));
        detailVo.setTo(transferUTF8.UTF8toC(to.getUserName())+to.getPhone());
        detailVo.setContent(transferUTF8.UTF8toC(note.getContent()));
        detailVo.setDate(note.getDate());
        detailVo.setFrom(transferUTF8.UTF8toC(note.getFrom()));
        return detailVo;
    }
}
