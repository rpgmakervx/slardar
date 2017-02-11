package org.easyarch.slardar.build;

import org.easyarch.slardar.cache.CacheFactory;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午1:39
 * description:
 */

public class SqlBuilderFactory {

    private String nameSpace;

    private String id;

    private CacheFactory factory = CacheFactory.getInstance();

    public SqlBuilderFactory(String nameSpace,String id){
        this.nameSpace = nameSpace;
        this.id = id;
    }
//
//    public SqlBuilder newInstance(String sql,Object[] args){
//        SqlMapCache cache = factory.getSqlMapCache();
//        SqlBuilder builder = new SqlBuilder();
//        //jsqlparser 在这一步，相对其他代码会慢一点
//        builder.buildSql(sql);
//        Parameter[] parameters = method.getParameters();
//        String[] paramNames = ReflectUtils.getMethodParameter(method);
//        int paramIndex = 0;
//        for (int index=0;index<args.length;index++) {
//            if (isBaseType(args[index].getClass())) {
//                SqlParam sqlParam = parameters[index].getAnnotation(SqlParam.class);
//                if (sqlParam == null) {
//                    builder.buildParams(args[index],paramNames[paramIndex]);
//                    paramIndex++;
//                }else{
//                    builder.buildParams(args[index],sqlParam.name());
//                }
//                continue;
//            }
//            if (args[index] instanceof Map) {
//                builder.buildParams((Map<String,Object>)args[index]);
//                continue;
//            }
//            builder.buildParams(args[index]);
//        }
//        builder.buildEntity();
//        cache.addSqlBuilder(interfaceName,method.getName(),builder);
//    }
//
//    private boolean isBaseType(Class clazz) {
//        if (clazz == String.class) {
//            return true;
//        }
//        if (clazz == int.class || clazz == Integer.class) {
//            return true;
//        }
//        if (clazz == float.class || clazz == Float.class) {
//            return true;
//        }
//        if (clazz == long.class || clazz == Long.class) {
//            return true;
//        }
//        if (clazz == double.class || clazz == Double.class) {
//            return true;
//        }
//        if (clazz == short.class || clazz == Short.class) {
//            return true;
//        }
//        if (clazz == byte.class || clazz == Byte.class) {
//            return true;
//        }
//        if (clazz == boolean.class || clazz == Boolean.class) {
//            return true;
//        }
//        if (clazz == char.class || clazz == Character.class) {
//            return true;
//        }
//        return false;
//    }
}
