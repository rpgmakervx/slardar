package org.easyarch.slardar.cache;

/**
 * Description :
 * Created by xingtianyu on 17-1-19
 * 下午7:17
 * description:
 */

public interface Cache<K,V> {

    V get(K key);

    void set(K key, V value);

    V remove(K key);

    boolean isHit(K key);
    void clear();
}
