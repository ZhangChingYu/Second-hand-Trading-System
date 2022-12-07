package dev.silvia.wechattrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import dev.silvia.wechattrade.dto.feedback.FeedbackDto;
import dev.silvia.wechattrade.dto.response.Result;

public interface IUserMangerService extends IService {

    Result addFeedBack(FeedbackDto feed);

    Result deleteFeedback(String number);

    Result swapAuthority(String phone,Integer violations);

    Result selectFeedback();

    Result userFeedback();
}
