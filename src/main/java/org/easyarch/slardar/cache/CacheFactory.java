package org.easyarch.slardar.cache;

import org.easyarch.slardar.cache.mode.LRUCache;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 上午12:30
 * description:
 */

public class CacheFactory {

    private static CacheFactory factory;

    private SqlMapCache sqlMapCache;
    private InterfaceCache interfaceCache;
    private ProxyCache proxyCache;

    private CacheFactory(){}

    public static CacheFactory getInstance(){
        synchronized (CacheFactory.class){
            if (factory == null){
                synchronized (CacheFactory.class){
                    factory = new CacheFactory();
                }
            }
        }
        return factory;
    }

    public SqlMapCache getSqlMapCache(){
        if (sqlMapCache == null){
            sqlMapCache = new SqlMapCache(new LRUCache<>(128));
        }
        return sqlMapCache;
    }

    public InterfaceCache getInterfaceCache(){
        if (interfaceCache == null){
            interfaceCache = new InterfaceCache(new LRUCache<>(128));
        }
        return interfaceCache;
    }

    public ProxyCache getProxyCache(){
        if (proxyCache == null){
            proxyCache = new ProxyCache(new LRUCache<>(128));
        }
        return proxyCache;
    }
}
