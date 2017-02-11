package org.easyarch.slardar.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-1-26
 * 下午10:20
 * description:
 */

public class ProxyCache implements Cache<Class,Object> {

    private volatile Map<Class,Object> proxyMap = new ConcurrentHashMap<>();

    @Override
    public Object get(Class key) {
        return proxyMap.get(key);
    }

    @Override
    public void set(Class key, Object value) {
        proxyMap.put(key,value);
    }

    @Override
    public Object remove(Class key) {
        return proxyMap.remove(key);
    }

    @Override
    public boolean isHit(Class key) {
        return proxyMap.containsKey(key);
    }

    @Override
    public void clear() {
        proxyMap.clear();
    }
}
