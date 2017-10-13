
package org.rapharino.entrance.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API 申明
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface API {

    // 名字
    String name() default "";

    // 分组
    String group() default "";

    // 开放 API
    Class<?> value();

    // 版本
    String version() default "v1";

}
