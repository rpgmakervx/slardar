package org.easyarch.test;

import org.easyarch.slardar.parser.script.JSContext;
import org.easyarch.test.pojo.User;
import org.easyarch.test.service.UserService;
import org.junit.jupiter.api.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.List;

/**
 * Description :
 * Created by code4j on 16-11-7
 * 下午2:13
 */

public class TestMain {

    @Test
    public void testOne(){
        UserService service = new UserService();
        User user = service.getUser("1234567");
        System.out.println(user);
    }

    @Test
    public void testList(){
        UserService userService = new UserService();
        User user = new User();
        user.setPhone("13855496582");
        user.setUserName("code4j");
        List<User> users = userService.getUsers(user);
        System.out.println(users);
    }

    @Test
    public void testCount(){
        UserService service = new UserService();
        User user = new User();
        user.setUserName("code4j");
        int count = service.getUserCount(user);
        System.out.println(count);
    }



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