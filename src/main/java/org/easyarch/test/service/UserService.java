package org.easyarch.test.service;

import org.easyarch.slardar.entity.Parameter;
import org.easyarch.slardar.session.DBSession;
import org.easyarch.slardar.session.DBSessionFactory;
import org.easyarch.slardar.session.DBSessionFactoryBuilder;
import org.easyarch.slardar.utils.ParamUtil;
import org.easyarch.slardar.utils.ResourcesUtil;
import org.easyarch.test.dao.UserMapper;
import org.easyarch.test.pojo.Query;
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
    private DBSession defaultSession;

    public UserService(){
        try {
            DBSessionFactory sessionFactory = new DBSessionFactoryBuilder().build(
                    ResourcesUtil.getResourceAsStream("/config.xml"));
            session = sessionFactory.newDelegateSession();
            defaultSession = sessionFactory.newDefaultSession();
            mapper = session.getMapper(UserMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUserCount(User user){
        return mapper.getCount(user);
    }

    public User getUser(String id){
        return mapper.findById(id);
    }

    public User fetchUser(String id){
        Parameter param = new Parameter("id",id);
        return defaultSession.selectOne(UserMapper.class.getName()+"@"+"findById",User.class,param);
    }
    public List<User> getUsers(User user){
        return mapper.findByUser(user);
    }
    public List<User> getUsers(Query query){
        return mapper.findByQuery(query);
    }

    public List<User> fetchUsers(User user){
        return defaultSession.selectList(UserMapper.class.getName()+"@"+"findByUser"
                ,User.class,ParamUtil.create(user));
    }

    public void saveUser(User user){
        mapper.insert(user);
    }
    public void insertUser(User user){
        defaultSession.insert(UserMapper.class.getName()+"@"+"insert", ParamUtil.create(user));
    }

    public void update(User user){
        mapper.update(user);
    }

    public List<User> searchUsers(User user){
        return session.selectList("select * from user where client_id = ?"
                ,User.class,user.getClientId());
    }

    public void deleteById(String id){
        mapper.deleteById(id);
    }

}
