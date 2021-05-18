package com.cn.momo.annotation;

import java.lang.annotation.*;

/**
 * @author dongwenmo
 * @create 2020-07-22 13:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Rbac {
}
