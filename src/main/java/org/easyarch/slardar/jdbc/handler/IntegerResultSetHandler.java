package org.easyarch.slardar.jdbc.handler;

import org.easyarch.slardar.jdbc.wrapper.IntegerWrapper;
import org.easyarch.slardar.jdbc.wrapper.Wrapper;

import java.sql.ResultSet;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午8:09
 * description:
 */

public class IntegerResultSetHandler implements ResultSetHandler<Integer>  {

    private Wrapper<Integer> wrapper;

    public IntegerResultSetHandler() {
        this.wrapper = new IntegerWrapper();
    }

    @Override
    public Integer handle(ResultSet rs) throws Exception {
        return wrapper.bean(rs,Integer.class);
    }
}
