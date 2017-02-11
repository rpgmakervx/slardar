package org.easyarch.slardar.session.impl;

import org.easyarch.slardar.cache.CacheFactory;
import org.easyarch.slardar.cache.SqlMapCache;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.jdbc.exec.SqlExecutor;
import org.easyarch.slardar.jdbc.handler.BeanListResultSetHadler;
import org.easyarch.slardar.jdbc.handler.MapResultHandler;
import org.easyarch.slardar.session.Configuration;
import org.easyarch.slardar.session.DBSessionAdapter;
import org.easyarch.slardar.utils.CollectionUtils;

import java.util.List;
import java.util.Map;

import static org.easyarch.slardar.parser.Token.BIND_SEPARATOR;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 上午12:38
 * description:
 */

public class DefaultDBSession extends DBSessionAdapter {

    private CacheFactory factory = CacheFactory.getInstance();

    private SqlExecutor executor;

    private Configuration configuration;

    public DefaultDBSession(Configuration configuration, SqlExecutor executor) {
        this.executor = executor;
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String bind, Class<T> clazz, Object... parameters) {
        List<T> list = this.selectList(bind, clazz, parameters);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public <E> List<E> selectList(String bind, Class<E> clazz, Object... parameters) {
        String[] tokens = bind.split(BIND_SEPARATOR);
//        SqlEntity entity = cache.getSqlEntity();
        SqlEntity entity = new SqlEntity();
        entity.setBinder(bind);
//        entity.setParamNames();
//        configuration.parseMappedSql();
        String sql = configuration.getMappedSql(tokens[0], tokens[1]);
        List<E> list = executor.query(sql, new BeanListResultSetHadler<>(clazz), parameters);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectMap(String bind, Object... parameters) {
        SqlMapCache cache = factory.getSqlMapCache();
        String[] tokens = bind.split(BIND_SEPARATOR);
        SqlEntity entity = cache.getSqlEntity(tokens[0], tokens[1]);
        String sql = entity.getPreparedSql();
        List<Map<String, Object>> list = executor.query(sql, new MapResultHandler(), parameters);
        return list;
    }

    @Override
    public int update(String bind, Object... parameter) {
        SqlMapCache cache = factory.getSqlMapCache();
        String[] tokens = bind.split(BIND_SEPARATOR);
        SqlEntity entity = cache.getSqlEntity(tokens[0], tokens[1]);
        String sql = entity.getPreparedSql();
        return executor.alter(sql, parameter);
    }

    @Override
    public int delete(String bind, Object... parameter) {
        return update(bind,parameter);
    }

    @Override
    public int insert(String bind, Object... parameter) {
        return update(bind,parameter);
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
