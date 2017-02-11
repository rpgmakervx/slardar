package org.easyarch.slardar.session;

import org.easyarch.slardar.jdbc.exec.MySqlExecutor;
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
        SqlExecutor executor = null;
        try {
            executor = new MySqlExecutor(configuration.getDataSource().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new DefaultDBSession(configuration,executor);
    }
    public DBSession newDelegateSession(){
        SqlExecutor executor = null;
        try {
            executor = new MySqlExecutor(configuration.getDataSource().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new MapperDBSession(configuration,executor);
    }

}
