package dev.silvia.wechattrade.handlers.common.auth;

import java.lang.annotation.*;

/**
 * 用于登录后才能操作的token
 */

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    //权限
    String[] value();
}
/*RetentionPolicy.RUNTIME:这种类型的Annotations将被JVM保留,
所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用。*/