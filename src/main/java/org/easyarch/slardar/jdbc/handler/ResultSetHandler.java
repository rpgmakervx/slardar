package org.easyarch.slardar.jdbc.handler;

import java.sql.ResultSet;

/**
 * Description :
 * Created by code4j on 16-11-2
 * 下午3:02
 */

public interface ResultSetHandler<T> {
    public T handle(ResultSet rs) throws Exception;
}
