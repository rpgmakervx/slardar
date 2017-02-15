package org.easyarch.slardar.cache;

/**
 * Description :
 * Created by xingtianyu on 17-1-26
 * 下午10:20
 * description:
 */

public class ProxyCache implements Cache<Class,Object> {

    private volatile Cache<Class,Object> cache;

    public ProxyCache(Cache<Class, Object> cache) {
        this.cache = cache;
    }

    @Override
    public Object get(Class key) {
        return cache.get(key);
    }

    @Override
    public void set(Class key, Object value) {
        cache.set(key,value);
    }

    @Override
    public Object remove(Class key) {
        return cache.remove(key);
    }

    @Override
    public boolean isHit(Class key) {
        return cache.isHit(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
