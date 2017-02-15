package org.easyarch.slardar.cache;

import org.easyarch.slardar.entity.CacheEntity;

/**
 * Description :
 * Created by xingtianyu on 17-2-16
 * 上午1:03
 * description:
 */

abstract public class AbstractCacheFactory<T extends Cache> {

    abstract public T createCache(CacheEntity entity);


}
