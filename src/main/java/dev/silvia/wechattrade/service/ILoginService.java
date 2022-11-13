package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.User;

public interface ILoginService extends IService<User> {
    User Login(String phone,String password);//登录，输入电话和密码，输出用户信息
    int LostPasswaod(String phone,String password);//忘记密码

}

