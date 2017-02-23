package org.easyarch.slardar.cache.factory;

import org.easyarch.slardar.cache.AbstractCacheFactory;
import org.easyarch.slardar.cache.TemplateCache;
import org.easyarch.slardar.cache.mode.DefaultCache;
import org.easyarch.slardar.cache.mode.FIFOCache;
import org.easyarch.slardar.cache.mode.LRUCache;
import org.easyarch.slardar.cache.mode.TimeOutCache;
import org.easyarch.slardar.entity.CacheEntity;

/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 上午12:18
 * description:
 */

public class TemplateCacheFactory extends AbstractCacheFactory<TemplateCache> {

    private static TemplateCacheFactory templateCacheFactory;

    private TemplateCache templateCache;

    private TemplateCacheFactory(){}

    public static TemplateCacheFactory getInstance(){
        synchronized (TemplateCacheFactory.class){
            if (templateCacheFactory == null){
                synchronized (TemplateCacheFactory.class){
                    templateCacheFactory = new TemplateCacheFactory();
                }
            }
        }
        return templateCacheFactory;
    }

    @Override
    public TemplateCache createCache(CacheEntity entity) {
        if (templateCache == null){
            if (entity != null &&entity.isEnable()){
                switch (entity.getMode()){
                    case FIFO:
                        templateCache = new TemplateCache(new FIFOCache<>(entity.getSize()));
                        return templateCache;
                    case LRU:
                        templateCache = new TemplateCache(new LRUCache<>(entity.getSize()));
                        return templateCache;
                    case TIMEOUT:
                        templateCache = new TemplateCache(new TimeOutCache<>(entity.getSize()));
                        return templateCache;
                    default:
                        templateCache = new TemplateCache(new DefaultCache<>());
                        return templateCache;
                }
            }
            templateCache = new TemplateCache(new DefaultCache<>());
        }
        return templateCache;
    }
}
