package com.wei.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author wei
 * @date 2019/7/3-22:31
 */
@Target({ElementType.FIELD})//作用于字段上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WeiAutowired {
    String  value() default "";
}
