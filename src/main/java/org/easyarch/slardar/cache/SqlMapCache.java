package org.easyarch.slardar.cache;

import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.mapping.SqlType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 上午12:10
 * description:保存sql解析和参数构造的结果
 */

public class SqlMapCache implements Cache<String,Map<String,SqlEntity>> {

    private volatile Map<String,Map<String,SqlEntity>> sqlMap = new ConcurrentHashMap<>();

    public SqlEntity getSqlEntity(String namespace, String id){
        if(!isHit(namespace,id)){
            return new SqlEntity();
        }
        Map<String,SqlEntity> statement = get(namespace);
        return statement.get(id);
    }

    public void addSqlEntity(SqlEntity sqlEntity){
        String namespace = sqlEntity.getPrefix();
        String id = sqlEntity.getSuffix();
        if (sqlMap.containsKey(namespace)){
            Map<String,SqlEntity> statement = get(namespace);
            statement.put(id,sqlEntity);
            return;
        }
        Map<String,SqlEntity> statement = new ConcurrentHashMap<>();
        statement.put(id,sqlEntity);
        sqlMap.put(namespace,statement);
    }

    public String getSql(String namespace,String id){
        if(!isHit(namespace,id)){
            return "";
        }
        Map<String,SqlEntity> statement = sqlMap.get(namespace);
        return statement.get(id).getSql();
    }

    public List<Map<String,Object>> getParams(String namespace, String id){
        if(!isHit(namespace,id)){
            return new ArrayList<>();
        }
        Map<String,SqlEntity> statement = sqlMap.get(namespace);
        return statement.get(id).getParams();
    }
    public Map<String,Object> getFlatParams(String namespace, String id){
        if(!isHit(namespace,id)){
            return new HashMap<>();
        }
        Map<String,SqlEntity> statement = sqlMap.get(namespace);
        return statement.get(id).getFlatParams();
    }

    public SqlType getType(String namespace,String id){
        if(!isHit(namespace,id)){
            return null;
        }
        Map<String,SqlEntity> statement = sqlMap.get(namespace);
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
        return sqlMap.get(key);
    }

    @Override
    public void set(String key, Map<String,SqlEntity> value) {
        sqlMap.put(key,value);
    }

    @Override
    public Map<String,SqlEntity> remove(String key) {
        return sqlMap.remove(key);
    }

    @Override
    public boolean isHit(String key) {
        return sqlMap.containsKey(key);
    }


    @Override
    public void clear() {
        sqlMap.clear();
    }
}
