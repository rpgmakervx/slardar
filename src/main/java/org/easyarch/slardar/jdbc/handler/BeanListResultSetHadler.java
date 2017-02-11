package org.easyarch.slardar.jdbc.handler;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  上午12:00
 */

import org.easyarch.slardar.binding.FieldBinder;
import org.easyarch.slardar.jdbc.wrapper.BeanWrapper;
import org.easyarch.slardar.jdbc.wrapper.Wrapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 上午12:00
 */

public class BeanListResultSetHadler<T> implements ResultSetHandler<List<T>> {

    protected Wrapper<T> wrapper;

    protected Class<T> type;

    public BeanListResultSetHadler(Class<T> type){
        this(new BeanWrapper<T>(new FieldBinder(type)),type);
    }

    public BeanListResultSetHadler(Wrapper<T> wrapper , Class<T> type) {
        this.wrapper = wrapper;
        this.type = type;
    }


    @Override
    public List<T> handle(ResultSet rs) throws Exception {
        return wrapper.list(rs, type);
    }

}
