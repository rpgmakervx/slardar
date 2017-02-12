package org.easyarch.slardar.jdbc.handler;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  上午12:00
 */

import org.easyarch.slardar.binding.FieldBinder;
import org.easyarch.slardar.jdbc.wrapper.BeanWrapper;
import org.easyarch.slardar.jdbc.wrapper.Wrapper;

import java.sql.ResultSet;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 上午12:00
 */

public class BeanResultSetHadler<T> implements ResultSetHandler<T> {

    protected Wrapper<T> wrapper;

    protected Class<T> type;

    public BeanResultSetHadler(Class<T> type) {
        this(new BeanWrapper<T>(new FieldBinder(type)),type);
    }

    public BeanResultSetHadler(Wrapper<T> wrapper, Class<T> type) {
        this.wrapper = wrapper;
        this.type = type;
    }

    @Override
    public T handle(ResultSet rs) throws Exception {
        return wrapper.bean(rs, type);
    }

}
