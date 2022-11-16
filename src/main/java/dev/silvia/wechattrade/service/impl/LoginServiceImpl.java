package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.logindto.LoginDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.common.cryto.Sign;
import dev.silvia.wechattrade.service.ILoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class LoginServiceImpl extends ServiceImpl<UserDao, User> implements ILoginService {
    @Autowired
    private UserDao userDao;

    private static final String SALT = "123456";
    @Autowired
    private  UserRepository accountRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Override
    public Optional<LoginResponseDto> login(String phone, String password, Integer authority) {
        return this.verifyAccount(phone, password,authority)
                .map(loginDto -> {
                    LoginResponseDto user = this.modelMapper.map(loginDto, LoginResponseDto.class);
                    user.setToken(Sign.generateToken(
                            user.getId(),
                            user.getUserName(),
                            user.getAuthority(),
                            1000 * 60 * 60 * 24
                    ));
                    return user;
                });
    }

    private Optional<LoginDto> verifyAccount(String phone, String password,Integer authority) {
        return this.accountRepository.findByPhone(phone)
                .filter(account -> {
                    try {
                         //System.out.println(Objects.equals(account.getPassword_hash(), Hash.encode(SALT, password)));
                        return true;
                        //return Objects.equals(account.getPassword_hash(), Hash.encode(SALT, password));
                    } catch (Exception e) {
                        return false;
                    }
                }).map(account ->{
                    LoginDto user=new LoginDto();
                    user.setAuthority(account.getAuthority());
                    user.setUserName(account.getUserName());
                    user.setId(account.getId());
                    if(!Objects.equals(account.getAuthority(), authority)){
                        if(!Objects.equals(account.getPassword(), password)){
                            user.setMsg("444");//密码错误和权限错误
                        }
                        else{
                            user.setMsg("333");//权限错误
                        }
                    }
                    else {
                        user.setMsg("666");//登陆成功
                    }
                    return user;
                } );
    }

    @Override
    public int lostPassward(String phone, String password) {
        try{
            int succeed=2;
            String sql="select * from user_info where phone = '" + phone+ "'";
            String pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class))).getPassword();
            if(Objects.equals(pa, password)){
                return succeed;//密码为旧密码,返回2
            }
            else{
                String sql1="update user_info set password='"+ password +"' " +
                        "where phone = '" + phone+ "'";
                return jdbcTemplate.update(sql1);//修改密码成功返回1
            }
        }catch (Exception e){

        }
        return 0;//电话号码不存在,返回0
    }
}
