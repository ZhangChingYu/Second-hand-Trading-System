package dev.silvia.wechattrade.handlers.common.auth;

//权限拒绝异常
public class PermissionDeniedException extends Exception {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
