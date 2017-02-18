package org.easyarch.slardar.annotation.entity;

import org.easyarch.slardar.jdbc.type.JDBCType;

import java.lang.annotation.*;

/**
 * Description :
 * Created by code4j on 16-12-25
 * 下午10:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface NotColumn {
    String name() default "";
    JDBCType type() default JDBCType.VARCHAR;
}
