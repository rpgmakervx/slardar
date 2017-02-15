package org.easyarch.slardar.cache.mode;

import org.easyarch.slardar.cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-16
 * 上午12:41
 * description:
 */

public class FIFOCache<K,V> implements Cache<K,V> {

    private final int MAX_CACHE_SIZE;

    private final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedHashMap<K, V> cache;

    public FIFOCache(int size) {
        MAX_CACHE_SIZE = size;
        //根据cacheSize和加载因子计算hashmap的capactiy，+1确保当达到cacheSize上限时不会触发hashmap的扩容，
        int capacity = (int) Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1;
        cache = new LinkedHashMap(capacity, DEFAULT_LOAD_FACTOR, false) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
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

    @Override
    public String toString() {
        return "cache size:"+MAX_CACHE_SIZE+",content:"+cache;
    }

    public static void main(String[] args) {
        Cache<String,String> cache = new LRUCache<>(5);
        cache.set("xingtianyu","123456");
        cache.set("code4j","aaaaaa");
        cache.set("rpgmakervx","bbbbbb");
        cache.set("ininininin","sdsdsdsd");
        cache.set("00000000","s");
        cache.get("xingtianyu");
        cache.set("啦啦啦啦啦","噜噜噜噜噜噜");
        System.out.println(cache);
    }
}
