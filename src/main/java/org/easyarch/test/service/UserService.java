package org.easyarch.test.service;

import org.easyarch.slardar.session.DBSession;
import org.easyarch.slardar.session.DBSessionFactory;
import org.easyarch.slardar.session.DBSessionFactoryBuilder;
import org.easyarch.slardar.utils.ResourcesUtil;
import org.easyarch.test.dao.UserMapper;
import org.easyarch.test.pojo.User;

import java.util.List;

/**
 * Description :
 * Created by xingtianyu on 17-2-9
 * 上午2:22
 * description:
 */

public class UserService {

    private UserMapper mapper;

    private DBSession session;

    public UserService(){
        try {
            DBSessionFactory sessionFactory = new DBSessionFactoryBuilder().build(
                    ResourcesUtil.getResourceAsStream("/config.xml"));
            session = sessionFactory.newDelegateSession();
            mapper = session.getMapper(UserMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public User getUser(String id){
        return mapper.findById(id);
    }
    public List<User> getUsers(User user){
        return mapper.findByUser(user);
    }

    public void saveUser(User user){
        mapper.insert(user);
    }

    public void update(User user){
        mapper.update(user);
    }

    public void deleteById(String id){
        mapper.deleteById(id);
    }
}
