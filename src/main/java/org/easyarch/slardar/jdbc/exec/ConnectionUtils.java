package org.easyarch.slardar.jdbc.exec;/**
 * Description : 
 * Created by YangZH on 16-11-2
 *  上午10:37
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Description :
 * Created by code4j on 16-11-2
 * 上午10:37
 */

public final class ConnectionUtils {

    public static void closeAll(Connection conn, Statement stmt,
                                    ResultSet rs) {
        try {
            if (rs != null&&!rs.isClosed()){
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null&&!stmt.isClosed())
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null){
                if (!conn.isClosed()){
                    conn.close();
                    System.out.println("connection close from DBUtils "+conn.isClosed());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void close(Connection conn) {
        closeAll(conn, null, null);
    }

    public static void close(ResultSet rs) {
        closeAll(null, null, rs);
    }

    public static void close(Statement stmt) {
        closeAll(null,stmt,null);
    }

    public static void beginTransaction(Connection conn){
        beginTransaction(conn,Connection.TRANSACTION_READ_COMMITTED);
    }

    public static void beginTransaction(Connection conn,int level){
        if (conn == null){
            new IllegalStateException("connection cannot be null!");
        }
        try {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(level);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn){
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollBack(conn);
        }
    }

    public static void endTransaction(Connection conn){
        endTransaction(conn,Connection.TRANSACTION_READ_COMMITTED);
    }

    public static void endTransaction(Connection conn,int level){
        if (conn == null){
            new IllegalStateException("connection cannot be null!");
        }
        try {
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(level);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollBack(Connection conn){
        try {
            if (conn != null){
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollBackAndClose(Connection conn){
        if (conn != null){
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                close(conn);
            }
        }
    }

    public static boolean isClose(Connection connection){
        try {
            connection.isValid(1000);
            return connection == null||connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean isValid(Connection connection){
        try {
            return connection == null||connection.isValid(3);
        } catch (SQLException e) {
            return false;
        }
    }

}
