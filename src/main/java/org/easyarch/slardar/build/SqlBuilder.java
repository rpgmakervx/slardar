package org.easyarch.slardar.build;

import org.easyarch.slardar.binding.ParamBinder;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.mapping.SqlType;
import org.easyarch.slardar.parser.SQLParser;

import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-1-19
 * 下午7:31
 * description:
 *
 * 构造一个SqlEntity时，先构造参数，再根据参数构造sql和类型，最后通过binder构造SqlEntity
 */

public class SqlBuilder {

    private SQLParser sqlParser;

//    private ParamParser paramParser;

    private ParamBinder paramBinder;

    private SqlEntity entity;

    public SqlBuilder(){
        sqlParser = new SQLParser();
        paramBinder = new ParamBinder();
        entity = new SqlEntity();
//        paramParser = new ParamParser();
    }

    public SqlBuilder buildSql(String sql){
        sqlParser.parse(sql);
        entity.setSql(sql);
        entity.setPreparedSql(sqlParser.getPreparedSql());
        entity.setType(sqlParser.getType());
        return this;
    }

    public SqlBuilder buildParams(Object object,String name ){
        paramBinder.reflect(object,name);
        return this;
    }
    public SqlBuilder buildParams(List objects, List<String> names){
        paramBinder.reflect(objects,names);
        return this;
    }

    public SqlBuilder buildParams(Map<String,Object> params){
        paramBinder.reflect(params);
        return this;
    }
    public SqlBuilder buildParams(Object object){
        paramBinder.reflect(object,object.getClass());
        return this;
    }

    public SqlBuilder prepareParams(){
        Map<String,Object> mapper = paramBinder.getMapper();
        List<String> paramNames = sqlParser.getSqlParamNames();
        for (String name:paramNames){
            for (Map.Entry<String,Object> entry:mapper.entrySet()){
                if (entry.getKey().equals(name)){
                    entity.addParam(entry.getKey(),entry.getValue());
                }
            }
        }
        return this;
    }

    public SqlEntity buildEntity(String bind){
        entity.setBinder(bind);
        return entity;
    }

    public SqlEntity buildEntity(SqlEntity entity){
        this.entity = entity;
        return this.entity;
    }

    public SqlType getType(){
        return sqlParser.getType();
    }

    public String getPreparedSql(){
        return sqlParser.getPreparedSql();
    }

    public List<Map<String,Object>> getParameters(){
        return entity.getParams();
    }

    /**
     * 无序的参数
     * @return
     */
    public Map<String,Object> getMapperParameters(){
        return paramBinder.getMapper();
    }


    public void setType(SqlType type) {
        sqlParser.setType(type);
    }

    public void setPreparedSql(String preparedSql) {
        sqlParser.setPreparedSql(preparedSql);
    }

    public void setParams(List<String> params) {
        sqlParser.setParamNames(params);
    }

    @Override
    public String toString() {
        return sqlParser.getOriginSql()+" \n "+sqlParser.getSqlParamNames()+" \n "+entity.getParams();
    }
}
