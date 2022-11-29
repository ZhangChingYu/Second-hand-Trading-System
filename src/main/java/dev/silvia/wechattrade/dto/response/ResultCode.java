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
    USER_PASSWORD_EXIST("777", "用户密码为旧密码"),

    /* 数据错误：50001-599999 */
    AUTH_CODE_ERROR("111", "验证码错误");


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
