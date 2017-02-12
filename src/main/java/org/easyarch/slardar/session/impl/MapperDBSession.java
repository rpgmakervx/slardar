package org.easyarch.slardar.session.impl;

import org.easyarch.slardar.cache.CacheFactory;
import org.easyarch.slardar.cache.ProxyCache;
import org.easyarch.slardar.jdbc.exec.SqlExecutor;
import org.easyarch.slardar.jdbc.handler.BaseTypeResultSetHandler;
import org.easyarch.slardar.jdbc.handler.BeanListResultSetHadler;
import org.easyarch.slardar.jdbc.handler.BeanResultSetHadler;
import org.easyarch.slardar.jdbc.handler.MapResultHandler;
import org.easyarch.slardar.mapping.MapperProxyFactory;
import org.easyarch.slardar.session.Configuration;
import org.easyarch.slardar.session.DBSessionAdapter;

import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午6:54
 * description:
 */

public class MapperDBSession extends DBSessionAdapter {

    private CacheFactory factory = CacheFactory.getInstance();

    private Configuration configuration;

    private SqlExecutor executor;

    public MapperDBSession(Configuration configuration, SqlExecutor executor) {
        this.executor = executor;
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String sql, Class<T> clazz, Object... parameters) {
        return executor.query(sql,new BeanResultSetHadler<T>(clazz),parameters);
    }

    @Override
    public <E> List<E> selectList(String sql, Class<E> clazz, Object... parameters) {
        List<E> list = executor.query(sql, new BeanListResultSetHadler<>(clazz), parameters);
        return list;
    }

    @Override
    public int selectCount(String sql, Object... parameters) {
        return executor.query(sql, new BaseTypeResultSetHandler<>(Integer.class), parameters);
    }

    @Override
    public List<Map<String, Object>> selectMap(String sql, Object... parameters) {
        List<Map<String, Object>> list = executor.query(sql, new MapResultHandler(), parameters);
        return list;
    }

    @Override
    public int update(String sql, Object... parameter) {
        return executor.alter(sql, parameter);
    }

    @Override
    public int delete(String sql, Object... parameter) {
        return update(sql, parameter);
    }

    @Override
    public int insert(String sql, Object... parameter) {
        return update(sql, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        ProxyCache proxyCache = factory.getProxyCache();
        if (proxyCache.isHit(clazz)){
            return (T) proxyCache.get(clazz);
        }
        MapperProxyFactory<T> mapperProxyFactory = new MapperProxyFactory(clazz);
        return mapperProxyFactory.newInstance(this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void close() {
        executor.close();
    }

    @Override
    public void rollback() {
        executor.rollback();
    }
}
