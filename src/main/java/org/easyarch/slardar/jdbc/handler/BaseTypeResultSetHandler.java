package org.easyarch.slardar.jdbc.handler;

import java.sql.ResultSet;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午8:09
 * description:
 */

public class BaseTypeResultSetHandler<T extends Number> implements ResultSetHandler<T>  {
    @Override
    public T handle(ResultSet rs) throws Exception {
        return null;
    }
}
