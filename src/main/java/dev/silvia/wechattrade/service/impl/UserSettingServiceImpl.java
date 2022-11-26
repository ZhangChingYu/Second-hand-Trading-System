package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.address.AddressCreateDto;
import dev.silvia.wechattrade.dto.address.AddressUpdateDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.AddressPacking;
import dev.silvia.wechattrade.handlers.CheckUserAuthority;
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
    TransferUTF8 transferUTF8;
    @Autowired
    private CheckUserAuthority CUA;

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
    // 不同地址用"#"區隔
    @Override
    public int addAddress(AddressCreateDto dto) {   // 格式是 add1#add2#add3#add4#.....addN#
        if(!CUA.isAuthorized(dto.getPhone())){
            return 403; // 用戶無權限
        }
        String sql;
        String new_address;
        User user = getUser(dto.getPhone());
        String old_address = user.getAddress();
        String address = transferUTF8.CtoUTF8(new AddressPacking().ObjectToAddress(dto.getName(), dto.getReceiverPhone(), dto.getRegion(), dto.getAddressDetail()));
        if(dto.getIsDefaultAddress() == 1){
            if(checkDefaultExist(dto.getPhone())){  // 如果是空的
                sql = "update user_info set default_address='"+address+"' where phone='"+dto.getPhone()+"'";
            }else { // 需要把現有的default地址放到其他地址裡
                new_address = user.getDefaultAddress() + transferUTF8.CtoUTF8("#") + old_address;
                sql = "update user_info set default_address='"+address+"', address='"+new_address+"' where phone='"+dto.getPhone()+"'";
            }
        }else { // 直接加在其他地址裡
            new_address = address+transferUTF8.CtoUTF8("#")+old_address;
            sql = "update user_info set address='"+new_address+"' where phone='"+dto.getPhone()+"'";
        }
        return checkUpdate(sql);
    }

    @Override
    public int addressEditing(AddressUpdateDto dto) {   // 更新地址
        User user = getUser(dto.getPhone());
        String sql;
        String new_address = new AddressPacking().ObjectToAddress(dto.getName(), dto.getReceiverPhone(), dto.getRegion(), dto.getAddressDetail());
        if(dto.getRank()==0){   // 直接更新
            if(user.getDefaultAddress() == null || user.getDefaultAddress().isEmpty()){
                return 4001; // 請求數據不存在
            }
            System.out.println("is not null: " + user.getDefaultAddress());
            sql = "update user_info set default_address='"+transferUTF8.CtoUTF8(new_address)+"' where phone='"+dto.getPhone()+"'";
        }else { // 其他都不能直接更新
            String[] old_address = splitCheck( transferUTF8.UTF8toC(user.getAddress()));
            if(old_address == null || dto.getRank() < 0 || dto.getRank() > old_address.length){
                return 4002; // 請求格式有誤，沒有這個rank或沒有地址存在
            }
            old_address[dto.getRank()-1] = new_address;
            String putTogether = "";
            for(int i = 0 ; i < old_address.length; i++){   // 拼起來
                putTogether+=old_address[i]+"#";
            }
            sql = "update user_info set address='"+transferUTF8.CtoUTF8(putTogether)+"' where phone='"+dto.getPhone()+"'";
        }
        return checkUpdate(sql);
    }

    @Override
    public int deleteAddress(String phone, Integer rank) {
        String sql;
        User user = getUser(phone);
        if(rank == 0){  // 直接刪除
            sql = "update user_info set default_address='' where phone='"+phone+"'";
        }else {
            String[] old_address = splitCheck(transferUTF8.UTF8toC(user.getAddress()));
            if(old_address == null || rank > old_address.length || rank < 0){
                return 400; // 請求地址不存在
            }
            String new_address;
            new_address = transferUTF8.UTF8toC(user.getAddress()).replaceAll(old_address[rank-1]+"#","");
            sql = "update user_info set address='"+transferUTF8.CtoUTF8(new_address)+"' where phone='"+phone+"'";
        }
        return checkUpdate(sql);
    }

    @Override
    public List<AddressVo> showAllAddress(String phone) {
        if(!CUA.isAuthorized(phone)){
            return null;
        }
        User user = getUser(phone);
        String address = transferUTF8.UTF8toC(user.getAddress());
        String default_address = transferUTF8.UTF8toC(user.getDefaultAddress());
        List<AddressVo> addressVos = new AddressPacking().StringToAddressVo(address,default_address);
        return addressVos;
    }

    @Override
    public AddressVo getDefaultAddress(String phone) {
        User user = getUser(phone);
        String default_address = user.getDefaultAddress();
        AddressVo addressVo = new AddressPacking().StringToAddressVo(transferUTF8.UTF8toC(default_address),0);
        return addressVo;
    }

    @Override
    public int setAsDefaultAddress(String phone, Integer rank) {
        if(rank == 0){  // 不需更新
            return 200;
        }
        String sql;
        User user = getUser(phone);
        String old_default = transferUTF8.UTF8toC(user.getDefaultAddress());
        String[] old_address = splitCheck(transferUTF8.UTF8toC(user.getAddress()));
        if(old_address == null || rank > old_address.length || rank < 0){
            return 400; // 沒有地址存在
        }
        String new_address, new_default;
        if(old_default == null || old_default.isEmpty()){  // 若default是空的，將old_address[rank-1]移過去
            new_default = transferUTF8.CtoUTF8(old_address[rank-1]);
            new_address = transferUTF8.UTF8toC(user.getAddress()).replaceAll(old_address[rank-1]+"#","");
            sql = "update user_info set default_address='" + new_default + "', address='"+ transferUTF8.CtoUTF8(new_address)+"' where phone='"+phone+"'";
        }
        else {  // 位置互換
            new_default = old_address[rank-1];
            old_address[rank-1] = old_default;
            new_address = "";
            for(int i = 0; i < old_address.length; i++){
                new_address += old_address[i]+"#";
            }
            sql = "update user_info set default_address='"+transferUTF8.CtoUTF8(new_default)+"', address='"+transferUTF8.CtoUTF8(new_address)+"' where phone='"+phone+"'";
        }
        return checkUpdate(sql);
    }

    @Override
    public List<String> getQuestions(String catalog) {
        return null;
    }

    @Override
    public String getAnswer(String question) {
        return null;
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }

    private boolean checkDefaultExist(String phone){    // 如果存在返回false
        User user = getUser(phone);
        return user.getDefaultAddress().isEmpty();
    }

    private String[] splitCheck(String decodeAddresses){
        if(decodeAddresses == null){
            return null;
        }
        return decodeAddresses.split("#");
    }

    private Integer checkUpdate(String sql){
        int check = jdbcTemplate.update(sql);
        if(check > 0){
            return 800;     // 更新成功
        }
        return 400; // 沒有數據更新
    }
}
