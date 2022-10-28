package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterServiceImpl extends ServiceImpl<UserDao, User> implements IRegisterService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();


    @Override
    public int Register(User user) {
        String phone_num = user.getPhone();
        // make sure that the phone number haven't been registered before.
        String check = "select count(*) from user_info where phone = '" + phone_num+ "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 806;
        }
        else {
            user.setAuthority(1);
            user.setRegisterDate(new Date());
            userDao.insert(user);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                return 800;
            }
            return 808;
        }
    }

    @Override
    public User getUserById(Integer user_id) {
        User user = userDao.selectById(user_id);
        // 將UTF-8編碼轉回字串
        user.setUserName(transferUTF8.UTF8toC(user.getUserName()));
        return user;
    }
}
