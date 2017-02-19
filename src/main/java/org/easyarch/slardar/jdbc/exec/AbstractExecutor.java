package org.easyarch.slardar.jdbc.exec;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  下午2:30
 */

import org.easyarch.slardar.jdbc.handler.ResultSetHandler;
import org.easyarch.slardar.utils.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.sql.*;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 下午2:30
 */

public abstract class AbstractExecutor {

    abstract public <T> T query(String sql, ResultSetHandler<T> rshandler, Object[] params);

    abstract public int alter(String sql,Object[] params);

    abstract public void alterBatch(String sql,Object[][]params);
    abstract public void rollback();

    abstract public void close();

    protected PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
    protected PreparedStatement batchPrepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    }

    protected void fillStatement(PreparedStatement ps, Object[] params) {
        try {
            int paramLength = params == null ? 0 : params.length;
            ParameterMetaData meta = ps.getParameterMetaData();
            int count = meta.getParameterCount();
            if (params == null||meta == null||count == 0)
                return;
            if (paramLength != count) {
                throw new IllegalArgumentException("param length is "+paramLength+",but sql string has "+count);
            }
            for (int index = 0; index < paramLength; index++) {
                if (params[index] == null) {
                    ps.setNull(index + 1, Types.VARCHAR);
                    continue;
                }
                ps.setObject(index + 1, params[index]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void fillStatementWithBean(PreparedStatement ps, Object bean) {
        if (bean == null)
            return;
        PropertyDescriptor[] descriptors = ReflectUtils.propertyDescriptors(bean.getClass());
        Object[] params = new Object[descriptors.length];
        for (int index = 0; index < params.length; index++) {
            Object value = ReflectUtils.getter(bean, descriptors[index].getName());
            params[index] = value;
        }
        fillStatement(ps, params);
    }

    protected void fillStatement(PreparedStatement ps, Object[][] params){
        try {
            int paramLength = params == null ? 0 : params.length;
            ParameterMetaData meta = ps.getParameterMetaData();
            int count = meta.getParameterCount();
            if (params == null||meta == null||count == 0)
                return;
            if (paramLength != count) {
                throw new IllegalArgumentException("your param not match query string's param");
            }
            for (Object[] objs:params){
                for (int index = 0; index < paramLength; index++) {
                    if (objs[index] == null) {
                        ps.setNull(index, Types.VARCHAR);
                        continue;
                    }
                    ps.setObject(index + 1, objs[index]);
                }
                ps.addBatch();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
