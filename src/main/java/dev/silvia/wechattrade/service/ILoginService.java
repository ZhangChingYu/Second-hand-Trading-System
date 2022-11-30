package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.logindto.LoginRequestDto;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.dto.logindto.LostPasswordDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.User;

import java.util.Optional;

public interface ILoginService extends IService<User> {

    Optional<LoginResponseDto> login(LoginRequestDto request);
    Result lostPassward(LostPasswordDto request);//忘记密码
}

