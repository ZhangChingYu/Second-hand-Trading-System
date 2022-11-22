package dev.silvia.wechattrade.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.handlers.common.redis.RedisKey;
import dev.silvia.wechattrade.entity.WXAuth;
import dev.silvia.wechattrade.entity.Weixin;
import dev.silvia.wechattrade.entity.WxUserInfo;
import dev.silvia.wechattrade.dao.WeixinMapper;
import dev.silvia.wechattrade.service.IWeixinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeixinServiceImpl extends ServiceImpl<WeixinMapper, Weixin> implements IWeixinService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    WxService wxService;

    @Autowired
    private WeixinMapper wxmapper;

    @Override
    public String getSessionId(String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid" +
                "={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);

        String res = HttpUtil.get(replaceUrl);
        System.out.println(res);
        log.info("发送链接后获得的数据{}",res);
        String s = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisKey.WX_SESSION_ID + s, res);
        return s;
    }

    @Override
    public Result authLogin(WXAuth wxAuth) {
        try {
            String wxRes = wxService.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            WxUserInfo wxUserInfo = JSON.parseObject(wxRes,WxUserInfo.class);
            // 这里是做业务操作的，理论上需要查询数据库，看这个用户信息是否存在，存在直接返回登录态，
            // 若不存在即添加进数据库，做持久化。（表建好了，相关依赖也添加了，发现这是demo.就... 你懂的哈）
            // 根据自己需求 更改
            return Result.SUCCESS(wxUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.FAIL();
    }
}
