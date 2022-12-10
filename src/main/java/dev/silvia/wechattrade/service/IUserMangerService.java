package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.dto.response.Result;

import java.util.List;

public interface IUserMangerService extends IService {
    Result addFeedBack(FeedbackDto feed);

    Result deleteFeedback(String number);

    Result swapAuthority(List<Integer> ids,Integer violations);

    Result selectFeedback();

    Result userManageAll();

    Result userManageSelect(Integer number1, Integer number2, Integer number3);

    Result deleteUser(List<Integer> ids);

    Result fuzzySelect(String name1);

    Result swapPassword(List<Integer> ids);

    Result selectAuth();

    Result selectTrade();

    Result selectViolation();

    Result selectUser(String phone);
}
