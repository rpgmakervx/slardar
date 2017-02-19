package org.easyarch.slardar.binding;

import org.easyarch.slardar.annotation.entity.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-6
 * 下午2:38
 * description:数据库column和属性字段的映射
 */

public class FieldBinder<T> {

    protected static Map<Class<?>,Map<String,String>> fieldMapper = new HashMap<>();

    private Class<T> cls;

    public FieldBinder(Class<T> cls){
        this.cls = cls;
        init();
    }

    /**
     * 初始化的时候只根据是不是标准实体来初始化（即有没有注解）
     */
    private void init(){
        if (fieldMapper.containsKey(cls)){
            return;
        }
        Field[] fields = cls.getDeclaredFields();
        Map<String,String> mapper = new HashMap<>();
        for (Field field : fields){
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column == null){
                mapper.put(field.getName(),field.getName());
            }else{
                mapper.put(column.name(),field.getName());
            }
        }
        fieldMapper.put(cls,mapper);
    }

    public void bind(String column,String property){
        fieldMapper.get(cls).put(column,property);
    }

    public void bind(Map<String,String> mapper){
        fieldMapper.get(cls).putAll(mapper);
    }

    public String getProperty(String column){
        return getProperty(cls,column);
    }

    public String getProperty(Class<?> cls,String column){
        if (fieldMapper.containsKey(cls)){
            return fieldMapper.get(cls).get(column);
        }
        return "";
    }
}
