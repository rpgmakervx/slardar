package org.easyarch.slardar.jdbc.wrapper;

import org.easyarch.slardar.jdbc.type.JAVAType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description :
 * Created by xingtianyu on 17-2-12
 * 下午10:18
 * description:
 */

public class BaseTypeWrapper<T> extends BeanWrapper<T> {

    public BaseTypeWrapper() {
        super(null);
    }

    @Override
    public T bean(ResultSet rs, Class<T> type) {
        try {
            if (rs.next()) {
                switch (JAVAType.getType(type)){
                    case INT:return (T) new Integer(rs.getInt(1));
                    case FLOAT:return (T) new Float(rs.getFloat(1));
                    case LONG:return (T) new Long(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
