package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.NotificationPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.INotificationService;
import dev.silvia.wechattrade.vo.notification.NotificationDetailVo;
import dev.silvia.wechattrade.vo.notification.NotificationOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationDao, Notification> implements INotificationService {
    @Autowired
    NotificationDao notificationDao;
    @Autowired
    NotificationPacking notePacking;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private  UserDao userDao;

    @Override
    public Boolean isRead(Integer id) {
        Notification notification = notificationDao.selectById(id);
        if(notification.getStatus() == 0){  // 已讀
            return true;
        }
        return false;
    }

    @Override
    public Integer sendNotification(Notification notification) {
        if(getUser(notification.getTarget()) == null ){
            return 422; // 接收者不存在
        }
        if(notificationDao.insert(notification) > 0){
            return 201;     // 發送成功
        }
        return 404; // 發送失敗
    }

    @Override
    public NotificationDetailVo readNotification(Integer id) {
        Notification notification = notificationDao.selectById(id);
        if(!isRead(id)){
            notification.setStatus(1);  // 設置為已讀
            notificationDao.updateById(notification);
        }
        NotificationDetailVo detailVo = notePacking.NoteToDetailVo(notification, getUser(notification.getTarget()));
        return detailVo;
    }

    @Override
    public List<NotificationOutlineVo> showAllNotification(String phone) {
        List<NotificationOutlineVo> outlineVos = new ArrayList<>();
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("target", phone);
        List<Notification> notifications = notificationDao.selectList(wrapper);
        for(Notification note : notifications){
            NotificationOutlineVo outlineVo = notePacking.NoteToOutlineVo(note);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public List<NotificationOutlineVo> showUnReadNotification(String phone) {
        List<NotificationOutlineVo> outlineVos = new ArrayList<>();
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("target", phone);
        wrapper.eq("status",0);
        List<Notification> notifications = notificationDao.selectList(wrapper);
        for(Notification note : notifications){
            NotificationOutlineVo outlineVo = notePacking.NoteToOutlineVo(note);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    private User getUser(String phone){
        String findUser = "select * from user_info where phone='"+phone+"'";
        User user = jdbcTemplate.queryForObject(findUser, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

}
