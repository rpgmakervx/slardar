package org.easyarch.slardar.cache;

import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.utils.CodecUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 上午12:10
 * description:保存sql解析和参数构造的结果
 */

public class SqlMapCache implements Cache<String,SqlEntity> {

    private volatile Cache<String,SqlEntity> cache;

    public SqlMapCache(Cache<String,SqlEntity> cache) {
        this.cache = cache;
    }

//    public SqlEntity getSqlEntity(String namespace, String id){
//        Map<String,SqlEntity> statement = get(namespace);
//        if(statement == null){
//            return new SqlEntity();
//        }
//        return statement.get(id);
//    }

    public SqlEntity getSqlEntity(String namespace, String id,Object[] params){
//        String namespaceHash = hashBinder(namespace);
//        String idHash = hashBinder(id);
//        String paramHash = hashParams(params);
//        Map<String, Map<String, SqlEntity>> idParamStatement = get(namespaceHash);
//        if(idParamStatement == null){
//            return new SqlEntity();
//        }else{
//            Map<String, SqlEntity> paramStatment = idParamStatement.get(idHash);
//            if (paramStatment != null){
//                return paramStatment.get(paramHash);
//            }else{
//                return new SqlEntity();
//            }
//        }
        String key = hashEntity(namespace,id,params);
        return get(key);
    }
    public SqlEntity getSqlEntity(String namespace, String id,Collection params){
        return getSqlEntity(namespace,id,params.toArray());
    }

    public void addSqlEntity(SqlEntity sqlEntity){
        String key = null;
        if (sqlEntity.getParams() != null){
            Collection col = sqlEntity.getParams().values();
            key = hashEntity(sqlEntity.getPrefix()
                    ,sqlEntity.getSuffix(),col.toArray());
        }else{
            key = hashEntity(sqlEntity.getPrefix()
                    ,sqlEntity.getSuffix(),new Object[0]);
        }
        set(key,sqlEntity);
    }

    public boolean isHit(String namespace,String id,Object[] params){
        String key = hashEntity(namespace,id,params);
        return isHit(key);
    }
    public boolean isHit(String namespace,String id,Collection params){
        String key = hashEntity(namespace,id,params.toArray());
        return isHit(key);
    }

    @Override
    public SqlEntity get(String key) {
        return cache.get(key);
    }

    @Override
    public void set(String key, SqlEntity value) {
        cache.set(key,value);
    }

    @Override
    public SqlEntity remove(String key) {
        return cache.remove(key);
    }

    @Override
    public boolean isHit(String key) {
        return cache.isHit(key);
    }

    private String hashEntity(String namespace,String id,Object[] params){
        StringBuffer keyBuffer = new StringBuffer();
        keyBuffer.append(CodecUtils.sha1(namespace))
                .append(CodecUtils.sha1Obj(id))
                .append(CodecUtils.sha1Obj(params));
        return CodecUtils.sha1(keyBuffer.toString());
    }

    private String hashBinder(String namespace){
        return CodecUtils.sha1(namespace);
    }

    private String hashParams(Collection params){
        return CodecUtils.sha1Obj(params);
    }
    private String hashParams(Object[] params){
        return CodecUtils.sha1Obj(params);
    }
    @Override
    public void clear() {
        cache.clear();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String[] array = list.toArray(new String[list.size()]);
        System.out.println(array.length);
    }
}
