package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemoService extends ServiceImpl<UserDao, User> implements IDemoService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Autowired
    private TransferUTF8 transferUTF8 = new TransferUTF8();


    @Override
    public User chineseOutput(String user_name) {
        // 將接收到的字串轉為UTF-8編碼存入數據庫
        String userName = transferUTF8.CtoUTF8(user_name);
        String sql = "update user_info set user_name='"+ userName +"' where id = "+8;
        int check = jdbcTemplate.update(sql);
        if(check == 1){
            User user = userDao.selectById(8);
            return user;
        }
        return null;
    }

    // 測試含中文的字串轉為UTF-8
    @Override
    public String testCtoUTF8(String user_name) {
        return transferUTF8.CtoUTF8(user_name);
    }

    // 測試UTF-8轉為含中文的字串
    @Override
    public String testUTF8toC(String utf8) {
        return transferUTF8.UTF8toC(utf8);
    }
}
