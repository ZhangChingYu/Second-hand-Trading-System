package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.vo.notification.NotificationDetailVo;
import dev.silvia.wechattrade.vo.notification.NotificationOutlineVo;

import java.util.List;

public interface IUserNotificationService extends IService<Notification> {
    Boolean isRead(Integer id); // 判斷該通知是否已讀(已讀返回true)
    Integer sendNotification(Notification notification); // 發送通知
    NotificationDetailVo readNotification(Integer id);    // 閱讀通知
    /** 通知都是根據日期 新-->舊 排序的*/
    Integer showUnreadCount(String phone);                          // 顯示用戶未讀通知數量
    List<NotificationOutlineVo> showAllNotification(String phone);   // 顯示該用戶的所有通知
    List<NotificationOutlineVo> showUnReadNotification(String phone);    // 顯示所有未讀通知
    Integer deleteNotification(Integer id); // 刪除通知
    Integer deleteReadNotification(String phone);   // 刪除所有已讀通知
}
