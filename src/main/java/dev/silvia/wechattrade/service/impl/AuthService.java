package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.personal.AddressDto;
import dev.silvia.wechattrade.dto.personal.AuthDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceImpl<UserDao, User> implements IAuthService {
    @Autowired
    private UserRepository accountRepository;

    private Result res;

    @Autowired
    private Optional<User> user;

    private List<AddressDto> addre=new ArrayList<>();


    private User user2;
    //个人信息修改
    @Override
    public Result PersonalInfo(User request) {
        try {
            accountRepository.save(request);
            res=new Result(ResultCode.SUCCESS,request);
            return res;
        } catch (Exception e) {
            res=new Result(ResultCode.FAIL,request);
            return res;
        }
    }
    //个人信息获取
    @Override
    public Optional<Result> Acquisition(Integer id) {
        user=accountRepository.findById(id);
        return this.user.filter(us-> {
            try {
                return true;
            } catch (Exception e) {
                return false;
            }}
        ).map(us->{
                    res=new Result(ResultCode.SUCCESS,us);
                    return res;
                }

        );
    }
    //实名认证
    @Override
    public Result authentication(AuthDto request) {
        try{
            User user1=user.get();
            user1.setIdCard(request.getIdCard());
            user1.setRealName(request.getRealName());
            accountRepository.save(user1);
            res=new Result(ResultCode.SUCCESS,user1);
            return res;
        } catch (Exception e) {
            res=new Result(ResultCode.FAIL);
            return res;
        }
    }

    @Override
    public AddressDto splitaddress(Integer id,String address) {
        AddressDto add=new AddressDto();
        String[] split= address.split(",");
        if(split.length!=8){
            return add;
        }
        else{
            add.setId(id);
            add.setAid(split[1]);
            add.setName(split[2]);
            add.setPhone(split[3]);
            add.setProvince(split[4]);
            add.setCity(split[5]);
            add.setCounty(split[6]);
            add.setDetail(split[7]);
            return add;
        }
    }

    //地址管理
    @Override
    public List<AddressDto> getaddress(Integer id) {
        user2=user.get();
        addre.add(splitaddress(id,user2.getDefaultAddr()));
        addre.add(splitaddress(id,user2.getAddrOne()));
        addre.add(splitaddress(id,user2.getAddrTwo()));
        addre.add(splitaddress(id,user2.getAddrThree()));
        return addre;
    }

    @Override
    public Result address(AddressDto request) {
        String add;
        String j = "0";
        user2=user.get();
        for(int i=0;i<addre.size();i++){
            if(addre.get(i).getId()==null){
                j= String.valueOf(i);
                break;
            }
        }
        add=request.getId()+","+j+","+request.getName()+","+
                request.getPhone()+","+request.getProvince()+","+
                request.getCity()+","+request.getCounty()+","+
                request.getDetail();
        if(j=="0"){
            user2.setDefaultAddr(add);
        }
        if(j=="1"){
            user2.setAddrOne(add);
        }
        if(j=="2"){
            user2.setAddrTwo(add);
        }
        if(j=="3"){
            user2.setAddrThree(add);
        }
        accountRepository.save(user2);
        res=new Result(ResultCode.SUCCESS,user2);
        return res;
    }

    @Override
    public Result editaddress(AddressDto request) {
        String j=request.getAid();
        String add;
        user2=user.get();
        add=request.getId()+","+j+","+request.getName()+","+
                request.getPhone()+","+request.getProvince()+","+
                request.getCity()+","+request.getCounty()+","+
                request.getDetail();
        if(j=="0"){
            user2.setDefaultAddr(add);
        }
        if(j=="1"){
            user2.setAddrOne(add);
        }
        if(j=="2"){
            user2.setAddrTwo(add);
        }
        if(j=="3"){
            user2.setAddrThree(add);
        }
        accountRepository.save(user2);
        res=new Result(ResultCode.SUCCESS,user2);
        return res;
    }

    @Override
    public Result deleteaddress(AddressDto request) {
        String j=request.getAid();
        String add;
        add=" ";
        user2=user.get();
        if(j=="0"){
            user2.setDefaultAddr(add);
        }
        if(j=="1"){
            user2.setAddrOne(add);
        }
        if(j=="2"){
            user2.setAddrTwo(add);
        }
        if(j=="3"){
            user2.setAddrThree(add);
        }
        accountRepository.save(user2);
        res=new Result(ResultCode.SUCCESS,user2);
        return res;
    }

    @Override
    public Result defaulteaddress(AddressDto request) {
        String temp;
        String j=request.getAid();
        String defaultaddress=user.get().getDefaultAddr();
        AddressDto ad=splitaddress(request.getId(),defaultaddress);

        temp=ad.getId()+","+j+","+ad.getName()+","+
                ad.getPhone()+","+ad.getProvince()+","+
                ad.getCity()+","+ad.getCounty()+","+
                ad.getDetail();
        //默认地址
        String add;
        add=request.getId()+","+"0"+","+request.getName()+","+
                request.getPhone()+","+request.getProvince()+","+
                request.getCity()+","+request.getCounty()+","+
                request.getDetail();
        user2=user.get();
        user2.setDefaultAddr(add);
        if(j=="1"){
            user2.setAddrOne(temp);
        }
        if(j=="2"){
            user2.setAddrTwo(temp);
        }
        if(j=="3"){
            user2.setAddrThree(temp);
        }
        accountRepository.save(user2);
        res=new Result(ResultCode.SUCCESS,user2);
        return res;
    }
    //地址管理完
}
