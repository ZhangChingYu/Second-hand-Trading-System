package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.vo.request.auth.AuthRequestDetailVo;
import dev.silvia.wechattrade.vo.request.auth.AuthRequestOutlineVo;

import java.util.List;

public interface IUserManageService extends IService<User> {
    /** 用戶實名認證審核 */
    List<AuthRequestOutlineVo> showAllAuthRequest();    // 顯示所有未處理的實名認證請求
    List<AuthRequestOutlineVo> showAuthRequestByStatus(Integer status);     // 根據處理狀態顯示請求
    AuthRequestDetailVo readAuthRequest(Integer id);    // 閱讀實名認證請求
    Integer processAuthRequest(Integer id, String decision, String explain);    // 處理實名認證請求
    /** 發送審核結果給用戶 */
    Integer sendNotification(String target, String decision, String explain);
}
