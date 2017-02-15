package org.easyarch.slardar.cache;

import org.easyarch.slardar.mapping.ClassItem;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 下午3:03
 * description:缓存接口信息，启动slardar扫描接口后缓存用
 */

public class InterfaceCache implements Cache<Class,ClassItem>{

    private volatile Cache<Class,ClassItem> cache;

    public InterfaceCache(Cache<Class,ClassItem> cache){
        this.cache = cache;
    }

    @Override
    public ClassItem get(Class key) {
        return cache.get(key);
    }

    @Override
    public void set(Class key, ClassItem value) {
        if (value == null){
            return;
        }
        cache.set(key,value);
    }

    @Override
    public ClassItem remove(Class key) {
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
