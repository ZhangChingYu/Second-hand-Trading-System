package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.product.CommentDto;
import dev.silvia.wechattrade.dto.product.CommentReplyDto;
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


    @RequestMapping(value = "/product/comment", method = RequestMethod.POST)
    public Integer postComment(@RequestBody CommentDto commentDto){
        return service.postComment(commentDto);
    }

    @RequestMapping(value = "/product/reply", method = RequestMethod.POST)
    public Integer postReply(@RequestBody CommentReplyDto commentReplyDto){
        return service.replyComment(commentReplyDto);
    }

    @RequestMapping(value = "/product/comment", method = RequestMethod.GET)
    public String showAllComments(HttpServletRequest request){
        String number = request.getParameter("number");
        return gson.toJson(service.getComments(number));
    }
}
