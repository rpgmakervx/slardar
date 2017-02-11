package org.easyarch.slardar.session;

import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-1-30
 * 下午6:56
 * description:
 */

public class DBSessionAdapter implements DBSession {
    @Override
    public <T> T selectOne(String bindOrSql, Class<T> clazz, Object... parameter) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String bindOrSql, Class<E> clazz, Object... parameter) {
        return null;
    }

    @Override
    public int selectCount(String bindOrSql, Object... parameters) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectMap(String bindOrSql, Object... parameters) {
        return null;
    }

    @Override
    public int update(String bindOrSql, Object... parameter) {
        return 0;
    }

    @Override
    public int delete(String bindOrSql, Object... parameter) {
        return 0;
    }

    @Override
    public int insert(String bindOrSql, Object... parameter) {
        return 0;
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void rollback() {

    }
}
