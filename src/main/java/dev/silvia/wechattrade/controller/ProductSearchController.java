package dev.silvia.wechattrade.controller;


import com.google.gson.Gson;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductSearchController {
    @Autowired
    @Resource
    private IProductService service;

    Gson gson = new Gson();

    // 通過商品編號查詢商品詳細信息
    @UserLoginToken
    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    public String getProductDetail(HttpServletRequest request){
        String number = request.getParameter("number");
        return gson.toJson(service.getProductDetail(number));
    }

    // 分類查找
    @UserLoginToken
    @RequestMapping(value = "/catalog/products", method = RequestMethod.GET)
    public String getProductByCatalog(HttpServletRequest request){
        String catalog = request.getParameter("catalog");
        return gson.toJson(service.getProductByCatalog(catalog));
    }

    // 關鍵字查詢商品
    @UserLoginToken
    @RequestMapping(value = "/search/products", method = RequestMethod.GET)
    public String getProductByKeyword(HttpServletRequest request){
        String keyword = request.getParameter("keyword");
        return gson.toJson(service.searchProductByKey(keyword));
    }

    // 首頁商品推送(暫時)
    @UserLoginToken
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepageProduct(){
        return gson.toJson(service.homepageProducts());
    }

    // 最新商品(top 10)
    @PassToken
    @RequestMapping(value = "/homepage/new/products", method = RequestMethod.GET)
    public String homepageProductNew(){
        return gson.toJson(service.homepageProductNew());
    }

    // 最多商品收藏(top 10)
    @PassToken
    @RequestMapping(value = "/homepage/like/products", method = RequestMethod.GET)
    public String homepageProductsLike(){
        return gson.toJson(service.homepageProductLike());
    }

    // 根據用戶數據推薦的商品(top 10)
    @UserLoginToken
    @RequestMapping(value = "/homepage/promote/products", method = RequestMethod.GET)
    public String homepageProductsPromote(HttpServletRequest request){
        String number = request.getParameter("number");
        return gson.toJson(service.homepageProductPromote(number));
    }
    /**
     * 日誌:
     * 用戶日誌:
     * 1. 登入用戶(手機號)
     * 2. 登入時間(系統時間)
     * 3. 用戶操作(查看、搜索、收藏、下單)
     * 4. 登出時間
     * 5. 日誌命名: 手機號+日期(方便系統存儲、分析)
     * 系統日誌:
     * 1. spring boot 自帶
     *
     * 大小
     *
     * 要求每输出日志文件大小建议设置在200M-300M之间，并可以通过修复配置文件按需调整。最好是在运行时可动态调整，包括级别、路径等
     *
     * 日志级别
     *
     * 日志具有几个级别，分别是INFO、DEBUG、ERROR、WARN、FATAL。
     *
     * ERROR：系统可以继续运行，但最好要尽快修复的错误。这个级别输出较多，常常伴随Java异常，错误(Error)的环境不一定会造成系统的崩溃，系统可以继续服务接下来的请求。运行时错误信息，包括异常信息等。其中应当包含技术异常。技术异常包括外部接口失败、IO异常、数据库连接异常等；
     *
     * WARN：系统可以正常运行，但需要引起注意的警告信息。这个级别预示较小的问题，由系统外部的因素造成的，比如用户输入了不符合条件的参数。如程序调用了一个即将作废的接口，接口的不当使用，运行状态不是期望的但仍可继续处理等；其中应当包含业务异常。业务异常标识业务中出现的异常信息，比如，用户余额不足、密码输入错误等
     *
     * INFO：系统运行的主要关键时点的操作信息，一般用于记录业务日志。同时也应该有足够的信息以保证可以记录再现缺陷的路径。这个级别记录了系统日常运转中有意义的事件。有意义的事件信息，如程序启动，关闭事件，收到请求事件、统计信息、系统性能信息、接口报文输出等。
     *
     * DEBUG：系统运行中的调试信息，便于开发人员进行错误分析和修正，一般用于程序日志，关心程序操作(细粒度)，不太关心业务操作(粗粒度)。系统出现问题时，必须抛出异常，在处理异常时记录日志，且日志级别必须是前三个级别Fatal、Error、Warn中的一种；*/
}
