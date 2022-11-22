package dev.silvia.wechattrade.dto.response;

/**
 * 通用响应状态
 */
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS("666", "操作成功！"),

    /* 错误状态码 */
    FAIL("000", "操作失败！"),

    /* 参数错误：10001-19999 */

    /* 参数错误：10001-19999 */
    PARAM_TYPE_BIND_ERROR("333", "手机号或邮箱格式错误"),

    /* 用户错误：20001-29999*/
    USER_LOGIN_ERROR("444", "密码错误"),
    USER_ACCOUNT_FORBIDDEN("222", "账号已被禁用"),
    USER_NOT_EXIST("555", "用户不存在"),
    USER_PASSWORD_EXIST("555", "用户密码为旧密码"),

    /* 数据错误：50001-599999 */
    AUTH_CODE_ERROR("111", "验证码错误"),


    /* 权限错误：70001-79999 */
    PERMISSION_UNAUTHENTICATED("70001", "此操作需要登陆系统！"),
    PERMISSION_UNAUTHORISE("70002", "权限不足，无权操作！"),
    PERMISSION_EXPIRE("70003", "登录状态过期！"),
    PERMISSION_TOKEN_EXPIRED("70004", "token已过期"),
    PERMISSION_LIMIT("70005", "访问次数受限制"),
    PERMISSION_TOKEN_INVALID("70006", "无效token"),
    PERMISSION_SIGNATURE_ERROR("70007", "签名失败");
    //操作代码
    String code;
    //提示信息
    String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
