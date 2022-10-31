package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.User;

public interface IDemoService extends IService<User> {
    User chineseOutput(String user_name);   // 測試中文讀寫數據庫會不會出現亂碼
    String testCtoUTF8(String user_name);   // 測試ChineseUTF8
    String testUTF8toC(String utf8);   // 測試ChineseUTF8
    User dataTransUTF8(Integer id);     // 將指定數據進行UTF8編碼後儲存
    User dataShowUI(Integer id);        // 將UTF8數據進行解碼在發送給前端
}
