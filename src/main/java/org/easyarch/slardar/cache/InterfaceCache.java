package org.easyarch.slardar.cache;

import org.easyarch.slardar.mapping.ClassItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 下午3:03
 * description:
 */

public class InterfaceCache implements Cache<Class,ClassItem> {

    private volatile Map<Class,ClassItem> interfaceMap = new ConcurrentHashMap<>();

    @Override
    public ClassItem get(Class key) {
        return interfaceMap.get(key);
    }

    @Override
    public void set(Class key, ClassItem value) {
        if (value == null){
            return;
        }
        interfaceMap.put(key,value);
    }

    @Override
    public ClassItem remove(Class key) {
        return interfaceMap.remove(key);
    }

    @Override
    public boolean isHit(Class key) {
        return interfaceMap.containsKey(key);
    }

    @Override
    public void clear() {
        interfaceMap.clear();
    }
}
