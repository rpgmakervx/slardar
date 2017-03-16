package org.easyarch.test.controlle;

import org.easyarch.test.pojo.User;
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
        User user = service.getUser("123567");
        System.out.println(user);
    }
}


