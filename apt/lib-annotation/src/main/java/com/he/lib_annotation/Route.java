package com.he.lib_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Target  用于描述注解的使用范围
 * Retention  用于描述注解的保留时间
 * @AutoService(Processor.class)  AutoService使用kotiln注解有一些问题 ，会出现无法生成代码的情况,//https://cloud.tencent.com/developer/article/1587253
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {

    String path() default "";
}
