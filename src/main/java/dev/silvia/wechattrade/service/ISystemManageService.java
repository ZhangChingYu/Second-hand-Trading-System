package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.vo.PrincipleVo;

import java.util.List;

public interface ISystemManageService extends IService<User> {
    /** 管理"幫助" */
    // 創建新的幫助分類
    Integer createHelpCatalog(String name);
    // 創建新的幫助(問題+答案)
    Integer createHelp(String catalog, String question, String answer);
    // 刪除幫助
    Integer deleteHelp(String catalog, String question);
    // 刪除幫助分類(若裡面還有問題則刪除失敗)
    Integer deleteHelpCatalog(String catalog);
    // 更新幫助(答案)
    Integer updateHelp(String catalog, String question, String newAnswer);
    Integer updateHelpCatalog(String catalog, String newCatalog);
    // 顯示幫助類型
    List<String> showHelpCatalog();
    // 顯示幫助問題
    List<String> showHelpQuestion(String catalog);
    // 顯示幫助答案
    String showHelpAnswer(String catalog, String question);
    /** 管理"用戶協議" */
    // 創建用戶協議
    Integer createUserPrinciple(String version, String content);
    // 刪除用戶協議
    Integer deleteUserPrinciple(String version);
    // 更新用戶協議
    Integer updateUserPrinciple(String version, String content);
    // 顯示用戶協議
    PrincipleVo showPrinciple(String version);
    // 顯示最新版用戶協議
    PrincipleVo showNewestPrinciple();
}
