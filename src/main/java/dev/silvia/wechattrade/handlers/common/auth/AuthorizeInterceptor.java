package dev.silvia.wechattrade.handlers.common.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.annotation.PassToken;
import dev.silvia.wechattrade.handlers.common.annotation.UserLoginToken;
import dev.silvia.wechattrade.handlers.common.repository.UserRepository;
import dev.silvia.wechattrade.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

//拦截器
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {
    private static final String TOKEN = "123456";
    private static final Algorithm algorithm = Algorithm.HMAC512(TOKEN);
    @Autowired
    IRegisterService userService;

    @Autowired
    private UserRepository accountRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                Integer id;
                try {
                    id= JWT.decode(token).getClaims().get("id").asInt();
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }

                Integer auth;
                try {
                    auth= JWT.decode(token).getClaims().get("authority").asInt();
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }

                User user = userService.getUserById(id);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }

                if (!Objects.equals(user.getId(), id)) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }

                if (!Objects.equals(user.getAuthority(), auth)) {
                    throw new RuntimeException("用户错误，请重新登录");
                }
                //验证token
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }

        Authorize authorize = handlerMethod.getMethod().getAnnotation(Authorize.class);
        if (authorize == null) {
            return true;
        }

        String auth1 = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(auth1)) {
            return true;
        }
        return true;
    }
}