package org.easyarch.slardar.jdbc.exec;/**
 * Description :
 * Created by YangZH on 16-11-3
 * 下午3:11
 */

import org.easyarch.slardar.cache.Cache;
import org.easyarch.slardar.cache.factory.QueryCacheFactory;
import org.easyarch.slardar.entity.CacheEntity;
import org.easyarch.slardar.jdbc.handler.ResultSetHandler;
import org.easyarch.slardar.utils.CodecUtils;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 下午3:11
 */

public class CachedExecutor extends AbstractExecutor {

    private Cache<String, Object> cache;

    private SqlExecutor executor;

    public CachedExecutor(SqlExecutor executor, CacheEntity entity) {
        this.executor = executor;
        this.cache = QueryCacheFactory.getInstance().createCache(entity);
    }

    /**
     * 缓存查询结果的执行器
     * @param sql
     * @param rshandler
     * @param params
     * @param <T>
     * @return
     */
    @Override
    public <T> T query(String sql, ResultSetHandler<T> rshandler, Object[] params) {
        String key = hybridInput(sql, params);
        T result = (T) cache.get(key);
        if (result != null) {
            System.out.println("hit execute cache");
            return result;
        }
        result = executor.query(sql, rshandler, params);
        cache.set(key,result);
        return result;
    }

    @Override
    public int alter(String sql, Object[] params) {
        return executor.alter(sql, params);
    }

    @Override
    public void alterBatch(String sql, Object[][] params) {
        executor.alterBatch(sql,params);
    }

    @Override
    public void rollback() {
        executor.rollback();
    }

    @Override
    public void close() {
        executor.close();
    }

    /**
     * 将sql和参数混合得到hash值
     * @param sql
     * @param params
     * @return
     */
    public String hybridInput(String sql, Object... params) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(sql);
        for (Object obj : params) {
            buffer.append(CodecUtils.sha1Obj(obj));
        }
        return CodecUtils.sha1(buffer.toString());
    }
}
