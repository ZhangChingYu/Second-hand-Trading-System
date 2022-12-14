package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.SystemNotePacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.UserNameGenerator;
import dev.silvia.wechattrade.handlers.fileHandler.WriteFile;
import dev.silvia.wechattrade.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl extends ServiceImpl<UserDao, User> implements IRegisterService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WriteFile writeFile;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    SystemNotePacking systemNotePacking;
    UserNameGenerator nameGenerator = new UserNameGenerator();
    @Override
    public int Register(User user) {
        String phone_num = user.getPhone();
        // make sure that the phone number haven't been registered before.
        String check = "select count(*) from user_info where phone = '" + phone_num+ "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 422;     // 該手機號已被註冊，不可重複註冊
        }
        else {
            if(user.getUserName().contains(" ")){   // 檢查命名是否有包含空格
                return 421;     // 暱稱不可包含空格
            }
            String sql = "select user_name from user_info";
            List<String> nameList = jdbcTemplate.queryForList(sql,String.class);
            for(int i = 0; i < nameList.size(); i++){
                nameList.set(i, transferUTF8.UTF8toC(nameList.get(i)));
            }
            user.setUserName(transferUTF8.CtoUTF8(nameGenerator.generateRandomName(user.getUserName(), nameList)));
            user.setAuthority(1);
            user.setRegisterDate(new Date());
            userDao.insert(user);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                if(sendWelcomeNotification(user.getPhone()) == 201){
                    System.out.println("Welcome notification sent.");
                }
                if(writeFile.createUserFilePath(user.getPhone()) == 201){
                    System.out.println("User file path created.");
                }
                return 201; // 註冊成功
            }
            return 404;     // 註冊失敗，信息未成功添加
        }
    }

    @Override
    public User getUserById(Integer user_id) {
        User user = userDao.selectById(user_id);
        // 將UTF-8編碼轉回字串
        user.setUserName(transferUTF8.UTF8toC(user.getUserName()));
        return user;
    }

    @Override
    public Integer sendWelcomeNotification(String phone) {
        Notification note = systemNotePacking.WelcomeNote(phone);
        if(notificationDao.insert(note) > 0 ){
            return 201;
        }
        return 404;
    }
}
