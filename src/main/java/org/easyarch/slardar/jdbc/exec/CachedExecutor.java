package org.easyarch.slardar.jdbc.exec;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  下午3:11
 */

import org.easyarch.slardar.cache.Cache;
import org.easyarch.slardar.cache.CacheFactory;

import java.sql.Connection;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 下午3:11
 */

public class CachedExecutor extends SqlExecutor {

    private Cache<String,Object> cache = CacheFactory.getInstance().getQueryCache();

    public CachedExecutor(Connection conn){
        super(conn);
    }

}
