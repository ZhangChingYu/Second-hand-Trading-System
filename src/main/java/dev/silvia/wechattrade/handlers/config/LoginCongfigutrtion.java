package dev.silvia.wechattrade.handlers.config;
import dev.silvia.wechattrade.handlers.common.congif.WechatTradeConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WechatTradeConfig.class)
public class LoginCongfigutrtion {

}
