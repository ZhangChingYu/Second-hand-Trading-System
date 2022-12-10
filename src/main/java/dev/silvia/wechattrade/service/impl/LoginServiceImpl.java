package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.cryto.Sign;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
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

    private Result use;

    private  Result redto;

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();

    @Autowired
    private ReadFile readFile = new ReadFile();
    // 正则匹配用户输入的格式，用户是：用户名，或手机号，或邮箱登录
    private final String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private final String ph =  "^[1][3578]\\d{9}$";

    private String auth_url = FileDirector.AUTH_URL;

    @Override
    public Optional<LoginResponseDto> login(LoginRequestDto request) {
        return this.verifyAccount(request)
                .map(loginDto -> {
                    LoginResponseDto user = new LoginResponseDto();
                    user.setCode(loginDto.getCode());
                    user.setMsg(loginDto.getMsg());

                    if(Objects.equals(user.getCode(), "666")){
                        //转换utf8
                        User u=transferUTF8.switchUtf8Tc((User) loginDto.getData());
                        //图片路径
                        String picture1;
                        if(u.getAvatar()==null||u.getAvatar().isEmpty()){
                            //默认图片
                            picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                            u.setAvatar(picture1);
                        }
                        else{
                            //  picture1 = readFile.getpictureBase64("Avatar",u.getPhone(),1);
                            picture1= ReadFile.getBaseFile(readFile.getAvatarPicture(u.getPhone()));
                            u.setAvatar(picture1);
                        }
                        if(u.getPicture()!=null){
                            String picture2;
                            picture2= ReadFile.getBaseFile(readFile.getAuthPicture(u.getPhone()));
                            u.setPicture(picture2);
                        }

                        user.setUser(u);
                        user.setToken(Sign.generateToken(
                                u.getId(),
                                transferUTF8.CtoUTF8(u.getUserName()),
                                u.getAuthority(),
                                1000 * 60 * 60
                        ));
                    }
                    return user;
                });
    }

    private Optional<Result> verifyAccount(LoginRequestDto request) {
        // 用户状态: 2 已被永久封禁
        int two = 2;
        String username=request.getUsername();
        // 如果用户输入的用户名，格式符合邮箱，为邮箱登陆
        if (!username.matches(em)) {
            // 通过邮箱查询数据库用户
            //要求用户名唯一
            if (username.matches(ph)){
                // 如果用户输入的用户名，格式符合手机号，为手机号登陆
                //要求用户名唯一
                user=accountRepository.findByPhone(username);
            }
            else{
                //格式错误

                use=new Result(ResultCode.PARAM_TYPE_BIND_ERROR);
                return Optional.ofNullable(use);
            }
        }
        else{
            user=accountRepository.findByEmail(username);
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
                    if(!Objects.equals(account.getAuthority(),two)){
                        if(!Objects.equals(account.getPassword(), request.getPassword())){
                            use=new Result(ResultCode.USER_LOGIN_ERROR);
                        }
                        else{
                            use=new Result(ResultCode.SUCCESS,account);
                        }
                    }
                    else{
                        //账号被禁用
                        use=new Result(ResultCode.USER_ACCOUNT_FORBIDDEN,account);
                    }
                    return use;
        } );
    }

    @Override
    public Result lostPassward(LostPasswordDto request) {
        try{
            String phone=request.getPhone();
            String password=request.getPassword();
            String pa;
            String sql;
            // 如果用户输入的用户名，格式符合邮箱，为邮箱登陆
            if (!phone.matches(em)) {
                // 通过邮箱查询数据库用户
                if (phone.matches(ph)){
                    // 如果用户输入的用户名，格式符合手机号，为手机号登陆
                    sql="select * from user_info where phone = '" + phone+ "'";
                    pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                            new BeanPropertyRowMapper<>(User.class))).getPassword();
                }
                else{
                    redto=new Result(ResultCode.PARAM_TYPE_BIND_ERROR);
                    return redto;
                }

            }
            else{
                sql="select * from user_info where email = '" + phone+ "'";
                pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                        new BeanPropertyRowMapper<>(User.class))).getPassword();
            }
            if(Objects.equals(pa, password)){
                redto=new Result(ResultCode.USER_PASSWORD_EXIST);
                return redto;
            }
            else{
                String sql1="update user_info set password='"+ password +"' " +
                        "where phone = '" + phone+ "'";
                String sql2="update user_info set password='"+ password +"' " +
                        "where email = '" + phone+ "'";
                if(jdbcTemplate.update(sql1)==1||jdbcTemplate.update(sql2)==1){
                    redto=new Result(ResultCode.SUCCESS);
                    return redto;
                }
                else{
                    redto=new Result(ResultCode.FAIL);
                    return redto;
                }
            }
        }catch (Exception e){

        }
        redto=new Result(ResultCode.USER_NOT_EXIST);
        return redto;
    }
}
