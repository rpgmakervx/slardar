package org.easyarch.slardar.jdbc.cfg;

import org.easyarch.slardar.utils.file.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 下午7:28
 */

public class ConnConfig {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String URL = "url";
    public static final String DRIVERNAME = "driverClassName";

    private static String user;
    private static String password;
    private static String url;
    private static String drivername;


    public static void config(String user,String password,
                              String url,String drivername){
        ConnConfig.user = user;
        ConnConfig.password = password;
        ConnConfig.url = url;
        ConnConfig.drivername = drivername;
        registerDriver();
    }

    public static void config(Properties props){
        config(props.getProperty(USERNAME),
                props.getProperty(PASSWORD),
                props.getProperty(URL),
                props.getProperty(DRIVERNAME));
        registerDriver();
    }

    public static void config(String path){
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            prop.load(fis);
            config(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeIO(fis);
        }
    }

    private static void registerDriver(){
        try {
            Class.forName(drivername);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUsername() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDrivername() {
        return drivername;
    }
}
