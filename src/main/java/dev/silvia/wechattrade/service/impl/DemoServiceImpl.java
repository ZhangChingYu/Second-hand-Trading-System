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
public class DemoServiceImpl extends ServiceImpl<UserDao, User> implements IDemoService {
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

    @Override
    public User dataTransUTF8(Integer id) {
        // 將接收到的字串轉為UTF-8編碼存入數據庫
        User T_user = userDao.selectById(id);
        String T_user_name = transferUTF8.CtoUTF8(T_user.getUserName());
        String T_real_name = transferUTF8.CtoUTF8(T_user.getRealName());
        String T_default_addr = transferUTF8.CtoUTF8(T_user.getDefaultAddr());
        String T_addr_one = transferUTF8.CtoUTF8(T_user.getAddrOne());
        String T_addr_two = transferUTF8.CtoUTF8(T_user.getAddrTwo());
        String T_addr_three = transferUTF8.CtoUTF8(T_user.getAddrThree());
        String sql = "update user_info set user_name='"+ T_user_name +
                "', real_name='"+ T_real_name +
                "', default_addr='"+ T_default_addr +
                "', addr_one='"+ T_addr_one +
                "', addr_two='"+ T_addr_two +
                "', addr_three='"+ T_addr_three +"' where id = "+ id;
        int check = jdbcTemplate.update(sql);
        if(check == 1){
            User user = userDao.selectById(id);
            return user;
        }
        return null;
    }

    // 將UTF8數據進行解碼在發送給前端
    @Override
    public User dataShowUI(Integer id) {
        User user = userDao.selectById(id);
        user.setUserName(transferUTF8.UTF8toC(user.getUserName()));
        user.setRealName(transferUTF8.UTF8toC(user.getRealName()));
        /*
        if(!user.getDefaultAddr().equals("null")){
            System.out.println("not null");
        }else {
            System.out.println("is null");
        }**/
        user.setDefaultAddr(transferUTF8.UTF8toC(user.getDefaultAddr()));
        user.setAddrOne(transferUTF8.UTF8toC(user.getAddrOne()));
        user.setAddrTwo(transferUTF8.UTF8toC(user.getAddrTwo()));
        user.setAddrThree(transferUTF8.UTF8toC(user.getAddrThree()));
        return user;
    }
}
