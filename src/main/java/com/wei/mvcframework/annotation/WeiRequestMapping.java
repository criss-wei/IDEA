package com.wei.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author wei
 * @date 2019/7/3-22:31
 */
@Target({ElementType.TYPE,ElementType.METHOD})//作用于类上 和方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WeiRequestMapping {
    String  value() default "";
}
