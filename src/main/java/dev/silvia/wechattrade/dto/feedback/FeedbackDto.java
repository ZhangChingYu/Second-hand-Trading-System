package dev.silvia.wechattrade.dto.feedback;

import lombok.Data;

import java.util.Date;

@Data
public class FeedbackDto {
    private String number;  //反馈编号

    private String phone;    //反馈人电话

    private String name;    //反馈人姓名

    private Date feedTime;    //反馈时间

    private String content;  //反馈内容

    private Integer status;   //消息状态：已读为1，未读为0
}
