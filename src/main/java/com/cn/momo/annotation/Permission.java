package com.cn.momo.annotation;

import java.lang.annotation.*;

/**
 * @author dongwenmo
 * @create 2020-07-23 8:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
	String authorities() default "admin";
}
