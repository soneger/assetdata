package cn.soneer.assetdata.annotation;

import java.lang.annotation.*;

/**
 * @Author Soneer
 * @Date 2021/2/5 17:18
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAstrict {
    String name() default "";
    int count() default 3; // 次数
    int seconds() default 10; // 时间/单位毫秒
}
