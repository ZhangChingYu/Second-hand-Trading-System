package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.comment.CommentDto;
import dev.silvia.wechattrade.dto.comment.CommentReplyDto;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {
    @Autowired
    @Resource
    ICommentService service;

    Gson gson = new Gson();

    @PassToken
    @RequestMapping(value = "/product/comment", method = RequestMethod.POST)
    public Integer postComment(@RequestBody CommentDto commentDto){
        return service.postComment(commentDto);
    }
    @PassToken
    @RequestMapping(value = "/product/reply", method = RequestMethod.POST)
    public Integer postReply(@RequestBody CommentReplyDto commentReplyDto){
        return service.replyComment(commentReplyDto);
    }
    @PassToken
    @RequestMapping(value = "/product/comment", method = RequestMethod.GET)
    public String showAllComments(HttpServletRequest request){
        String number = request.getParameter("number");
        return gson.toJson(service.getProductComments(number));
    }
}
