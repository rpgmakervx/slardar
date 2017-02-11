package org.easyarch.slardar.annotation.sql;

import java.lang.annotation.*;

/**
 * Description :
 * Created by xingtianyu on 17-1-26
 * 下午4:39
 * description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Mapper {
    String namespace() default "";

}
