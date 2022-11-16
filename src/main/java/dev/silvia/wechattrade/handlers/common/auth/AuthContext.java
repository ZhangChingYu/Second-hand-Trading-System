package dev.silvia.wechattrade.handlers.common.auth;

import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class AuthContext {
    //身份验证内容
    //获取请求标头
    private static String getRequestHeader(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request.getHeader(headerName);
        }
        return null;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    public static String getTokenUserId() {
        String token = getRequest().getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    public static String getUserId() {
        return getRequestHeader(String.valueOf(AuthConstant.X_JWT_ID_HEADER));
    }
    public static String getUsername() {
        return getRequestHeader(AuthConstant.X_JWT_NAME_HEADER);
    }

    public static String getUserRole() {
        return getRequestHeader(String.valueOf(AuthConstant.X_JWT_ROLE_HEADER));
    }
}

