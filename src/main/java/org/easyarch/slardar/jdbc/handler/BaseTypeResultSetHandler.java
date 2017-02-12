package org.easyarch.slardar.jdbc.handler;

import org.easyarch.slardar.jdbc.wrapper.BaseTypeWrapper;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午8:09
 * description:
 */

public class BaseTypeResultSetHandler<T> extends BeanResultSetHadler<T>  {

    public BaseTypeResultSetHandler(Class<T> type) {
        super(new BaseTypeWrapper(),type);
    }
}
