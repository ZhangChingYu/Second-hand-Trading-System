package dev.silvia.wechattrade.handlers.fileHandler;

public interface FileDirector {// 整理所有文件路徑相關的參數
    // 商品圖片路徑
    String PRODUCT_URL ="C:/Users/Sunny/Desktop/Products/";
    // 用戶反饋路徑
    String FEEDBACK_URL="C:/Users/Sunny/Desktop/Feedback/";
    // 幫助文檔路徑(注意!!!後面少一個斜槓喔!!!)
    String HELP_URL = "C:/Users/Sunny/Desktop/Help";
    // 头像和认证文件存储路径
    String AUTH_URL = "C:/Users/Sunny/Desktop/User/";
    //默认头像路径
    String  AVATAR_URL= "C:/Users/Sunny/Desktop/User/Default/default/default.jpg";
    // 證件照站存處(注意!!!後面少一個斜槓喔!!!)
    String AUTHENTICATION_TEMP_URL = "C:/Users/Sunny/Desktop/User/AuthTemp";
    // 用戶協議存放路徑
    String USER_PRINCIPLE_URL = "C:/Users/Sunny/Desktop/User/Principle";
}
