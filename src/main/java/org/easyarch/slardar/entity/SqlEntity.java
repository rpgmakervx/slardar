package org.easyarch.slardar.entity;

import org.easyarch.slardar.mapping.SqlType;

import java.util.*;

import static org.easyarch.slardar.parser.Token.BIND_SEPARATOR;

/**
 * Description :
 * Created by xingtianyu on 17-1-21
 * 下午7:19
 * description:
 */

public class SqlEntity {

    private String sql;

    private String preparedSql;

    private Map<String, Object> params;

    private SqlType type;

    //sql语句的key,通常是 mapper接口名@方法名
    private String binder;

    public SqlEntity() {
        params = new LinkedHashMap<>();
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getPreparedSql() {
        return preparedSql;
    }

    public void setPreparedSql(String preparedSql) {
        this.preparedSql = preparedSql;
    }

    public SqlType getType() {
        return type;
    }

    public void setType(SqlType type) {
        this.type = type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String name, Object value) {
        params.put(name,value);
    }
    public void addParam(Map<String,Object> params) {
        this.params.putAll(params);
    }

    public void delParam(String name){
        params.remove(name);
    }

    public void clear(){
        params.clear();
    }

    public String getBinder() {
        return binder;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public String getPrefix() {
        String[] segements = binder.split(BIND_SEPARATOR);
        return segements[0];
    }

    public String getSuffix() {
        String[] segements = binder.split(BIND_SEPARATOR);
        return segements[1];
    }
}
