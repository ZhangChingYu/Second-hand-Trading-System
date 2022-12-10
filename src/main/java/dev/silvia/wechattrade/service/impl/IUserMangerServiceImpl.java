package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dto.feedback.AuthDirectoryDto;
import dev.silvia.wechattrade.dto.feedback.DirectoryDto;
import dev.silvia.wechattrade.dto.feedback.FeedUserDto;
import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.Buyer;
import dev.silvia.wechattrade.entity.Feedback;
import dev.silvia.wechattrade.entity.Seller;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.UserMangerDirectory;
import dev.silvia.wechattrade.handlers.common.repository.BuyerRepository;
import dev.silvia.wechattrade.handlers.common.repository.FeedbackRepository;
import dev.silvia.wechattrade.handlers.common.repository.SellerRepository;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.fileHandler.DeleteFile;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IUserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class IUserMangerServiceImpl extends ServiceImpl implements IUserMangerService {
    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BuyerRepository buyerRepository;
    @Autowired
    SellerRepository sellerRepository;
    private Result res;
    @Autowired
    private ReadFile readFile = new ReadFile();
    @Autowired
    private DeleteFile deleteFile = new DeleteFile();

    List<DirectoryDto> directoryList1;  //交易目录
    List<DirectoryDto> directoryList2;  //违规目录
    @Override
    public Result addFeedBack(FeedbackDto feed) {
        Feedback feedback=new Feedback();
        feedback.setContent(transferUTF8.CtoUTF8(feed.getContent()));
        feedback.setPhone(feed.getPhone());
        feedback.setFeedTime(new Date());
        feedback.setNumber(OrderCodeUtils.createCode("FD"));
        feedback.setStatus(0);
        feedbackRepository.save(feedback);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result deleteFeedback(String number) {
        feedbackRepository.deleteByNumber(number);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result swapAuthority(List<Integer> ids,Integer violations) {
        for (Integer s : ids) {
            User user = userRepository.findById(s).get();
            if (violations == 0) {
                user.setAuthority(2);
            } else {
                if (user.getPicture() == null) {
                    user.setAuthority(1);
                } else {
                    user.setAuthority(0);
                }
            }
            userRepository.save(user);
        }
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result selectFeedback() {
        FeedbackDto feed=new FeedbackDto();
        List<FeedbackDto> feedList=new ArrayList<>();
        List<Feedback> backList=feedbackRepository.findAll(Sort.by(Sort.Direction.DESC,"feedTime"));
        for (Feedback fb : backList) {
            User user=userRepository.findByPhone(fb.getPhone()).get();
            if(user.getAuthority()!=2){
                feed.setPhone(fb.getPhone());
                feed.setFeedTime(fb.getFeedTime());
                feed.setContent(transferUTF8.UTF8toC(fb.getContent()));
                feed.setName(transferUTF8.UTF8toC(user.getUserName()));
                feed.setNumber(fb.getNumber());
                feed.setStatus(fb.getStatus());
                feedList.add(feed);
            }
        }
        res=new Result(ResultCode.SUCCESS,feedList);
        return res;
    }

    @Override
    public Result userManageAll() {
        List<User> userList=userRepository.findAll();
        List<FeedUserDto> feedList=packFeedUserDto(userList);
        res=new Result(ResultCode.SUCCESS,feedList);
        return res;
    }

    @Override
    public Result userManageSelect(Integer number1, Integer number2, Integer number3) {
        List<FeedUserDto> feed;
        List<User> user;
        if(number1==-1){
            if(number2==-1){
                if(number3==-1){
                    //所有
                    user=userRepository.findAll();
                    feed=packFeedUserDto(user);
                }
                else{
                    user=userRepository.findAll();
                    //按交易范围
                    feed=selectByRange(number2,number3,user);
                }
            }
            else{
                //按违规范围、按交易和违规范围
                user=userRepository.findAll();
                feed=selectByRange(number2,number3,user);
            }
        }
        else{
            //按权限搜索
            //权限和违规
            //交易、权限和违规
            if(number3==-1){
                //只按权限搜索
                user=userRepository.findByAuthority(number1);
                feed=packFeedUserDto(user);
            }
            else{
                //按权限和交易范围
                user=userRepository.findByAuthority(number1);
                feed=selectByRange(number2,number3,user);
            }
        }
        res=new Result(ResultCode.SUCCESS,feed);
        return res;
    }

    @Override
    public Result deleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            User user=userRepository.findById(id).get();
            if(user.getAvatar()!=null){
                deleteFile.deleteOneFile(readFile.getAvatarPicture(user.getPhone()));
            }
            if(user.getPicture()!=null){
                deleteFile.deleteOneFile(readFile.getAuthPicture(user.getPhone()));
            }
            userRepository.delete(user);
        }
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result fuzzySelect(String name1) {
        List<User> u=userRepository.findByRealNameLikeOrUserNameLike("%"+name1+"%","%"+name1+"%");
        List<FeedUserDto> feed=packFeedUserDto(u);
        res=new Result(ResultCode.SUCCESS,feed);
        return res;
    }

    @Override
    public Result swapPassword(List<Integer> ids) {
        for (Integer id : ids) {
            User user = userRepository.findById(id).get();
            user.setPassword("123456");
            userRepository.save(user);
        }
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result selectAuth() {
        List<AuthDirectoryDto> authList=new ArrayList<>();
        AuthDirectoryDto auth;
        auth=new AuthDirectoryDto();
        auth.setNumber(0);
        auth.setName("已认证");
        authList.add(auth);
        auth=new AuthDirectoryDto();
        auth.setNumber(1);
        auth.setName("未认证");
        authList.add(auth);
        auth=new AuthDirectoryDto();
        auth.setNumber(2);
        auth.setName("已禁用");
        authList.add(auth);
        res=new Result(ResultCode.SUCCESS,authList);
        return res;
    }

    @Override
    public Result selectTrade() {
        List<Integer> trade=UserMangerDirectory.createTrade();
        directoryList1=UserMangerDirectory.createDirectory(trade.get(0),trade.get(1));
        res=new Result(ResultCode.SUCCESS,directoryList1);
        return res;
    }

    @Override
    public Result selectViolation() {
        List<Integer> trade=UserMangerDirectory.createViolation();
        directoryList2=UserMangerDirectory.createDirectory(trade.get(0),trade.get(1));
        res=new Result(ResultCode.SUCCESS,directoryList2);
        return res;
    }

    @Override
    public Result selectUser(String phone) {
        User user=userRepository.findByPhone(phone).get();
        FeedUserDto feed=new FeedUserDto();
        feed.setId(user.getId());
        feed.setRealName(transferUTF8.UTF8toC(user.getRealName()));
        feed.setUserName(transferUTF8.UTF8toC(user.getUserName()));
//        //图片路径
        String picture1;
        if (user.getAvatar()==null) {
            //默认图片
            picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
            feed.setAvatar(picture1);
        } else {
            picture1 = ReadFile.getBaseFile(readFile.getAvatarPicture(user.getPhone()));
            feed.setAvatar(picture1);
        }
        Integer auth = user.getAuthority();
        if (auth == 0) {
            feed.setAuthority("已认证");
        } else if (auth == 1) {
            feed.setAuthority("未认证");
        } else {
            feed.setAuthority("已禁用");
        }
        feed.setViolationCount(user.getViolationCount());
        List<Buyer> buyer = buyerRepository.findAllByPhone(phone);
        List<Seller> seller = sellerRepository.findAllByPhone(phone);
        feed.setBuy(buyer.size());
        feed.setSell(seller.size());
        res=new Result(ResultCode.SUCCESS,feed);
        return res;
    }

    List<FeedUserDto> packFeedUserDto(List<User> userList){
        List<FeedUserDto> feedList=new ArrayList<>();
        for (User user : userList) {
            FeedUserDto feed=new FeedUserDto();
            feed.setId(user.getId());
            feed.setRealName(transferUTF8.UTF8toC(user.getRealName()));
            feed.setUserName(transferUTF8.UTF8toC(user.getUserName()));
            //图片路径
            String picture1;
            if (user.getAvatar()==null) {
                //默认图片
                picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                feed.setAvatar(picture1);
            } else {
                picture1 = ReadFile.getBaseFile(readFile.getAvatarPicture(user.getPhone()));
                feed.setAvatar(picture1);
            }
            Integer auth = user.getAuthority();
            if (auth == 0) {
                feed.setAuthority("已认证");
            } else if (auth == 1) {
                feed.setAuthority("未认证");
            } else {
                feed.setAuthority("已禁用");
            }
            feed.setViolationCount(user.getViolationCount());
            String phone = user.getPhone();
            List<Buyer> buyer = buyerRepository.findAllByPhone(phone);
            List<Seller> seller = sellerRepository.findAllByPhone(phone);
            feed.setBuy(buyer.size());
            feed.setSell(seller.size());
            feedList.add(feed);
        }
        return feedList;
    }
    List<FeedUserDto> selectByRange(Integer number2,Integer number3,List<User> user){
        List<FeedUserDto> feed=new ArrayList<>();
        for (User user1 : user) {
            String phone = user1.getPhone();
            List<Buyer> buyer = buyerRepository.findAllByPhone(phone);
            List<Seller> seller=sellerRepository.findAllByPhone(phone);
            int count,upper1,lower1,violation,upper2,lower2;
            if(number2==-1){
                upper2=100000;
                lower2=0;
            }
            else{
                upper2=directoryList2.get(number2).getRange().get(1);
                lower2=directoryList2.get(number2).getRange().get(0);
            }
            if(number3==-1){
                upper1=100000;
                lower1=0;
            }
            else{
                upper1=directoryList1.get(number3).getRange().get(1);
                lower1=directoryList1.get(number3).getRange().get(0);
            }
            count=buyer.size()+seller.size();
            if(user1.getViolationCount()==null){
                violation=0;
            }
            else
                violation=user1.getViolationCount();
            if(count<=upper1&&count>=lower1&&violation<=upper2&&violation>=lower2){
                FeedUserDto feed1=new FeedUserDto();
                feed1.setId(user1.getId());
                feed1.setRealName(transferUTF8.UTF8toC(user1.getRealName()));
                feed1.setUserName(transferUTF8.UTF8toC(user1.getUserName()));
                //图片路径
                String picture1;
                if (user1.getAvatar()==null) {
                    //默认图片
                    picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                    feed1.setAvatar(picture1);
                } else {
                    picture1 = ReadFile.getBaseFile(readFile.getAvatarPicture(user1.getPhone()));
                    feed1.setAvatar(picture1);
                }
                Integer auth = user1.getAuthority();
                if (auth == 0) {
                    feed1.setAuthority("已认证");
                } else if (auth == 1) {
                    feed1.setAuthority("未认证");
                } else {
                    feed1.setAuthority("已禁用");
                }
                feed1.setViolationCount(violation);
                feed1.setBuy(buyer.size());
                feed1.setSell(seller.size());
                feed.add(feed1);
            }
        }
        return feed;
    }

}
