package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.logindto.LoginDto;
import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.dto.response.ResponseDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.cryto.Sign;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
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

    @Autowired
    private Optional<User> user;

    // 正则匹配用户输入的格式，用户是：用户名，或手机号，或邮箱登录
    private final String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private final String ph = "^[1][3578]\\d{9}$";

    @Override
    public Optional<LoginResponseDto> login(LoginRequestDto request) {
        return this.verifyAccount(request)
                .map(loginDto -> {
                    LoginResponseDto user = this.modelMapper.map(loginDto, LoginResponseDto.class);
                    User u=user.getUser();
                    if(Objects.equals(user.getCode(), "666")){
                        user.setToken(Sign.generateToken(
                                u.getId(),
                                u.getUserName(),
                                u.getAuthority(),
                                1000 * 60 * 60
                        ));
                    }
                    return user;
                });
    }

    private Optional<LoginDto> verifyAccount(LoginRequestDto request) {

        // 用户状态: 2 已被永久封禁
        int two = 2;

        String username=request.getUsename();

        // 如果用户输入的用户名，格式符合邮箱，为邮箱登陆
        if (username.matches(em)) {
            // 通过邮箱查询数据库用户
            //要求用户名唯一
            user=accountRepository.findByEmail(username);
        }
        else if (username.matches(ph)){
            // 如果用户输入的用户名，格式符合手机号，为手机号登陆
            //要求用户名唯一
            user=accountRepository.findByPhone(username);
        }
        else{
            // 输入的用户名格式，不是邮箱，也不是手机号，那就是用户名登陆
            //要求用户名唯一
            user=accountRepository.findByUserName(username);
        }
        return this.user.filter(account -> {
                    try {
                         //System.out.println(Objects.equals(account.getPassword_hash(), Hash.encode(SALT, password)));
                        return true;
                        //return Objects.equals(account.getPassword_hash(), Hash.encode(SALT, password));
                    } catch (Exception e) {
                        return false;
                    }
                }).map(account ->{
                    LoginDto use=new LoginDto();
                    if(!Objects.equals(account.getAuthority(),two)){
                        if(!Objects.equals(account.getPassword(), request.getPassword())){
                            use.setMsg("密码错误");//密码错误
                            use.setCode("444");
                        }
                        else{
                            use.setUser(account);
                            use.setMsg("登陆成功");//登陆成功
                            use.setCode("666");
                        }
                    }
                    else{
                        use.setUser(account);
                        use.setMsg("用户被禁用");//用户被禁用
                        use.setCode("222");
                    }
                    return use;
                } );
    }

    @Override
    public ResponseDto lostPassward(LostPasswordDto request) {
        ResponseDto redto=new ResponseDto();
        try{
            String phone=request.getPhone();
            String password=request.getPassword();
            String pa;
            String sql;
            // 如果用户输入的用户名，格式符合邮箱，为邮箱登陆
            if (phone.matches(em)) {
                // 通过邮箱查询数据库用户
                sql="select * from user_info where email = '" + phone+ "'";
                pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                        new BeanPropertyRowMapper<>(User.class))).getPassword();
            }
            else if (phone.matches(ph)){
                // 如果用户输入的用户名，格式符合手机号，为手机号登陆
                sql="select * from user_info where phone = '" + phone+ "'";
                pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                        new BeanPropertyRowMapper<>(User.class))).getPassword();
            }
            else{
                redto.setMsg("手机号或邮箱格式错误");
                redto.setCode("111");
                return redto;
            }
            if(Objects.equals(pa, password)){
                redto.setMsg("密码为旧密码");
                redto.setCode("222");
                return redto;
            }
            else{
                String sql1="update user_info set password='"+ password +"' " +
                        "where phone = '" + phone+ "'";
                String sql2="update user_info set password='"+ password +"' " +
                        "where email = '" + phone+ "'";
                if(jdbcTemplate.update(sql1)==1||jdbcTemplate.update(sql2)==1){
                    redto.setMsg("修改密码成功");
                    redto.setCode("666");
                    return redto;
                }
                else{
                    redto.setMsg("修改密码失败");
                    redto.setCode("444");
                    return redto;
                }
            }
        }catch (Exception e){

        }
        redto.setMsg("用户不存在");
        redto.setCode("000");
        return redto;
    }
}
