package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.User;

public interface IRegisterService extends IService<User> {
    int Register(User user); // 用戶註冊

    User getUserById(Integer user_id); // 根據用戶id顯示用戶信息
}
