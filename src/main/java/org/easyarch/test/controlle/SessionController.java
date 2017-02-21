package org.easyarch.test.controlle;

import org.easyarch.test.pojo.Query;
import org.easyarch.test.service.UserService;

/**
 * Description :
 * Created by xingtianyu on 17-2-12
 * 下午10:39
 * description:
 */

public class SessionController {

    public static void main(String[] args) {
        UserService service = new UserService();
//        User user = new User();
//        user.setPhone("1360000000");
        Query query = new Query();
        query.setUserName("code4j");
        query.setPageIndex(0);
        query.setPageSize(2);
//        user.setUserName("code4j");
//        user.setPassword("22s5d9f8rg");
//        user.setPhone("15189785965");
        System.out.println("查询结果："+service.getUsers(query));
//        User u = new User();
//        u.setUserName("code4j");
//        long begin = System.nanoTime();
//        System.out.println("第二次："+service.getUsers(u));
//        long end = System.nanoTime();
//        System.out.println(end - begin);
    }
}


