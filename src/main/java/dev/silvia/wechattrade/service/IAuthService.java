package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.personal.AddressDto;
import dev.silvia.wechattrade.dto.personal.AuthDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.User;

import java.util.List;
import java.util.Optional;

//个人信息（信息修改显示，实名认证，收货地址管理）
public interface IAuthService extends IService<User> {
    //信息修改显示
    Result PersonalInfo(User request);
    //获取个人信息
    Optional<Result> Acquisition(Integer id);
    //实名认证
    Result authentication(AuthDto request);

    AddressDto splitaddress(Integer id,String address);
    //获取地址
    List<AddressDto> getaddress(Integer id);
    //新增地址
    Result address(AddressDto request);
    //修改地址
    Result editaddress(AddressDto request);

    //删除地址
    Result deleteaddress(AddressDto request);

    //默认地址
    Result defaulteaddress(AddressDto request);
}
