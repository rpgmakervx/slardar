package org.easyarch.slardar.session.impl;

import org.easyarch.slardar.build.SqlBuilder;
import org.easyarch.slardar.cache.CacheFactory;
import org.easyarch.slardar.cache.SqlMapCache;
import org.easyarch.slardar.entity.Parameter;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.jdbc.exec.SqlExecutor;
import org.easyarch.slardar.jdbc.handler.BeanListResultSetHadler;
import org.easyarch.slardar.jdbc.handler.IntegerResultSetHandler;
import org.easyarch.slardar.jdbc.handler.MapResultHandler;
import org.easyarch.slardar.session.Configuration;
import org.easyarch.slardar.session.DBSessionAdapter;
import org.easyarch.slardar.utils.CollectionUtils;
import org.easyarch.slardar.utils.ReflectUtils;

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
    public <T> T selectOne(String bindOrSql, Class<T> clazz, Map<String, Object> parameter) {
        return super.selectOne(bindOrSql, clazz, parameter);
    }

    @Override
    public <E> List<E> selectList(String bind, Class<E> clazz, Object... parameters) {
        SqlEntity entity = genEntity(bind,parameters);
        List<E> list = executor.query(entity.getPreparedSql(),
                new BeanListResultSetHadler<>(clazz),
                CollectionUtils.gatherMapListsValues(entity.getParams()));
        return list;
    }

    @Override
    public <E> List<E> selectList(String bind, Class<E> clazz, Map<String, Object> parameters) {
        Object[] params = CollectionUtils.gatherMapListsValues(parameters);
        SqlEntity entity = genEntity(bind,params);
        List<E> list = executor.query(entity.getPreparedSql(),
                new BeanListResultSetHadler<>(clazz),params);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectMap(String bind, Object... parameters) {
        SqlEntity entity = genEntity(bind,parameters);
        List<Map<String, Object>> list = executor.query(entity.getPreparedSql(), new MapResultHandler(),
                CollectionUtils.gatherMapListsValues(entity.getParams()));
        return list;
    }

    @Override
    public List<Map<String, Object>> selectMap(String bind, Map<String, Object> parameters) {
        Object[] params = CollectionUtils.gatherMapListsValues(parameters);
        SqlEntity entity = genEntity(bind,params);
        List<Map<String, Object>> list = executor.query(entity.getPreparedSql(), new MapResultHandler(),params);
        return list;
    }

    @Override
    public int selectCount(String bind, Object... parameters) {
        SqlEntity entity = genEntity(bind,parameters);
        Integer count = executor.query(entity.getPreparedSql(),
                new IntegerResultSetHandler(),
                CollectionUtils.gatherMapListsValues(entity.getParams()));
        return count;
    }

    @Override
    public int selectCount(String bind, Map<String, Object> parameters) {
        Object[] params = CollectionUtils.gatherMapListsValues(parameters);
        SqlEntity entity = genEntity(bind,params);
        Integer count = executor.query(entity.getPreparedSql(),
                new IntegerResultSetHandler(),params);
        return count;
    }

    @Override
    public int update(String bind, Object... parameters) {
        SqlEntity entity = genEntity(bind,parameters);
        return executor.alter(entity.getPreparedSql(),
                CollectionUtils.gatherMapListsValues(entity.getParams()));
    }

    @Override
    public int update(String bindOrSql, Map<String, Object> parameter) {
        return super.update(bindOrSql, parameter);
    }

    @Override
    public int delete(String bind, Object... parameter) {
        return update(bind,parameter);
    }

    @Override
    public int delete(String bindOrSql, Map<String, Object> parameter) {
        return super.delete(bindOrSql, parameter);
    }

    @Override
    public int insert(String bind, Object... parameter) {
        return update(bind,parameter);
    }

    @Override
    public int insert(String bindOrSql, Map<String, Object> parameter) {
        return super.insert(bindOrSql, parameter);
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

    private SqlEntity genEntity(String bind, Object... parameters){
        if (!ReflectUtils.isUnifiedCollection(parameters, Parameter.class)){
            throw new IllegalArgumentException("All these paramters's type should be instanceof Parameter or null.");
        }
        String[] tokens = bind.split(BIND_SEPARATOR);
        String interfaceName = tokens[0];
        String methodName = tokens[1];
        SqlBuilder builder = new SqlBuilder();
        SqlMapCache cache = factory.getSqlMapCache();
        if (cache.isHit(interfaceName,methodName)) {
            SqlEntity entity = cache.getSqlEntity(interfaceName,methodName);
            builder.buildEntity(entity);
        }else{
            for (int index=0;index>parameters.length;index++){
                Parameter param = (Parameter) parameters[index];
                builder.buildParams(param.getValue(),param.getName());
            }
        }
        SqlEntity entity = new SqlEntity();
        entity.setParams(CollectionUtils.flatMapLists(builder.getMapperParameters()));
        entity.setBinder(bind);
        configuration.parseMappedSql(entity);
        String sql = configuration.getMappedSql(interfaceName, methodName);
        //jsqlparser 在这一步，相对其他代码会慢一点
        builder.buildSql(sql);
        builder.prepareParams();
        SqlEntity se = builder.buildEntity(bind);
        cache.addSqlEntity(se);
        return se;
    }
}
