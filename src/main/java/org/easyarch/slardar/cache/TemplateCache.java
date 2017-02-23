package org.easyarch.slardar.cache;

/**
 * Description :
 * Created by xingtianyu on 17-2-19
 * 下午2:28
 * description:
 */

public class TemplateCache implements Cache<String,String>{

    private Cache<String,String> cache;

    public TemplateCache(Cache<String, String> cache) {
        this.cache = cache;
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public void set(String key, String value) {
        cache.set(key,value);
    }

    @Override
    public String remove(String key) {
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
