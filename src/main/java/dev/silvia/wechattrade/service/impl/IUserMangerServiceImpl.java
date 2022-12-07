package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dto.feedback.FeedUserDto;
import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.Feedback;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.FeedbackRepository;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IUserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class IUserMangerServiceImpl extends ServiceImpl implements IUserMangerService {
    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    UserRepository userRepository;
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
    public Result swapAuthority(String phone,Integer violations) {
        User user=userRepository.findByPhone(phone).get();
        if(violations==0){
            user.setAuthority(2);
        }
        else{
            user.setAuthority(1);
            user.setViolationCount(0);
        }
        userRepository.save(user);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result selectFeedback() {
        FeedbackDto feed=new FeedbackDto();
        List<FeedbackDto> feedList=new ArrayList<>();
        List<Feedback> backList=feedbackRepository.findAll();
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
    public Result userFeedback() {
        FeedUserDto feed=new FeedUserDto();
        List<FeedUserDto> feedList=new ArrayList<>();
        List<User> userList=userRepository.findByViolationCountGreaterThanEqual(1);
        for (User user : userList) {
            feed.setPhone(user.getPhone());
            feed.setName(transferUTF8.UTF8toC(user.getUserName()));
            //图片路径
            List<String> picture1;
            if (user.getAvatar().isEmpty()) {
                //默认图片
                picture1 = Collections.singletonList(ReadFile.getBaseFile(FileDirector.AVATAR_URL));
                feed.setAvatar(picture1.get(0));
            } else {
                picture1 = Collections.singletonList(ReadFile.getBaseFile(user.getAvatar()));
                feed.setAvatar(picture1.get(0));
            }
            feed.setViolationCount(user.getViolationCount());
            feedList.add(feed);
        }
        res=new Result(ResultCode.SUCCESS,feedList);
        return res;
    }
}
