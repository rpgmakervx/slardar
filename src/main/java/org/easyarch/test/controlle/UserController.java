package org.easyarch.test.controlle;


import org.easyarch.slardar.jdbc.exec.AbstractExecutor;
import org.easyarch.slardar.jdbc.exec.SqlExecutor;
import org.easyarch.slardar.jdbc.handler.BeanListResultSetHadler;
import org.easyarch.slardar.jdbc.pool.DBCPoolFactory;
import org.easyarch.slardar.utils.ResourcesUtil;
import org.easyarch.test.JDBCUtil;
import org.easyarch.test.pojo.User;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Description :
 * Created by xingtianyu on 17-2-9
 * 上午2:23
 * description:7000次查询 毫秒值
 * slardar: 4 861
 * jdbcutil: 4 052
 * jdbc: 3 000
 * mybatis: 890
 * cached slardar:1750
 *
 * 1次查询 纳秒：
 * jdbc: 620 745
 * jdbcutil: 2 113 198
 * jade: 1 333 903
 */

public class UserController {

    public static void main(String[] args) throws Exception {
//        Properties prop1 = new Properties();
//        prop1.load(ResourcesUtil.getResourceAsStream("/db.properties"));
//        DataSource dataSource1 = DBCPoolFactory.newConfigedDBCPool(prop1);
//        final SqlExecutor executor1 = new MySqlExecutor(dataSource1.getConnection());
//        Object[] params1 = new Object[]{"ewrgthgdsvng"};
//        executor1.query("select * from user where client_id = ? ",
//                new BeanListResultSetHadler<User>(User.class), params1);
//
//        Properties prop = new Properties();
//        prop.load(ResourcesUtil.getResourceAsStream("/db.properties"));
//        DataSource dataSource = DBCPoolFactory.newConfigedDBCPool(prop);
//        final SqlExecutor executor = new MySqlExecutor(dataSource.getConnection());
//        Object[] params = new Object[]{"ewrgthgdsvng"};
//        long begin2 = System.nanoTime();
//        List<User> users = executor.query("select * from user where client_id = ? ",
//                new BeanListResultSetHadler<User>(User.class), params);
//        long end2 = System.nanoTime();
//        System.out.println("jdbcutil use:"+(end2- begin2));


//        MybatisService service1 = new MybatisService();
//        service1.getUsername("ewrgthgdsvng");
//
//        MybatisService service = new MybatisService();
//        long begin1 = System.nanoTime();
//        service.getUsername("ewrgthgdsvng");
//        long end1 = System.nanoTime();
//        System.out.println("jade use:"+(end1- begin1));

        org.easyarch.test.service.UserService service = new org.easyarch.test.service.UserService();
//        System.out.println(service.getUser("ewrgthgdsvng"));;
        User user = new User();
        user.setClientId("8d9avt5d6h51w3p0n");
        user.setPhone("13855496582");
        user.setUserName("code4j");
        user.setPassword("3546635234");
//        service.deleteById("++++++");
        System.out.println();
        service.saveUser(user);
    }

    @Test
    public void test(){
        long begin1 = System.currentTimeMillis();
        for (int index = 0;index<10000;index++){
            System.out.println("");
        }
        long end1 = System.currentTimeMillis();
        long begin2 = System.currentTimeMillis();
        for (int index = 0;index<10000;index++){
        }
        long end2 = System.currentTimeMillis();

        System.out.println("1 use:"+(end1- begin1));
        System.out.println("2 use:"+(end2- begin2));
    }


    @Test
    public void jdbc() throws Exception {
        Connection con = JDBCUtil.getConnection();
        long begin1 = System.currentTimeMillis();
        PreparedStatement ps = con.prepareStatement("select * from user where client_id = ? ");
        ps.setString(1,"ewrgthgdsvng");
        ResultSet rs = ps.executeQuery();
        long end1 = System.currentTimeMillis();
        con.close();
        System.out.println("jdbc use:"+(end1-begin1));
    }

    @Test
    public void jdbcutil() throws Exception {
        Properties prop = new Properties();
        prop.load(ResourcesUtil.getResourceAsStream("/db.properties"));
        DataSource dataSource = DBCPoolFactory.newConfigedDBCPool(prop);
        final AbstractExecutor executor = new SqlExecutor(dataSource.getConnection());
        Object[] params = new Object[]{"ewrgthgdsvng"};
        long begin2 = System.currentTimeMillis();
//        for (int index = 0;index<7000;index++){
            List<User> users = executor.query("select * from user where client_id = ? ",
                    new BeanListResultSetHadler<User>(User.class), params);
//        }
        long end2 = System.currentTimeMillis();
        System.out.println("jdbcutil use:"+(end2- begin2));
    }
//
//    @Test
//    public void mybatis(){
//        MybatisService mybatisService = new MybatisService();
//        long begin3 = System.currentTimeMillis();
//        for (int index = 0;index<7000;index++){
//            mybatisService.getUser("ewrgthgdsvng");
//        }
//        long end3 = System.currentTimeMillis();
//        System.out.println("mybatis use:"+(end3- begin3));
//    }

    /**
     * druid:1676
     * jsqlparser:1711
     */
    @Test
    public void slardar(){
        org.easyarch.test.service.UserService service = new org.easyarch.test.service.UserService();
        long begin1 = System.currentTimeMillis();
        for (int index = 0;index<7000;index++){
            service.getUser("ewrgthgdsvng");
        }
        long end1 = System.currentTimeMillis();
        System.out.println("slardar use:"+(end1- begin1));
    }

    /**
     * jsqlparser:1 276 893 963(nano)
     * druid:1 289 766 447
     *
     * template js  :
     * template json:410 973 959
     *
     */
    @Test
    public void slardarOne(){
        org.easyarch.test.service.UserService service = new org.easyarch.test.service.UserService();
//        service.getUser("ewrgthgdsvng");
        long begin1 = System.nanoTime();
        service.getUser("ewrgthgdsvng");
        long end1 = System.nanoTime();
        System.out.println("slardar use:"+(end1- begin1));
    }

}
