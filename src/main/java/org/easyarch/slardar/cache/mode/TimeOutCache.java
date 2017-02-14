package org.easyarch.slardar.cache.mode;

import org.easyarch.slardar.cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-15
 * 上午2:25
 * description:
 */

public class TimeOutCache<K,V> implements Cache<K,V> {

    private final long TIMEOUT;

    private final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedHashMap<K, V> cache;

    private Map<K,Long> timeTable;

    public TimeOutCache(long timeout){
        TIMEOUT = timeout;
        timeTable = new ConcurrentHashMap<>();
        cache = new LinkedHashMap() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                long time = timeTable.get(eldest.getKey());
                long gap = System.currentTimeMillis() - time;
                return gap > TIMEOUT;
            }
        };
    }

    @Override
    public V get(K key) {
        timeTable.put(key,System.currentTimeMillis());
        return cache.get(key);
    }

    @Override
    public void set(K key, V value) {
        timeTable.put(key,System.currentTimeMillis());
        cache.put(key,value);
    }

    @Override
    public V remove(K key) {
        timeTable.remove(key);
        return cache.remove(key);
    }

    @Override
    public boolean isHit(K key) {
        return cache.containsKey(key);
    }

    @Override
    public void clear() {
        timeTable.clear();
        cache.clear();
    }

    @Override
    public String toString() {
        return "cache timeout:"+TIMEOUT+",content:"+cache;
    }

    public static void main(String[] args) throws InterruptedException {
        TimeOutCache<Integer,String> cache = new TimeOutCache(1000);
        cache.set(1,"1s");
        Thread.sleep(200);
        System.out.println(cache);
        cache.set(2,"2s");
        Thread.sleep(200);
        System.out.println(cache);
        cache.set(3,"3s");
        Thread.sleep(200);
        System.out.println(cache);
        cache.set(4,"4s");
        Thread.sleep(200);
        System.out.println(cache);
        cache.set(5,"5s");
        Thread.sleep(200);
        System.out.println(cache);
        cache.set(6,"6s");
        Thread.sleep(200);
        System.out.println(cache);
        cache.set(7,"7s");
        Thread.sleep(200);
        System.out.println(cache);
    }
}
