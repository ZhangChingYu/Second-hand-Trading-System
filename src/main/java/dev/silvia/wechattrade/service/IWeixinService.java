package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.entity.WXAuth;

public interface IWeixinService extends IService {
    String getSessionId(String code);
    LoginResponseDto authLogin(WXAuth wxAuth);

    String getToken(String session);
}

