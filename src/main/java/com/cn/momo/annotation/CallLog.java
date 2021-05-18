package com.cn.momo.annotation;

import java.lang.annotation.*;

/**
 * @author dongwenmo
 * @create 2020-08-20 17:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CallLog {
	String name() default "";
	String desc() default "";
}
