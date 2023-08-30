package org.carl.common.model.annotation;

import java.lang.annotation.*;

/**
 * Written by Mr. Carl
 * @description: TODO 字段含义注解
 * @version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Meaning {

    /**
     * 字段含义
     *
     * @return 字段含义
     */
    String value();

}
