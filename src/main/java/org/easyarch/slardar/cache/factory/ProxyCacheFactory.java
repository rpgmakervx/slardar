package org.easyarch.slardar.cache.factory;

import org.easyarch.slardar.cache.AbstractCacheFactory;
import org.easyarch.slardar.cache.ProxyCache;
import org.easyarch.slardar.cache.mode.DefaultCache;
import org.easyarch.slardar.cache.mode.FIFOCache;
import org.easyarch.slardar.cache.mode.LRUCache;
import org.easyarch.slardar.cache.mode.TimeOutCache;
import org.easyarch.slardar.entity.CacheEntity;

/**
 * Description :
 * Created by xingtianyu on 17-2-16
 * 上午1:19
 * description:
 */

public class ProxyCacheFactory extends AbstractCacheFactory<ProxyCache> {

    private static volatile ProxyCacheFactory proxyCacheFactory;

    private volatile ProxyCache proxyCache;

    private ProxyCacheFactory() {
    }

    public static ProxyCacheFactory getInstance(){
        synchronized (ProxyCacheFactory.class){
            if (proxyCacheFactory == null){
                synchronized (ProxyCacheFactory.class){
                    proxyCacheFactory = new ProxyCacheFactory();
                }
            }
        }
        return proxyCacheFactory;
    }

    @Override
    public ProxyCache createCache(CacheEntity entity) {
        if (proxyCache == null){
            if (entity != null &&entity.isEnable()){
                switch (entity.getMode()){
                    case FIFO:
                        proxyCache = new ProxyCache(new FIFOCache<>(entity.getSize()));
                        return proxyCache;
                    case LRU:
                        proxyCache = new ProxyCache(new LRUCache<>(entity.getSize()));
                        return proxyCache;
                    case TIMEOUT:
                        proxyCache = new ProxyCache(new TimeOutCache<>(entity.getSize()));
                        return proxyCache;
                    default:
                        proxyCache = new ProxyCache(new DefaultCache<>());
                        return proxyCache;
                }
            }
            proxyCache = new ProxyCache(new DefaultCache<>());
        }
        return proxyCache;
    }
}
