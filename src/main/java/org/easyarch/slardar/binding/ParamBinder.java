package org.easyarch.slardar.binding;

import org.easyarch.slardar.annotation.entity.Column;
import org.easyarch.slardar.annotation.entity.Table;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-1-19
 * 下午7:42
 * description:
 */

public class ParamBinder {

    private Map<String,Object> reflectMap;

    public ParamBinder(){
        reflectMap = new LinkedHashMap<>();
    }

    public Map<String,Object> reflect(List obj, List<String> name){
        for (int index=0;index<obj.size();index++){
            reflectMap.put(name.get(index),obj.get(index));
        }
        return reflectMap;
    }

    public <T> Map<String,Object> reflect(Object obj,Class<T> clazz){
        try {
            FieldBinder<T> binder = new FieldBinder(clazz);
            Field[] fields = clazz.getDeclaredFields();
            Table table = clazz.getAnnotation(Table.class);
            if (table == null){
                for (Field field:fields){
                    field.setAccessible(true);
                    reflectMap.put(field.getName(),field.get(obj));
                    binder.bind(field.getName(),field.getName());
                }
            }else{
                for (Field field:fields){
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    reflectMap.put(field.getName(),field.get(obj));
                    binder.bind(column.name(),field.getName());
                }
            }
            return reflectMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,Object> reflect(Object val,String name){
        reflectMap.put(name,val);
        return reflectMap;
    }

    public Map<String,Object> reflect(Map<String,?> params){
        reflectMap.putAll(params);
        return reflectMap;
    }

    public Map<String,Object> getMapper(){
        return reflectMap;
    }

}
