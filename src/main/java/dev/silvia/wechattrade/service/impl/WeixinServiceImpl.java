package dev.silvia.wechattrade.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.logindto.LoginResponseDto;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.entity.WXAuth;
import dev.silvia.wechattrade.entity.WxUserInfo;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.cryto.Sign;
import dev.silvia.wechattrade.handlers.fileHandlers.ReadFile;
import dev.silvia.wechattrade.service.IWeixinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class WeixinServiceImpl extends ServiceImpl implements IWeixinService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    WxService wxService;
    @Autowired
    TransferUTF8 trans;
    @Autowired
    private ReadFile readFile = new ReadFile();
    @Override
    public String getSessionId(String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid" +
                "={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);

        String res = HttpUtil.get(replaceUrl);
        // System.out.println(res);
        // log.info("发送链接后获得的数据{}",res);
//        String s = UUID.randomUUID().toString();
//        redisTemplate.opsForValue().set(RedisKey.WX_SESSION_ID + s, res);
        String s=getToken(res);
        return s;
    }

    @Override
    public LoginResponseDto authLogin(WXAuth wxAuth) {
        LoginResponseDto redto = new LoginResponseDto();
        try {
            String wxRes = wxService.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            WxUserInfo wxUserInfo = JSON.parseObject(wxRes,WxUserInfo.class);

            User user = new User();
            user.setPhone(wxUserInfo.getOpenId());
            user.setPassword("123456");
            user.setAuthority(1);

            //图片路径
            List<String> picture1;
            List<String> picture2;
            if(user.getAvatar().isEmpty()){
                //默认图片
                picture1 = readFile.getpictureBase64("Avatar","default",1);
                user.setAvatar(picture1.get(0));
            }
            else{
                picture1 = readFile.getpictureBase64("Avatar",user.getPhone(),1);
                user.setAvatar(picture1.get(0));
            }

            user.setUserName(trans.CtoUTF8(wxUserInfo.getNickName()));
            redto.setToken(Sign.generateToken(
                    user.getId(),
                    transferUTF8.CtoUTF8(user.getUserName()),
                    user.getAuthority(),
                    1000 * 60 * 60
            ));

            //判断用户信息是否存在
            String check = "select count(*) from user_info where phone = '" + wxUserInfo.getOpenId()+ "'";
            int checked = jdbcTemplate.queryForObject(check, Integer.class);
            redto.setUser(user);
            if(checked == 1){
                redto.setCode("666");
                redto.setMsg("操作成功！");

            }
            else{
                redto.setCode("666");
                redto.setMsg("用户初次登录！");

                userDao.insert(user);
            }
            return redto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        redto.setCode("000");
        redto.setMsg("操作失败！");
        return redto;
    }

    @Override
    public String getToken(String session) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(session).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC512("123456"));
        return token;
    }
}
