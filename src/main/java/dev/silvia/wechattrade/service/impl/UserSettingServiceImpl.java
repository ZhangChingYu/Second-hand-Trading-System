package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.AuthenticationRequestDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.address.AddressCreateDto;
import dev.silvia.wechattrade.dto.address.AddressUpdateDto;
import dev.silvia.wechattrade.dto.authentication.AuthRequestDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.AuthenticationRequest;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.AddressPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.handlers.fileHandler.WriteFile;
import dev.silvia.wechattrade.service.IUserSettingService;
import dev.silvia.wechattrade.vo.AddressVo;
import dev.silvia.wechattrade.vo.AuthenticationVo;
import dev.silvia.wechattrade.vo.FeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserSettingServiceImpl extends ServiceImpl<UserDao, User> implements IUserSettingService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationRequestDao authRequestDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    TransferUTF8 transferUTF8;
    @Autowired
    private ReadFile readFile;
    @Autowired
    private WriteFile writeFile;

    @Autowired
    private UserRepository accountRepository;

    private Result res;

    @Autowired
    private Optional<User> user;

    private String help_url = FileDirector.HELP_URL;

    private User user2;

    //??????????????????
    @Override
    public Result PersonalInfo(User request) {
        try {
            //??????utf8
            request=transferUTF8.switchUtf8(request);
            //??????
            accountRepository.save(request);
            res=new Result(ResultCode.SUCCESS);
            return res;
        } catch (Exception e) {
            res=new Result(ResultCode.FAIL);
            return res;
        }
    }
    //??????????????????
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
            //??????utf8
                    us=transferUTF8.switchUtf8Tc(us);
                    us.setAvatar(ReadFile.getBaseFile(us.getAvatar()));
                    us.setPicture(ReadFile.getBaseFile(us.getPicture()));
                    res=new Result(ResultCode.SUCCESS,us);
                    return res;
                }

        );
    }
    //????????????
    @Override
    public Result authentication(AuthenticationVo request) {
        try{
            User user1=accountRepository.findByPhone(request.getPhone()).get();
            user1.setIdCard(request.getIdNumber());
            user1.setRealName(request.getRealName());
            //List<String> pictures = readFile.getPicturesBase64(request.getIdCardPics().size(), request.getIdCardPics());
            if(writeFile.storeAuthenticationPicture(request.getPhone(),request.getIdCardPic())==808){
                res=new Result(ResultCode.FAIL);
                return res;
            }
            String pictures = readFile.getAuthPicture(request.getPhone());
            user1.setAuthority(0);
            user1.setPicture(pictures);
            res=new Result(ResultCode.SUCCESS);
            user1=transferUTF8.switchUtf8(user1);
            accountRepository.save(user1);
            return res;
        } catch (Exception e) {
            res=new Result(ResultCode.FAIL);
            return res;
        }
    }

    @Override
    public Integer UserPostAuthenticationRequest(AuthRequestDto dto) {
        User requester = getUser(dto.getPhone());
        if(requester == null){
            return 422; // ???????????????
        }
        if(requester.getAuthority() == 0){
            return 200; // ???????????????????????????
        }
        if(checkIsAuthSent(dto.getPhone())){
            return 203; // ??????????????????????????????????????????
        }
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setStatus(0);   // ?????????
        authRequest.setDate(new Date());
        authRequest.setPhone(dto.getPhone());
        authRequest.setRealName(transferUTF8.CtoUTF8(dto.getRealName()));
        authRequest.setIdNumber(dto.getIdNumber());
        if(authRequestDao.insert(authRequest) > 0){
            if(writeFile.storeAuthPicAtTemp(dto.getPhone(), dto.getIdCardPic()) == 800){
                return 404; // ??????????????????
            }
            return 201; // ????????????
        }
        return 422; // ?????????????????????
    }

    @Override
    public Integer CheckUserAuthenticationStatus(String phone) {
        User check = getUser(phone);
        if(check.getAuthority() == 0){
            return 201; // ?????????????????????
        } else {
            QueryWrapper<AuthenticationRequest> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", phone);
            List<AuthenticationRequest> requests = authRequestDao.selectList(wrapper);
            if(!requests.isEmpty()){
                for(AuthenticationRequest request : requests){
                    if(request.getStatus() == 0){   // ?????????
                        return 202; // ???????????????
                    }
                }
            }
        }
        return 203; // ?????????????????????(????????????/??????????????????)
    }

    @Override
    public Result swapRelatedAvatar(String phone,String avatar) {
        User user1=accountRepository.findByPhone(phone).get();
        user1.setAvatar(avatar);
        accountRepository.save(user1);
        res=new Result(ResultCode.SUCCESS,user1);
        return res;
    }

    @Override
    public Result swapRelatedPhone(String phone) {
        User user1=accountRepository.findByPhone(phone).get();
        user1.setPhone(phone);
        accountRepository.save(user1);
        res=new Result(ResultCode.SUCCESS,user1);
        return res;
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

    // ????????????????????????????????????(?????????%?????????%????????????%????????????)
    // ???????????????"#"??????
    @Override
    public int addAddress(AddressCreateDto dto) {   // ????????? add1#add2#add3#add4#.....addN#
        if(getUser(dto.getPhone()).getAuthority()!=0){
            return 403; // ???????????????
        }
        String sql;
        String new_address;
        User user = getUser(dto.getPhone());
        String old_address = user.getAddress();
        String address = transferUTF8.CtoUTF8(new AddressPacking().ObjectToAddress(dto.getReceiverName(), dto.getReceiverPhone(), dto.getRegion(), dto.getAddressDetail()));
        if(dto.getIsDefaultAddress() == 1){
            if(checkDefaultExist(dto.getPhone())){  // ???????????????
                sql = "update user_info set default_address='"+address+"' where phone='"+dto.getPhone()+"'";
            }else { // ??????????????????default???????????????????????????
                new_address = user.getDefaultAddress() + transferUTF8.CtoUTF8("#") + old_address;
                sql = "update user_info set default_address='"+address+"', address='"+new_address+"' where phone='"+dto.getPhone()+"'";
            }
        }else { // ???????????????????????????
            new_address = address+transferUTF8.CtoUTF8("#")+old_address;
            sql = "update user_info set address='"+new_address+"' where phone='"+dto.getPhone()+"'";
        }
        return checkUpdate(sql);
    }


    @Override
    public int addressEditing(AddressUpdateDto dto) {   // ????????????
        User user = getUser(dto.getPhone());
        String sql;
        String new_address = new AddressPacking().ObjectToAddress(dto.getReceiverName(), dto.getReceiverPhone(), dto.getRegion(), dto.getAddressDetail());
        if(dto.getRank()==0){   // ????????????
            if(user.getDefaultAddress() == null || user.getDefaultAddress().isEmpty()){
                return 4001; // ?????????????????????
            }
            System.out.println("is not null: " + user.getDefaultAddress());
            sql = "update user_info set default_address='"+transferUTF8.CtoUTF8(new_address)+"' where phone='"+dto.getPhone()+"'";
        }else { // ???????????????????????????
            String[] old_address = splitCheck( transferUTF8.UTF8toC(user.getAddress()));
            if(old_address == null || dto.getRank() < 0 || dto.getRank() > old_address.length){
                return 4002; // ?????????????????????????????????rank?????????????????????
            }
            old_address[dto.getRank()-1] = new_address;
            String putTogether = "";
            for(int i = 0 ; i < old_address.length; i++){   // ?????????
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
        if(rank == 0){  // ????????????
            sql = "update user_info set default_address='' where phone='"+phone+"'";
        }else {
            String[] old_address = splitCheck(transferUTF8.UTF8toC(user.getAddress()));
            if(old_address == null || rank > old_address.length || rank < 0){
                return 400; // ?????????????????????
            }
            String new_address;
            new_address = transferUTF8.UTF8toC(user.getAddress()).replaceAll(old_address[rank-1]+"#","");
            sql = "update user_info set address='"+transferUTF8.CtoUTF8(new_address)+"' where phone='"+phone+"'";
        }
        return checkUpdate(sql);
    }

    @Override
    public List<AddressVo> showAllAddress(String phone) {
        if(getUser(phone).getAuthority()!=0){
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
        if(rank == 0){  // ????????????
            return 200;
        }
        String sql;
        User user = getUser(phone);
        String old_default = transferUTF8.UTF8toC(user.getDefaultAddress());
        String[] old_address = splitCheck(transferUTF8.UTF8toC(user.getAddress()));
        if(old_address == null || rank > old_address.length || rank < 0){
            return 400; // ??????????????????
        }
        String new_address, new_default;
        if(old_default == null || old_default.isEmpty()){  // ???default???????????????old_address[rank-1]?????????
            new_default = transferUTF8.CtoUTF8(old_address[rank-1]);
            new_address = transferUTF8.UTF8toC(user.getAddress()).replaceAll(old_address[rank-1]+"#","");
            sql = "update user_info set default_address='" + new_default + "', address='"+ transferUTF8.CtoUTF8(new_address)+"' where phone='"+phone+"'";
        }
        else {  // ????????????
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
    public List<String> getQuestionCatalog(){
        List<String> catalogs = readFile.getSubFileNames(help_url);
        return catalogs;
    }

    @Override
    public List<String> getQuestions(String catalog) {
        String root = help_url +"/"+catalog;
        List<String> questions = readFile.getSubFileNames(root);
        if(questions != null || !questions.isEmpty()){
            for(int i = 0 ; i < questions.size(); i++){
                questions.set(i, questions.get(i).replaceAll(".txt", ""));
            }
        }
        return questions;
    }

    @Override
    public String getAnswer(String catalog, String question) {
        String filePath = help_url +"/"+catalog+"/"+question+".txt";
        String answer = readFile.readHelpFile(filePath);
        return answer;
    }

    @Override
    public Integer sendFeedback(String phone, String content) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(System.currentTimeMillis());
        User user = getUser(phone);
        if (user != null) {     // ??????????????????
            // ?????????:?????????+??????+??????????????????:??????+????????????
            FeedbackVo feedback = new FeedbackVo();
            feedback.setContent(content);
            feedback.setDate(date);   // ??????????????????
            feedback.setFrom(transferUTF8.UTF8toC(user.getUserName())); // ???????????????
            feedback.setPhone(phone);
            feedback.setYear(date.substring(0,4));
            feedback.setMonth(date.substring(5,7));
            feedback.setTime(date.substring(11,13)+date.substring(14,16)+date.substring(17));
            return writeFile.writeFeedbackFile(feedback);
        }
        return 400; // ???????????????
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }

    private boolean checkDefaultExist(String phone){    // ??????????????????false
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
            return 800;     // ????????????
        }
        return 400; // ??????????????????
    }
    private boolean checkIsAuthSent(String phone){  // ????????????????????????????????????????????????
        QueryWrapper<AuthenticationRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        wrapper.eq("status", 0);
        if(authRequestDao.selectCount(wrapper) > 0){
            return true;    // ????????????????????????
        }
        return false;
    }
}

