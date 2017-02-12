package org.easyarch.slardar.entity;

/**
 * Description :
 * Created by xingtianyu on 17-2-12
 * 下午8:47
 * description:
 * 用户使用DefaultDBSession 时用来传递参数到session中
 */

public class Parameter {

    private String name;
    private Object value;

    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
