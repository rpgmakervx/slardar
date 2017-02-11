package org.easyarch.slardar.mapping;

import org.easyarch.slardar.cache.CacheFactory;
import org.easyarch.slardar.cache.ProxyCache;
import org.easyarch.slardar.session.impl.MapperDBSession;

import java.lang.reflect.Proxy;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 下午3:02
 * description:
 */

public class MapperProxyFactory<T> {

    private Class<T> interfaceClass;

    /**
     * 缓存代理实例，减轻动态代理带来的性能损耗
     */
    private static ProxyCache proxyCache = CacheFactory.getInstance().getProxyCache();

    public MapperProxyFactory(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T newInstance(MapperDBSession session) {
        if (proxyCache.isHit(interfaceClass)){
            return (T) proxyCache.get(interfaceClass);
        }
        MapperProxy mapperProxy = new MapperProxy(session, interfaceClass);
        T interFace = (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(), new Class[]{interfaceClass}, mapperProxy);
        proxyCache.set(interfaceClass,interFace);
        return interFace;
    }

}
