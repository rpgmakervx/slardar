package org.easyarch.slardar.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午9:02
 * description:
 */

public class BeanMapUtils {

    public static <T> T getBean(Map<String,Object> map, Class<T> cls){
        T bean = (T) ReflectUtils.newInstance(cls);
        for (Map.Entry<String,Object> entry:map.entrySet()){
            ReflectUtils.setter(bean,entry.getKey(),entry.getKey());
        }
        return bean;
    }

    /**
     * 如果有用户自定义类则递归获取属性
     * @param bean
     * @param <T>
     * @return
     */
    public static<T> Map<String,Object> getMap(T bean){
        Map<String,Object> map = new HashMap<>();

        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field f:fields){
                if (Modifier.isStatic(f.getModifiers())){
                    continue;
                }
                f.setAccessible(true);
                Object val = f.get(bean);
                String name = f.getName();
                System.out.println("name:"+name+",val:"+val+",bean:"+bean.getClass().getName());
                if (isBaseObject(val.getClass())||bean == null){
                    map.put(name,val);
                }else if (isCollection(val.getClass())) {
                    Iterable itra = null;
                    if (val.getClass().isArray()){
                        itra = Arrays.asList((Object[])val);
                    }else{
                        itra = (Iterable) val;
                    }
                    Iterator it = itra.iterator();
                    List list = new ArrayList();
                    Map<String,Object> m = new HashMap<>();
                    while(it.hasNext()){
                        Object value = it.next();
                        m.putAll(getMap(value));
                    }
                    list.add(m);
                    map.put(name,list);
                }else if (isKV(val.getClass())){
                    Map<String,Object> m = (Map) val;
                    for (Map.Entry<String,Object> entry:m.entrySet()){
                        map.putAll(getMap(entry.getValue()));
                    }
                }else {
                    map.putAll(getMap(val));
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }

    private static boolean isBaseObject(Class cls){
        if (ReflectUtils.isBaseType(cls)||cls.equals(String.class)||cls.equals(Date.class)){
            return true;
        }
        return false;
    }

    private static boolean isCollection(Class cls){
        if (Collection.class.isAssignableFrom(cls)
                ||cls.isArray()){
            return true;
        }
        return false;
    }

    public static boolean isKV(Class cls){
        return Map.class.isAssignableFrom(cls);
    }

}
