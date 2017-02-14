package org.easyarch.slardar.cache;

/**
 * Description :
 * Created by xingtianyu on 17-2-15
 * 上午2:18
 * description:
 */

public class QueryCache implements Cache<String,Object> {

    private Cache<String,Object> cache;

    public QueryCache(Cache<String, Object> cache) {
        this.cache = cache;
    }

    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    @Override
    public void set(String key, Object value) {
        cache.set(key,value);
    }

    @Override
    public Object remove(String key) {
        return cache.remove(key);
    }

    @Override
    public boolean isHit(String key) {
        return cache.isHit(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
