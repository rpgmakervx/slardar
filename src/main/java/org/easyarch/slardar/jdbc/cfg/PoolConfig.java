package org.easyarch.slardar.jdbc.cfg;/**
 * Description : 
 * Created by YangZH on 16-11-4
 *  下午3:57
 */

import java.util.Properties;

/**
 * Description :
 * Created by code4j on 16-11-4
 * 下午3:57
 */

public class PoolConfig {

    public static final String MAXACTIVE = "maxActive";
    public static final String MINIDLE = "minIdle";
    public static final String INITIAL_SIZE = "initialSize";
    public static final String MAXWAIT = "maxWait";

    private int maxActive;

    private int minIdle;

    private int maxIdle;

    private long maxWait;

    private Properties properties;
    private PoolConfig(){
        properties = new Properties();
    }

    public static PoolConfig config(int maxPoolSize, int maxIdle,
                                    int minIdle, long keepAliveTime){
        PoolConfig config = new PoolConfig();
        config.maxActive = maxPoolSize<=0?Integer.MAX_VALUE:maxPoolSize;
        config.minIdle = minIdle<=0?0:minIdle;
        config.maxIdle = maxIdle<=0?Integer.MAX_VALUE:maxIdle;
        config.maxWait = keepAliveTime<=0?60:keepAliveTime;
        return config;
    }

    public static PoolConfig config(Properties prop){
        PoolConfig config = new PoolConfig();
        int maxPoolSize = prop.getProperty(MAXACTIVE)==null?
                Runtime.getRuntime().availableProcessors()*4:Integer.parseInt(prop.getProperty(MAXACTIVE));
        int minIdle = prop.getProperty(MINIDLE)==null?
                0:Integer.parseInt(prop.getProperty(MINIDLE));
        int maxIdle = prop.getProperty(INITIAL_SIZE)==null?
                512:Integer.parseInt(prop.getProperty(INITIAL_SIZE));
        int keepAliveTime = prop.getProperty(MAXWAIT)==null?
                60:Integer.parseInt(prop.getProperty(MAXWAIT));
        config.maxActive = maxPoolSize<=0?Integer.MAX_VALUE:maxPoolSize;
        config.minIdle = minIdle<=0?0:minIdle;
        config.maxIdle = maxIdle<=0?Integer.MAX_VALUE:maxIdle;
        config.maxWait = keepAliveTime<=0?60:keepAliveTime;
        ConnConfig.config(prop);
        return config;
    }

    public Properties getProperties(){
        properties.setProperty(MAXACTIVE,String.valueOf(maxActive));
        properties.setProperty(MINIDLE,String.valueOf(maxIdle));
        properties.setProperty(INITIAL_SIZE,String.valueOf(maxIdle));
        properties.setProperty(MAXWAIT,String.valueOf(maxWait));
        return properties;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void print() {
        System.out.println("maxActive:"+ maxActive +
                "\nmaxIdle:"+maxIdle+
                "\nminIdle:"+minIdle+
                "\nmaxWait:"+ maxWait);
    }
}
