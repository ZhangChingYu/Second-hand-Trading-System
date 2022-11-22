package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.WXAuth;
import dev.silvia.wechattrade.entity.Weixin;

public interface IWeixinService extends IService<Weixin> {
    String getSessionId(String code);
    Result authLogin(WXAuth wxAuth);
}

