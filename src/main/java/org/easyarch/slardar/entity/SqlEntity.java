package org.easyarch.slardar.entity;

import org.easyarch.slardar.mapping.SqlType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<Map<String, Object>> params;

    private SqlType type;

    //sql语句的key,通常是 mapper接口名@方法名
    private String binder;

    public SqlEntity() {
        params = new ArrayList<>();
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public Map<String, Object> getFlatParams() {
        Map<String, Object> flatParams = new HashMap<>();
        for (Map<String, Object> flatMap : params) {
            flatParams.putAll(flatMap);
        }
        return flatParams;
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

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }

    public void setType(SqlType type) {
        this.type = type;
    }

    public void addParam(String name, Object value) {
        Map<String, Object> param = new HashMap<>();
        param.put(name,value);
        params.add(param);
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
