package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.entity.User;

import java.util.Optional;

public interface ILoginService extends IService<User> {
    Optional<LoginResponseDto> login(String phone, String password, Integer authority);
    int lostPassward(String phone, String password);//忘记密码
}

