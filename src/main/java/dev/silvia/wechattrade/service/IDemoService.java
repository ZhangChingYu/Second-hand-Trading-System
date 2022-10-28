package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.User;

public interface IDemoService extends IService<User> {
    User chineseOutput(String user_name);   // 測試中文讀寫數據庫會不會出現亂碼
    String testCtoUTF8(String user_name);   // 測試ChineseUTF8
    String testUTF8toC(String utf8);   // 測試ChineseUTF8
}
