package dev.silvia.wechattrade.handlers.fileHandler;

public interface FileDirector {// 整理所有文件路徑相關的參數
    // 商品圖片路徑
    String PRODUCT_URL ="D:/大四上學期/畢業實習/WechatTrade/src/main/resources/Products/";
    // 用戶反饋路徑
    String FEEDBACK_URL="D:/大四上學期/畢業實習/WechatTrade/src/main/resources/Feedback/";
    // 聊天图片存储路径
    String CHAT_URL = "C:/Users/Sunny/Desktop/Chat/";
    // 幫助文檔路徑(注意!!!後面少一個斜槓喔!!!)
    String HELP_URL = "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/Help";
    // 头像和认证文件存储路径
    String AUTH_URL = "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/User/";    //默认头像路径
    String  DEFAULT_AVATAR_URL= "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/User/Default/default";
    //默认头像路径
    String  AVATAR_URL= "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/User/Default/default/default.jpg";
    // 證件照站存處(注意!!!後面少一個斜槓喔!!!)
    String AUTHENTICATION_TEMP_URL = "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/User/AuthTemp";
    // 用戶協議存放路徑
    String USER_PRINCIPLE_URL = "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/User/Principle";
    // 首頁輪播圖路徑(放在項目路徑的static下)
    String ADS_URL = "D:/大四上學期/畢業實習/WechatTrade/src/main/resources/static";
}
