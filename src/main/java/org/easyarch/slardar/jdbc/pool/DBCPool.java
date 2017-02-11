package org.easyarch.slardar.jdbc.pool;/**
 * Description :
 * Created by YangZH on 16-11-5
 * 下午10:04
 */

import org.easyarch.slardar.jdbc.cfg.ConnConfig;
import org.easyarch.slardar.jdbc.cfg.PoolConfig;
import org.easyarch.slardar.utils.CodecUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description :
 * Created by code4j on 16-11-5
 * 下午10:04
 */

public class DBCPool extends DataSourceAdapter {
    private Queue<Connection> conpool;
    private Queue<Connection> idleQueue;

    private final int maxPoolSize;
    private final int minIdle;
    private final int maxIdle;
    private final long keepAliveTime;
    private AtomicInteger currentPoolSize = new AtomicInteger(0);

    static {
        try {
            Class.forName(ProcessWatcher.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBCPool(PoolConfig config){
        maxPoolSize = config.getMaxActive();
        minIdle = config.getMinIdle();
        maxIdle = config.getMaxIdle();
        keepAliveTime = config.getMaxWait();
        conpool = new ConcurrentLinkedQueue<Connection>();
        idleQueue = new LinkedBlockingQueue<Connection>();
        initQueue();
    }

    public DBCPool(int maxPoolSize, int maxIdle,
                   int minIdle, long keepAliveTime, Queue queue) {
        PoolConfig.config(maxPoolSize, maxIdle, minIdle, keepAliveTime);
        this.maxPoolSize = maxPoolSize;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.keepAliveTime = keepAliveTime;
        conpool = new ConcurrentLinkedQueue<Connection>();
        idleQueue = queue;
        initQueue();
    }

    private void initQueue() {
        for (int index = 0; index < minIdle; index++) {
            try {
                Connection conn = createConnection();
                idleQueue.offer(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized Connection getConnection() {
        Connection conn = null;
        try {
            if (!idleQueue.isEmpty()) {
                conn = idleQueue.poll();
            }
            if (conn != null && !conn.isClosed()) {
                poolIn(conn);
            }else{
                if (currentPoolSize.get()  < maxPoolSize+ maxIdle) {
                    conn = createConnection();
//                    System.out.println("队列没有，在池中创建:" + conn);
                    poolIn(conn);
                    return conn;
                }
//                System.out.println("try again...." + idleQueue.size());
                long timestamp = System.currentTimeMillis();
                while (System.currentTimeMillis() - timestamp <= keepAliveTime) {
                    conn = idleQueue.poll();
                    if (conn != null){
                        poolIn(conn);
                        break;
                    }
                }
                if (conn == null) {
                    throw new RuntimeException("db connection pool was full");
                }
//                System.out.println("got it!!" + conn);
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void poolIn(Connection conn) throws Exception {
        if (conn != null){
            if (!conn.isClosed()){
                conpool.offer(conn);
                currentPoolSize.incrementAndGet();
//                System.out.println("连接入池成功"+conn);
            }else{
//                System.out.println("连接入池失败，连接已关闭");
            }
        }else{
//            System.out.println("连接入池失败，连接为空");
        }
    }

    private void poolOut(Connection conn){
        if (conpool.remove(conn)){
//            System.out.println("从池中删除连接");
            currentPoolSize.decrementAndGet();
            if (idleQueue.size() < maxIdle){
                idleQueue.offer(conn);
            }
        }
    }

    protected synchronized Connection createConnection() {
        try {
            Connection conn = DriverManager.getConnection(ConnConfig.getUrl()
                    , ConnConfig.getUsername(), ConnConfig.getPassword());
            long id = CodecUtils.hash(conn.toString());
            ConnectionWrapper wrapper = new ConnectionWrapper(id,conn);
            RealCPool.getConnections().add(wrapper);
            return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(),
                    new Class[]{Connection.class}, new ConnectionProxy(conn));
        } catch (SQLException e) {
            throw new RuntimeException("fail to create connection:\n" + e.getMessage());
        }
    }

    protected synchronized void recycle(Connection conn) {
        if (conn != null) {
            poolOut(conn);
//            System.out.println("recycle 总连接数--->"+RealCPool.getConnections().size()+", idle --> "+idleQueue.size()+",active-->"+conpool.size()+",activecount-->"+currentPoolSize.get());
        }else{
//            System.out.println("recycle fail...");
        }
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new RuntimeException("don't support this way to get connection");
    }

    class ConnectionProxy implements InvocationHandler {
        private static final String CLOSE = "close";
        private static final String EQUALS = "equals";

        private Connection conn;

        public ConnectionProxy(Connection conn) {
            this.conn = conn;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!CLOSE.equals(method.getName())) {
                method.setAccessible(true);
                if (EQUALS.equals(method.getName())){
                    return args[0] == proxy;
                }
                if (!conn.isClosed()) {
                    return method.invoke(conn, args);
                }
            }
            recycle((Connection) proxy);
            return null;
        }
    }

    class Monitor implements Runnable{
        private static final int PERIOD = 5;

        private static final String PING = "SELECT 1";
        private Connection conn;

        public Monitor(Connection conn){
            this.conn = conn;
        }

        @Override
        public void run() {

        }
    }

}
