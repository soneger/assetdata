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

    int count() default Integer.MAX_VALUE; // 次数

    long time() default 5000; // 时间/单位毫秒
}
