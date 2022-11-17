package dev.silvia.wechattrade.handlers.common.annotation;

import java.lang.annotation.*;
/**
 * 用于登录后才能操作的token
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
}
