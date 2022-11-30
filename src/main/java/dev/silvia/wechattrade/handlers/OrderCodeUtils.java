package dev.silvia.wechattrade.handlers;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderCodeUtils {

    /**
     * 生成编号(指定前缀+时间戳+4位随机数组成)
     *
     */
    public static String createCode(String params) {

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        int i = (int)(Math.random()*9000 + 1000);
        return params +str + i;
    }


}
