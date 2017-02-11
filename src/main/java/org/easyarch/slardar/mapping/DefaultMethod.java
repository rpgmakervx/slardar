package org.easyarch.slardar.mapping;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 下午5:21
 * description:
 */

public enum  DefaultMethod {
    EQUALS("equals"),
    HASHCODE("hashCode"),
    TOSTRING("toString"),
    GETCLASS("getClass"),
    CLONE("clone"),
    WAIT("wait"),
    NOTIFY("notify"),
    NOTIFYALL("notifyAll");

    public String methodName;

    DefaultMethod(String methodName) {
        this.methodName = methodName;
    }

    public static boolean contains(String name){
        for (DefaultMethod method:values()){
            if(method.equals(method.methodName)){
                return true;
            }
        }
        return false;
    }
}
