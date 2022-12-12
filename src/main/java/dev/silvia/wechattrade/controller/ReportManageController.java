package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.report.CommentReportDto;
import dev.silvia.wechattrade.dto.report.ProductReportDto;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.ICommentReportService;
import dev.silvia.wechattrade.service.IProductReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ReportManageController {
    @Autowired
    @Resource
    private IProductReportService PRService;

    @Autowired
    @Resource
    private ICommentReportService CRService;


    Gson gson = new Gson();

    @UserLoginToken
    @RequestMapping(value = "/product/report", method = RequestMethod.POST)
    public Integer sendProductReport(@RequestBody ProductReportDto productReportDto){
        return PRService.userPostReport(productReportDto);
    }
    //@UserLoginToken
    @RequestMapping(value = "/my/product/reports", method = RequestMethod.GET)
    public String showAllMyProductReport(HttpServletRequest request){
        String phone = request.getParameter("phone");
        return gson.toJson(PRService.showAllMyReport(phone));
    }
    //@UserLoginToken
    @RequestMapping(value = "/my/product/reports/status", method = RequestMethod.GET)
    public String showMyProductReportByStatus(HttpServletRequest request){
        String phone = request.getParameter("phone");
        Integer status = Integer.parseInt(request.getParameter("status"));
        return gson.toJson(PRService.showMyReportByStatus(phone, status));
    }
    @UserLoginToken
    @RequestMapping(value = "/my/product/report", method = RequestMethod.DELETE)
    public Integer deleteMyProductReport(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        return PRService.deleteMyReport(id);
    }
    @PassToken
    @RequestMapping(value = "/product/reports", method = RequestMethod.GET)
    public String showAllProductReport(){
        return gson.toJson(PRService.showAllProductReport());
    }
    @PassToken
    @RequestMapping(value = "/product/status/reports", method = RequestMethod.GET)
    public String showProductReportByStatus(HttpServletRequest request){
        Integer status = Integer.parseInt(request.getParameter("status"));
        return gson.toJson(PRService.showProductReportByStatus(status));
    }
    @PassToken
    @RequestMapping(value = "/product/report", method = RequestMethod.GET)
    public String readProductReport(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        return gson.toJson(PRService.readProductReportDetail(id));
    }
    @PassToken
    @RequestMapping(value = "/product/report/result", method = RequestMethod.PUT)
    public Integer processProductReport(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        String decision = param.get("decision").toString();
        String explain = param.get("explain").toString();
        return PRService.processProductReport(id, decision, explain);
    }
    @PassToken
    @RequestMapping(value = "/comment/report", method = RequestMethod.POST)
    public Integer sendCommentReport(@RequestBody CommentReportDto dto){
        return CRService.userPostReport(dto);
    }
    @PassToken
    @RequestMapping(value = "/comment/reports", method = RequestMethod.GET)
    public String showAllCommentReport(){
        return gson.toJson(CRService.showAllCommentReport());
    }
    @PassToken
    @RequestMapping(value = "/comment/reports/same", method = RequestMethod.GET)
    public String showCommentReportByCommentId(HttpServletRequest request){
        Integer comment_id = Integer.parseInt(request.getParameter("commentId"));
        return gson.toJson(CRService.showCommentReportByCommentId(comment_id));
    }
    @PassToken
    @RequestMapping(value = "/comment/reports/status", method = RequestMethod.GET)
    public String showCommentReportByStatus(HttpServletRequest request){
        Integer status = Integer.parseInt(request.getParameter("status"));
        return gson.toJson(CRService.showCommentReportByStatus(status));
    }
    @PassToken
    @RequestMapping(value = "/comment/report", method = RequestMethod.GET)
    public String readCommentReport(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter("id"));
        return gson.toJson(CRService.readCommentReportDetail(id));
    }
    @PassToken
    @RequestMapping(value = "/comment/report", method = RequestMethod.PUT)
    public Integer processCommentReport(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        String decision = param.get("decision").toString();
        return CRService.processCommentReport(id, decision);
    }
}
