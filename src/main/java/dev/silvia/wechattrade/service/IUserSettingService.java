package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.AddressCreateDto;
import dev.silvia.wechattrade.dto.AddressUpdateDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.vo.AddressVo;
import dev.silvia.wechattrade.vo.AuthenticationVo;
import io.swagger.models.auth.In;

import java.util.List;

public interface IUserSettingService extends IService<User> {
    /** 帳號與安全設置 */
    int swapRelatedPhone(String phone);     // 更換綁定的手機
    int realNameAuthentication(AuthenticationVo authenticationVo);           // 發送實名認證申請
    int frozeAccount(String phone);     // 凍結帳號，若用戶發現帳號被盜用
    int unfrozeAccount(String phone);       // 解凍帳號(由前端進行短信驗證，通過後再調用該功能)
    List<String> AccountLog(String phone);  // 顯示帳號日誌(了解帳號登入、綁定的詳細信息，若發現異常請儘快處理)

    /** 消息接收設置 */

    /** 地址設置 */
    int addAddress(AddressCreateDto addressCreateDto);       // 添加地址
    int addressEditing(AddressUpdateDto addressUpdateDto);    // 更新指定地址，rank: 0(default), 1(備用1), 2(備用2), 3(備用3)
    int deleteAddress(String phone, Integer rank);    // 刪除地址
    List<AddressVo> showAllAddress(String phone);     // 顯示用戶所有地址
    AddressVo getDefaultAddress(String phone);  // 獲取default地址
    int setAsDefaultAddress(String phone, Integer rank);    // 將某一地址設置成默認地址

    /** 常見問題 */
    List<String> getQuestions(String catalog);  // 根據問題分類顯示常見的問題
    String getAnswer(String question);     // 根據常見問題查找相應的解答

    /** 意見反饋 */

    /** 關於閒置重重 */

}
