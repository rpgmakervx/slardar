package org.easyarch.test;

import org.easyarch.slardar.parser.script.JSContext;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Description :
 * Created by code4j on 16-11-7
 * 下午2:13
 */

public class TestMain {

//    private static final int BARRIER = 100;

//    public static void main(String[] args) throws SQLException {
//        ConnConfig.config("root", "123456",
//                "jdbc:mysql://localhost:3306/database?useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false", "com.mysql.jdbc.Driver");
//        PoolConfig.config(200, 50, 20, 3 * 1000L);
//        DataSource pool = DBCPoolFactory.newConfigedDBCPool();
//        List<Connection> cons = new LinkedList<>();
//        Connection con1 = pool.getConnection();
//        Connection con2 = pool.getConnection();
//        Connection con3 = pool.getConnection();
//        Connection con4 = pool.getConnection();
//        Connection con5 = pool.getConnection();
//        cons.add(con1);
//        cons.add(con2);
//        cons.add(con3);
//        cons.add(con4);
//        cons.add(con5);
//        Connection c1 = cons.get(0);
//        Connection c2 = cons.get(1);
//        Connection c3 = cons.get(2);
//        Connection c4 = cons.get(3);
//        Connection c5 = cons.get(4);
//        String s = new String("12");
//        System.out.println(s.equals(s));
//        System.out.println(con1.equals(c1));
//    }

    public static void main(String[] args) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("javascript");
        try {
//            UserVO user = new UserVO();
//            user.setId(1000);
//            user.setUsername("xingtianyu");
//            Map<String,Object> usermap = new HashMap<>();
//            usermap.put("id",user.getId());
//            usermap.put("username",user.getUsername());
            JSContext context = new JSContext();
            engine.put(JSContext.CONTEXT,context.getCtx());
            engine.eval(new FileReader("/home/code4j/IDEAWorkspace/myutils/myutils-slardar/src/main/resources/mapper/usermapper.js"));
            Invocable func = (Invocable)engine;
//            Map<String,Object> resultMap = (Map<String, Object>) func.invokeFunction("findUserByCondition",usermap);
//            Map<String,Object> paramMap = (Map<String, Object>) resultMap.get("param");
//            System.out.println(resultMap.get("sql"));
//            System.out.println(paramMap.get("1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}