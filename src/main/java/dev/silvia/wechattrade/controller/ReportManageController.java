package dev.silvia.wechattrade.controller;

import com.google.gson.Gson;
import dev.silvia.wechattrade.dto.report.ProductReportDto;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
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


    Gson gson = new Gson();

    @PassToken
    @RequestMapping(value = "/product/report", method = RequestMethod.POST)
    public Integer sendProductReport(@RequestBody ProductReportDto productReportDto){
        return PRService.userPostReport(productReportDto);
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
}
