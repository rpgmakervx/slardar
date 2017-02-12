package org.easyarch.slardar.jdbc.type;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Description :
 * Created by xingtianyu on 16-12-27
 * 上午12:05
 * description:
 */

public enum JAVAType {
    INT(int.class,Integer.class),
    CHAR(char.class,Character.class),
    FLOAT(float.class,Float.class),
    DOUBLE(double.class,Double.class),
    LONG(long.class,Long.class),
    SHORT(short.class,Short.class),
    BOOL(boolean.class,Boolean.class),
    BYTE(byte.class,Byte.class),
    BYTEARRAY(byte[].class,Byte[].class),
    STRING(String.class,String.class),
    DATE(Date.class, java.sql.Date.class),
    TIMESTAMP(Timestamp.class, Timestamp.class);

    public Class obj;
    public Class base;

    JAVAType(Class base,Class obj) {
        this.obj = obj;
        this.base = base;
    }

    public static JAVAType getType(Class cls){
        for (JAVAType type:values()){
            if (type.base.equals(cls)||type.obj.equals(cls)){
                return type;
            }
        }
        throw new IllegalArgumentException("there is no type named "+cls.getTypeName()+" here");
    }
}
