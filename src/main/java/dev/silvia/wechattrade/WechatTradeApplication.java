package dev.silvia.wechattrade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("dev/silvia/wechattrade/dao")
public class WechatTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatTradeApplication.class, args);
    }

}
