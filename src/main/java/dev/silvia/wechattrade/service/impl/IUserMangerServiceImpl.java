package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import dev.silvia.wechattrade.handlers.common.repository.BuyerRepository;
import dev.silvia.wechattrade.handlers.common.repository.FeedbackRepository;
import dev.silvia.wechattrade.handlers.common.repository.SellerRepository;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
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
                if (user.getPicture().isEmpty() || user.getPicture() == null) {
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
    public Result userManageSelect(String type, Integer upper, Integer lower) {
        List<FeedUserDto> feed = new ArrayList<>();
        if(Objects.equals(type, "可买卖")){
            List<User> user=userRepository.findByAuthority(0);
            List<User> user1=userRepository.findByAuthority(1);
            user.addAll(user1);
            feed=packFeedUserDto(user);
        }
        else if(Objects.equals(type, "违规")){
            List<User> user=userRepository.findByViolationCountBetween(lower,upper);
            feed=packFeedUserDto(user);
        }
        else if(Objects.equals(type, "已禁用")){
            List<User> user=userRepository.findByAuthority(2);
            feed=packFeedUserDto(user);
        }
        else{
            List<User> userList=userRepository.findAll();
            for (User user : userList) {
                String phone = user.getPhone();
                List<Buyer> buyer = buyerRepository.findAllByPhone(phone);
                List<Seller> seller = sellerRepository.findAllByPhone(phone);
                if(buyer.size()<=upper&&buyer.size()>=lower){
                    FeedUserDto feed1=new FeedUserDto();
                    feed1.setId(user.getId());
                    feed1.setRealName(transferUTF8.UTF8toC(user.getRealName()));
                    feed1.setUserName(transferUTF8.UTF8toC(user.getUserName()));

                    String picture1;
                    if (user.getAvatar().isEmpty()||user.getAvatar()==null) {
                      //默认图片
                      picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                      feed1.setAvatar(picture1);
                    } else {
                      picture1 = ReadFile.getBaseFile(user.getAvatar());
                      feed1.setAvatar(picture1);
                    }
                    Integer auth = user.getAuthority();
                    if (auth == 0) {
                        feed1.setAuthority("可买卖");
                    } else if (auth == 1) {
                        feed1.setAuthority("可买卖");
                    } else {
                        feed1.setAuthority("已禁用");
                    }
                    feed1.setViolationCount(user.getViolationCount());
                    feed1.setBuy(buyer.size());
                    feed1.setSell(seller.size());
                    feed.add(feed1);
                }
            }

        }
        res=new Result(ResultCode.SUCCESS,feed);
        return res;
    }

    @Override
    public Result deleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userRepository.delete(userRepository.findById(id).get());
        }
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result fuzzySelect(String name1) {
        List<User> u=userRepository.findByRealNameLike("%"+name1+"%");
        List<User> u1=userRepository.findByUserNameLike("%"+name1+"%");
        List<FeedUserDto> feed=packFeedUserDto(u);
        List<FeedUserDto> feed1=packFeedUserDto(u1);
        feed.addAll(feed1);
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

    List<FeedUserDto> packFeedUserDto(List<User> userList){
        List<FeedUserDto> feedList=new ArrayList<>();
        for (User user : userList) {
            FeedUserDto feed=new FeedUserDto();
            feed.setId(user.getId());
            feed.setRealName(transferUTF8.UTF8toC(user.getRealName()));
            feed.setUserName(transferUTF8.UTF8toC(user.getUserName()));
            //图片路径
            String picture1;
            if (user.getAvatar().isEmpty()||user.getAvatar()==null) {
                //默认图片
                picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                feed.setAvatar(picture1);
            } else {
                picture1 = ReadFile.getBaseFile(user.getAvatar());
                feed.setAvatar(picture1);
            }
            Integer auth = user.getAuthority();
            if (auth == 0) {
                feed.setAuthority("可买卖");
            } else if (auth == 1) {
                feed.setAuthority("可买卖");
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
}
