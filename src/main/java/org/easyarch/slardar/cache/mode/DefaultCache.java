package org.easyarch.slardar.cache.mode;

import org.easyarch.slardar.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-15
 * 上午12:34
 * description:
 */

public class DefaultCache<K,V> implements Cache<K,V> {

    private Map<K,V> cache;

    public DefaultCache(){
        this(new ConcurrentHashMap<K, V>());
    }

    public DefaultCache(Map<K,V> map){
        cache = map;
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void set(K key, V value) {
        cache.put(key,value);
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public boolean isHit(K key) {
        return cache.containsKey(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
