package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.AddressCreateDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.service.IUserSettingService;
import dev.silvia.wechattrade.vo.AddressVo;
import dev.silvia.wechattrade.vo.AuthenticationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSettingServiceImpl extends ServiceImpl<UserDao, User> implements IUserSettingService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson = new Gson();

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Override
    public int swapRelatedPhone(String phone) {
        return 0;
    }

    @Override
    public int realNameAuthentication(AuthenticationVo authenticationVo) {
        return 0;
    }

    @Override
    public int frozeAccount(String phone) {
        return 0;
    }

    @Override
    public int unfrozeAccount(String phone) {
        return 0;
    }

    @Override
    public List<String> AccountLog(String phone) {
        return null;
    }

    // 地址在數據庫中的統一格式(收件人-手機號-所在地區-詳細地址)
    @Override
    public int addAddress(String phone, AddressCreateDto addressCreateDto) {
        // 用來存放地址的字串
        String addressFormat = addressCreateDto.getName()+"-"+addressCreateDto.getPhone()+"-"+addressCreateDto.getRegion()+"-"+addressCreateDto.getAddressDetail();
        // 將字串進行UTF-8編碼保存
        String codeAddress = transferUTF8.CtoUTF8(addressFormat);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userDao.selectOne(wrapper);
        String address0 = user.getDefaultAddr();
        String address1 = user.getAddrOne();
        String address2 = user.getAddrTwo();
        String address3 = user.getAddrThree();
        if(user != null){
            if(address3=="null"){  // 確認地址沒有滿(可添加新地址)
                if(addressCreateDto.isDefaultAddress()){    // 用戶希望將當前新增的地址設為默認地址
                    if(address0 != "null"){     // 若已存在默認地址，則將所有地址的先後順序後移
                        String sql = "update user_info set default_addr='"+ codeAddress +"', addr_one='"+address0+ "', addr_two='"+address1+"', addr_three='"+address2+"' where phone='"+ phone+"'";
                        return checkUpdate(sql);
                    }
                    else {  // 不存在默認地址，則該地址自動存為默認地址
                        String sql = "update user_info set default_addr='"+codeAddress+"' where phone='"+phone+"'";
                        return checkUpdate(sql);
                    }
                }
                else {  // 不需要設為默認地址
                    if(address0 == "null"){     // 沒有地址狀態，直接設為默認
                        String sql = "update user_info set default_addr='"+codeAddress+"' where phone='"+phone+"'";
                        return checkUpdate(sql);
                    } else if (address1 == "null") {    // 設為地址一

                    } else if (address2 == "null") {    // 設為地址二

                    }else { // 設為地址三

                    }
                }
            }
            // 地址已滿，不可添加
        }
        // 用戶不存在，系統出錯
        return 0;
    }

    @Override
    public int AddressSetting(Integer type, String address) {
        return 0;
    }

    @Override
    public int deleteAddress(Integer type) {
        return 0;
    }

    @Override
    public List<AddressVo> showAllAddress(String phone) {
        return null;
    }

    @Override
    public List<String> getQuestions(String catalog) {
        return null;
    }

    @Override
    public String getAnswer(String question) {
        return null;
    }

    private Integer checkUpdate(String sql){
        int check = jdbcTemplate.update(sql);
        if(check > 0){
            return 800;     // 更新成功
        }
        return 200; // 沒有數據更新
    }
}
