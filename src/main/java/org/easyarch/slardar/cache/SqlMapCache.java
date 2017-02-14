package org.easyarch.slardar.cache;

import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.mapping.SqlType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 上午12:10
 * description:保存sql解析和参数构造的结果
 */

public class SqlMapCache implements Cache<String,Map<String,SqlEntity>> {

    private volatile Map<String,Map<String,SqlEntity>> sqlMap = new ConcurrentHashMap<>();

    private volatile Cache<String,Map<String,SqlEntity>> cache;

    public SqlMapCache(Cache<String, Map<String, SqlEntity>> cache) {
        this.cache = cache;
    }

    public SqlEntity getSqlEntity(String namespace, String id){
        Map<String,SqlEntity> statement = get(namespace);
        if(statement == null){
            return new SqlEntity();
        }
        return statement.get(id);
    }

    public void addSqlEntity(SqlEntity sqlEntity){
        String namespace = sqlEntity.getPrefix();
        String id = sqlEntity.getSuffix();
        Map<String,SqlEntity> statement = get(namespace);
        if (statement != null){
            statement.put(id,sqlEntity);
            return;
        }
        statement = new ConcurrentHashMap<>();
        statement.put(id,sqlEntity);
        cache.set(namespace,statement);
    }

    public String getSql(String namespace,String id){
        Map<String,SqlEntity> statement = cache.get(namespace);
        if(statement == null){
            return "";
        }
        return statement.get(id).getSql();
    }

    public Map<String,Object> getParams(String namespace, String id){
        Map<String,SqlEntity> statement = cache.get(namespace);
        if(statement == null){
            return new LinkedHashMap<>();
        }
        return statement.get(id).getParams();
    }

    public SqlType getType(String namespace,String id){
        Map<String,SqlEntity> statement = cache.get(namespace);
        if(statement == null){
            return null;
        }
        return statement.get(id).getType();
    }

    public boolean isHit(String namespace,String id){
        Map<String,SqlEntity> map = get(namespace);
        if (map != null && map.containsKey(id)){
            return true;
        }
        return false;
    }

    @Override
    public Map<String,SqlEntity> get(String key) {
        return cache.get(key);
    }

    @Override
    public void set(String key, Map<String,SqlEntity> value) {
        cache.set(key,value);
    }

    @Override
    public Map<String,SqlEntity> remove(String key) {
        return cache.remove(key);
    }

    @Override
    public boolean isHit(String key) {
        return cache.isHit(key);
    }


    @Override
    public void clear() {
        cache.clear();
    }
}
