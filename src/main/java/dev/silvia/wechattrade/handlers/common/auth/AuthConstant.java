package dev.silvia.wechattrade.handlers.common.auth;

public class AuthConstant {
    //身份验证常量
    //LoginResponse.Dto
    public static final String CLAIM_USER_ID = "id";
    public static final String CLAIM_USER_NAME = "userName";
    public static final String CLAIM_ROLE = "authority";

    public static final String X_JWT_ID_HEADER = "X-jwt-id";
    public static final String X_JWT_NAME_HEADER = "X-jwt-userName";
    public static final String X_JWT_ROLE_HEADER ="X-jwt-authority";
}