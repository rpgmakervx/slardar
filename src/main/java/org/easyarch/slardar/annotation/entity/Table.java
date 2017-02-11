package org.easyarch.slardar.annotation.entity;

import java.lang.annotation.*;

/**
 * Description :
 * Created by code4j on 16-12-25
 * 下午9:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Table {

    String tableName() default "";
}
