package org.easyarch.slardar.cache.factory;

import org.easyarch.slardar.cache.AbstractCacheFactory;
import org.easyarch.slardar.cache.InterfaceCache;
import org.easyarch.slardar.cache.mode.DefaultCache;
import org.easyarch.slardar.cache.mode.FIFOCache;
import org.easyarch.slardar.cache.mode.LRUCache;
import org.easyarch.slardar.cache.mode.TimeOutCache;
import org.easyarch.slardar.entity.CacheEntity;

/**
 * Description :
 * Created by xingtianyu on 17-2-16
 * 上午1:33
 * description:
 */

public class InterfaceCacheFactory extends AbstractCacheFactory<InterfaceCache> {

    private static InterfaceCacheFactory interfaceCacheFactory;

    private volatile InterfaceCache interfaceCache;

    public static InterfaceCacheFactory getInstance() {
        synchronized (InterfaceCacheFactory.class){
            if (interfaceCacheFactory == null){
                synchronized (InterfaceCacheFactory.class){
                    interfaceCacheFactory = new InterfaceCacheFactory();
                }
            }
        }
        return interfaceCacheFactory;
    }

    private InterfaceCacheFactory(){

    }
    @Override
    public InterfaceCache createCache(CacheEntity entity) {
        if (interfaceCache == null){
            if (entity != null &&entity.isEnable()){
                switch (entity.getMode()){
                    case FIFO:
                        interfaceCache = new InterfaceCache(new FIFOCache<>(entity.getSize()));
                        return interfaceCache;
                    case LRU:
                        interfaceCache = new InterfaceCache(new LRUCache<>(entity.getSize()));
                        return interfaceCache;
                    case TIMEOUT:
                        interfaceCache = new InterfaceCache(new TimeOutCache<>(entity.getSize()));
                        return interfaceCache;
                    default:
                        interfaceCache = new InterfaceCache(new DefaultCache<>());
                        return interfaceCache;
                }
            }
            interfaceCache = new InterfaceCache(new DefaultCache<>());
        }
        return interfaceCache;
    }
}
