package org.easyarch.slardar.mapping;

import org.easyarch.slardar.utils.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description :
 * Created by xingtianyu on 17-1-21
 * 下午11:31
 * description:
 */

public class ClassItem {

    private String itemName;

    private Class clazz;

    private List<Method> methods;

    public ClassItem(String itemName, Class clazz, Method[] methods) {
        this.itemName = itemName;
        this.clazz = clazz;
        this.methods = CollectionUtils.newArrayList(methods);
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

//    @Override
//    public String toString() {
//        return "ClassItem{" +
//                "itemName='" + itemName + '\'' +
//                ", clazz=" + clazz +
//                ", methods=" + methods +
//                '}';
//    }
}
