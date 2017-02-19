package org.easyarch.slardar.utils;

import org.easyarch.slardar.annotation.entity.Column;
import org.easyarch.slardar.binding.FieldBinder;
import org.easyarch.slardar.entity.Parameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-19
 * 下午12:56
 * description:
 * 提供用户使用，在使用DefaultDBSession时将基本类型或对象 以及 定义对象转换成Parameter作为参数使用
 */

public class ParamUtil {

    public static Parameter create(String name,Object value){
        if (StringUtils.isEmpty(name)||value == null){
            return null;
        }
        return new Parameter(name,value);
    }

    public static Parameter[] create(Map<String,Object> params){
        Parameter[] parameters = new Parameter[params.size()];
        int index = 0;
        for (Map.Entry<String,Object> entry : params.entrySet()){
            parameters[index] = new Parameter(entry.getKey(),entry.getValue());
        }
        return parameters;
    }

    public static Parameter[] create(Object bean){
        FieldBinder binder = new FieldBinder(bean.getClass());
        List<Parameter> parameterList = new ArrayList<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields){
            Object value = ReflectUtils.getFieldValue(bean,field.getName());
            Column column = field.getAnnotation(Column.class);
            Parameter param = null;
            if (column == null){
                param = new Parameter(field.getName(),value);
                binder.bind(field.getName(),field.getName());
            }else{
                param = new Parameter(column.name(),value);
                binder.bind(column.name(),field.getName());
            }
            parameterList.add(param);
        }
        return parameterList.toArray(new Parameter[parameterList.size()]);
    }

}
