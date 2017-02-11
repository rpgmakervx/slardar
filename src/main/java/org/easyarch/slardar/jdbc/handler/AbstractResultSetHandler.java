package org.easyarch.slardar.jdbc.handler;/**
 * Description : 
 * Created by YangZH on 16-11-2
 *  下午7:08
 */

import org.easyarch.slardar.annotation.entity.Column;
import org.easyarch.slardar.annotation.entity.Table;
import org.easyarch.slardar.jdbc.wrapper.Wrapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by code4j on 16-11-2
 * 下午7:08
 */

abstract public class AbstractResultSetHandler<T> implements ResultSetHandler<T>{

    protected Wrapper<T> wrapper;
    protected Class<T> type;

    protected static Map<Class<?>,Map<String,String>> fieldMapper = new HashMap<>();
    public AbstractResultSetHandler(Wrapper wrapper, Class<T> type){
        this.wrapper = wrapper;
        this.type = type;
        mapFields();
    }

    private void mapFields(){
        if (this.type.getAnnotation(Table.class) == null){
            return;
        }
        Field [] fields = this.type.getDeclaredFields();
        Map<String,String> mapper = new HashMap<>();
        for (Field field : fields){
            Column column = field.getAnnotation(Column.class);
            mapper.put(field.getName(),column.name());
        }
        fieldMapper.put(type,mapper);
    }

}
