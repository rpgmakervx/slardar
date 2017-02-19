package org.easyarch.test.dao;

import org.easyarch.slardar.annotation.sql.SqlParam;
import org.easyarch.test.pojo.Query;
import org.easyarch.test.pojo.User;

import java.util.List;

/**
 * Description :
 * Created by xingtianyu on 17-2-9
 * 上午2:07
 * description:
 */

public interface UserMapper {

    public User findById(@SqlParam(name = "id") String id);

    public List<User> findByUser(User user);
    public List<User> findByQuery(Query query);

    public int getCount(User user);
    public int insert(User user);

    public void update(User user);
    public void deleteById(@SqlParam(name = "clientId") String id);
}
