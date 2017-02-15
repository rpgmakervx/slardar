package org.easyarch.slardar.cache.factory;

import org.easyarch.slardar.cache.AbstractCacheFactory;
import org.easyarch.slardar.cache.QueryCache;
import org.easyarch.slardar.cache.mode.DefaultCache;
import org.easyarch.slardar.cache.mode.FIFOCache;
import org.easyarch.slardar.cache.mode.LRUCache;
import org.easyarch.slardar.cache.mode.TimeOutCache;
import org.easyarch.slardar.entity.CacheEntity;

/**
 * Description :
 * Created by xingtianyu on 17-2-16
 * 上午1:35
 * description:
 */

public class QueryCacheFactory extends AbstractCacheFactory<QueryCache> {

    private volatile QueryCache queryCache;

    private static QueryCacheFactory queryCacheFactory;

    private QueryCacheFactory(){

    }

    public static QueryCacheFactory getInstance(){
        synchronized (QueryCacheFactory.class){
            if (queryCacheFactory == null){
                synchronized (QueryCacheFactory.class){
                    queryCacheFactory = new QueryCacheFactory();
                }
            }
        }
        return queryCacheFactory;
    }

    @Override
    public QueryCache createCache(CacheEntity entity) {
        if (queryCache == null){
            if (entity != null &&entity.isEnable()){
                switch (entity.getMode()){
                    case FIFO:
                        queryCache = new QueryCache(new FIFOCache<>(entity.getSize()));
                        return queryCache;
                    case LRU:
                        queryCache = new QueryCache(new LRUCache<>(entity.getSize()));
                        return queryCache;
                    case TIMEOUT:
                        queryCache = new QueryCache(new TimeOutCache<>(entity.getSize()));
                        return queryCache;
                    default:
                        queryCache = new QueryCache(new DefaultCache<>());
                        return queryCache;
                }
            }
            queryCache = new QueryCache(new DefaultCache<>());
        }
        return queryCache;
    }
}
