package org.easyarch.slardar.session;

import org.easyarch.slardar.entity.CacheEntity;
import org.easyarch.slardar.jdbc.exec.AbstractExecutor;
import org.easyarch.slardar.jdbc.exec.CachedExecutor;
import org.easyarch.slardar.jdbc.exec.SqlExecutor;
import org.easyarch.slardar.session.impl.DefaultDBSession;
import org.easyarch.slardar.session.impl.MapperDBSession;

import java.sql.SQLException;

/**
 * Description :
 * Created by xingtianyu on 16-12-29
 * 上午12:11
 * description:
 */

public class DBSessionFactory {

    private Configuration configuration;

    public DBSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    public DBSession newDefaultSession(){
        return new DefaultDBSession(configuration,getExecutor());
    }
    public DBSession newDelegateSession(){
        return new MapperDBSession(configuration,getExecutor());
    }

    private AbstractExecutor getExecutor(){
        AbstractExecutor executor = null;
        CacheEntity entity = configuration.getCacheEntity();
        try {
            if (entity.isEnable()){
                executor = new CachedExecutor(new SqlExecutor(
                        configuration.getDataSource().getConnection()),entity);
            }else{
                executor = new SqlExecutor(configuration.getDataSource().getConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return executor;
    }

}
