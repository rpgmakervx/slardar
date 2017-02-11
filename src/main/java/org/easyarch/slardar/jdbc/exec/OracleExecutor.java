package org.easyarch.slardar.jdbc.exec;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  下午3:11
 */

import java.sql.Connection;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 下午3:11
 */

public class OracleExecutor extends SqlExecutor {

    public OracleExecutor(Connection conn){
        super(conn,false);
    }

}
