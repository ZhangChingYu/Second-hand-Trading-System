package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class LoginServiceImpl extends ServiceImpl<UserDao, User> implements ILoginService {
    @Autowired
    private UserDao userDao;

    private static final String SALT = "123456";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    private Optional<Object> modelMapper;

    public LoginServiceImpl() {
    }

    @Override
    public User Login(String phone, String password) {
        User user=null;
        //创建sql
        try{
            String sql="select * from user_info where phone = '" + phone+ "'";
            user=jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
        }catch (Exception e){

        }
        return user;
    }

    @Override
    public int LostPasswaod(String phone,String password) {
        try{
            int succeed=2;
            String sql="select * from user_info where phone = '" + phone+ "'";
            String pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class))).getPassword();
            if(Objects.equals(pa, password)){
                return succeed;//密码为旧密码
            }
            else{
                String sql1="update user_info set password='"+ password +"' " +
                        "where phone = '" + phone+ "'";
                return jdbcTemplate.update(sql1);//修改密码成功返回1
            }
        }catch (Exception e){

        }
        return 0;//电话号码不存在,
    }
}
