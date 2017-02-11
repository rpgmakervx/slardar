package org.easyarch.slardar.jdbc.pool;/**
 * Description : 
 * Created by YangZH on 16-11-4
 *  上午2:00
 */


import org.easyarch.slardar.jdbc.cfg.PoolConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Description :
 * Created by code4j on 16-11-4
 * 上午2:00
 */

public class DBCPoolFactory {

    public static DataSource newFixedDBCPool(int maxPoolSize) {
        return new DBCPool(maxPoolSize, maxPoolSize >> 2, maxPoolSize >> 3,
                1000 * 30L, new LinkedBlockingQueue<Connection>());
    }

    public static DataSource newCachedDBCPool() {
        return new DBCPool(Integer.MAX_VALUE, Integer.MAX_VALUE, 10,
                0L, new LinkedTransferQueue());
    }
    public static DataSource newConfigedDBCPool(Properties prop) {
        return new DBCPool(PoolConfig.config(prop));
    }
    public static DataSource newCustomDBCPool(PoolConfig config) {
        return new DBCPool(config);
    }

}
