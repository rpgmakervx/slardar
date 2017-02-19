package org.easyarch.slardar.build;

import org.easyarch.slardar.binding.ParamBinder;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.mapping.SqlType;
import org.easyarch.slardar.parser.DruidSqlParser;

import java.util.LinkedHashMap;
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

    private DruidSqlParser sqlParser;

//    private ParamParser paramParser;

    private ParamBinder paramBinder;

    private SqlEntity entity;

    public SqlBuilder(){
        sqlParser = new DruidSqlParser();
        paramBinder = new ParamBinder();
        entity = new SqlEntity();
    }

    public SqlBuilder buildSql(String sql){
        sqlParser.parse(sql);
        entity.setSql(sql);
        entity.setPreparedSql(sqlParser.getPreparedSql());
        entity.setType(sqlParser.getType());
        return this;
    }

    public SqlBuilder buildParams(Object object,String name ){
        entity.addParam(paramBinder.reflect(object,name));
        return this;
    }
    public SqlBuilder buildParams(List objects, List<String> names){
        entity.addParam(paramBinder.reflect(objects,names));
        return this;
    }

    public SqlBuilder buildParams(Map<String,Object> params){
        entity.addParam(paramBinder.reflect(params));
        return this;
    }
    public SqlBuilder buildParams(Object object){
        entity.addParam(paramBinder.reflect(object,object.getClass()));
        return this;
    }

    /**
     * 重构SqlEntity,整理出需要的参数，将不在sql解析结果的参数抛弃掉
     * 这个方法要在sql解析后执行
     * @return
     */
    public SqlBuilder buildParams(){
        Map<String,Object> mapper = paramBinder.getMapper();
        List<String> paramNames = sqlParser.getSqlParamNames();
        entity.clear();
        for (String name:paramNames){
            for (Map.Entry<String,Object> entry:mapper.entrySet()){
//                System.out.println("name:"+name+",getKey:"+entry.getKey());
                if (entry.getKey().equals(name)){
                    entity.addParam(name,entry.getValue());
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
        buildSql(entity.getSql());
        buildParams(entity.getParams());
        buildParams();
        return this.entity;
    }

    public SqlEntity getSqlEntity(){
        return entity;
    }

    public SqlType getType(){
        return entity.getType();
    }

    public String getPreparedSql(){
        return entity.getPreparedSql();
    }

    public Map<String,Object> getParameters(){
        return entity.getParams();
    }

    public void setType(SqlType type) {
        entity.setType(type);
    }

    public void setPreparedSql(String preparedSql) {
        entity.setPreparedSql(preparedSql);
    }

    public void setParams(List<String> params) {
        sqlParser.setSqlParamNames(params);
    }

    @Override
    public String toString() {
        return sqlParser.getOriginSql()+" \n "+sqlParser.getSqlParamNames()+" \n "+entity.getParams();
    }


    public static void main(String[] args) {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("daaf","b");
        map.put("1","a");
        map.put("4","d");
        map.put("3","c");
        map.put("6","f");
        map.put("5","e");
        for (Map.Entry<String,String> entry:map.entrySet()){
            System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
        }
    }
}
