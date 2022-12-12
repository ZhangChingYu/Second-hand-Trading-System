package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.address.AddressCreateDto;
import dev.silvia.wechattrade.dto.address.AddressUpdateDto;
import dev.silvia.wechattrade.dto.authentication.AuthRequestDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.vo.AddressVo;
import dev.silvia.wechattrade.vo.AuthenticationVo;

import java.util.List;
import java.util.Optional;

public interface IUserSettingService extends IService<User> {
    /** 帳號與安全設置 */
    Result swapRelatedPhone(String phone);     // 更換綁定的手機
    int realNameAuthentication(AuthenticationVo authenticationVo);           // 發送實名認證申請
    int frozeAccount(String phone);     // 凍結帳號，若用戶發現帳號被盜用
    int unfrozeAccount(String phone);       // 解凍帳號(由前端進行短信驗證，通過後再調用該功能)
    List<String> AccountLog(String phone);  // 顯示帳號日誌(了解帳號登入、綁定的詳細信息，若發現異常請儘快處理)

    /** 信息修改 */
    //信息修改显示
    Result PersonalInfo(User request);
    //获取个人信息
    Optional<Result> Acquisition(Integer id);
    //实名认证
    Result authentication(AuthenticationVo request);
    Integer UserPostAuthenticationRequest(AuthRequestDto authRequestDto);    // 用戶發送實名認證請求
    Integer CheckUserAuthenticationStatus(String phone);    // 檢查用戶實名認證狀態

    Result swapRelatedAvatar(String phone,String avatar);

    /** 消息接收設置 */


    /** 地址設置 */
    int addAddress(AddressCreateDto addressCreateDto);       // 添加地址
    int addressEditing(AddressUpdateDto addressUpdateDto);    // 更新指定地址，rank: 0(default), 1(備用1), 2(備用2), 3(備用3)
    int deleteAddress(String phone, Integer rank);    // 刪除地址
    List<AddressVo> showAllAddress(String phone);     // 顯示用戶所有地址
    AddressVo getDefaultAddress(String phone);  // 獲取default地址
    int setAsDefaultAddress(String phone, Integer rank);    // 將某一地址設置成默認地址

    /** 幫助 */
    List<String> getQuestionCatalog();     // 獲取問題分類
    List<String> getQuestions(String catalog);  // 根據問題分類顯示常見的問題
    String getAnswer(String catalog, String question);     // 根據常見問題查找相應的解答

    /** 意見反饋 */
    Integer sendFeedback(String phone, String content);     // 發送用戶反饋

    /** 關於閒置重重 */

}

