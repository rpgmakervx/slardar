package org.easyarch.slardar.cache.factory;

import org.easyarch.slardar.cache.AbstractCacheFactory;
import org.easyarch.slardar.cache.SqlMapCache;
import org.easyarch.slardar.cache.mode.DefaultCache;
import org.easyarch.slardar.cache.mode.FIFOCache;
import org.easyarch.slardar.cache.mode.LRUCache;
import org.easyarch.slardar.cache.mode.TimeOutCache;
import org.easyarch.slardar.entity.CacheEntity;

public class SqlMapCacheFactory extends AbstractCacheFactory<SqlMapCache>{
    private static SqlMapCacheFactory sqlMapCacheFactory = new SqlMapCacheFactory();

    private volatile SqlMapCache sqlMapCache;

    public static SqlMapCacheFactory getInstance() {
        synchronized (SqlMapCacheFactory.class){
            if (sqlMapCacheFactory == null){
                synchronized (SqlMapCacheFactory.class){
                    sqlMapCacheFactory = new SqlMapCacheFactory();
                }
            }
        }
        return sqlMapCacheFactory;
    }

    private SqlMapCacheFactory() {
    }

    @Override
    public SqlMapCache createCache(CacheEntity entity) {
        if (sqlMapCache == null){
            if (entity != null &&entity.isEnable()){
                switch (entity.getMode()){
                    case FIFO:
                        sqlMapCache = new SqlMapCache(new FIFOCache<>(entity.getSize()));
                        return sqlMapCache;
                    case LRU:
                        sqlMapCache = new SqlMapCache(new LRUCache<>(entity.getSize()));
                        return sqlMapCache;
                    case TIMEOUT:
                        sqlMapCache = new SqlMapCache(new TimeOutCache<>(entity.getSize()));
                        return sqlMapCache;
                    default:
                        sqlMapCache = new SqlMapCache(new DefaultCache<>());
                        return sqlMapCache;
                }
            }
            sqlMapCache = new SqlMapCache(new DefaultCache<>());
        }
        return sqlMapCache;
    }
}
