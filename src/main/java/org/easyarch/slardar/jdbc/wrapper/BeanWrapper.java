package org.easyarch.slardar.jdbc.wrapper;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  上午12:19
 */

import org.easyarch.slardar.binding.FieldBinder;
import org.easyarch.slardar.utils.ReflectUtils;
import org.easyarch.slardar.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 上午12:19
 */

public class BeanWrapper<T> extends WrapperAdapter<T> implements Wrapper<T> {


    public BeanWrapper(FieldBinder fieldBinder) {
        super(fieldBinder);
    }

    @Override
    public List<T> list(ResultSet rs, Class<T> type) {
        List<T> list = new CopyOnWriteArrayList<T>();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                list.add(createBean(rs,meta,type));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T bean(ResultSet rs, Class<T> type) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            if (rs.next()) {
                return createBean(rs,meta,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * bean生成模块抽取
     * @param rs
     * @param meta
     * @param type
     * @return
     */
    private T createBean(ResultSet rs, ResultSetMetaData meta,Class<T> type) {
        Object object = ReflectUtils.newInstance(type);
        try {
            int count = meta.getColumnCount();

            for (int i = 0; i < count; i++) {
                Object value = rs.getObject(i + 1);
                String propertyName = fieldBinder.getProperty(type, meta.getColumnName(i + 1));
                if (StringUtils.isEmpty(propertyName)){
                    ReflectUtils.setFieldValue(object,meta.getColumnName(i + 1), value);
                }else{
                    ReflectUtils.setFieldValue(object,propertyName, value);
                }
            }
            return (T) object;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
