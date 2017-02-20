Slardar Sql Mapper Framework for Java
=====================================
![slardar](https://github.com/rpgmakervx/slardar/blob/master/doc/image/slardar-logo.png)

Slardar is a frame of data persistence layer,whitch features are similar to **mybatis** , **hibernate** etc...
Slardar use **javascript** to build dynamic sql,whitch is easier for some one who familiar with **javascript**.
It also provides annotaions to build relational mapping.
Simplicity is the biggest advantage of the Slardar data mapper over object relational mapping tools.
## Features:
 * use **javascript** to build dynamic sql。
 * support simple inerface define to map your sql, or simply use native jdbc interface.
 * connection pool is supported by default, or the third pool (now contains **DBCP** and **Druid**)

## Install:
 ```xml
    <dependency>
        <groupId>org.easyarch</groupId>
        <artifactId>slardar</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
 ```
## Get Started:

### A simple query:
 
 Assume you have a table named: t_user. A query demo like this:
 
 First create a class: ```java User```
 ```java
 package org.easyarch.test.pojo;
 import org.easyarch.slardar.annotation.entity.Column;
 import org.easyarch.slardar.annotation.entity.Table;
 @Table(tableName = "user")
 public class User {
 
     @Column(name = "client_id")
     private String clientId;
 
     @Column(name = "username")
     private String userName;
 
     @Column(name = "password")
     private String password;
 
     @Column(name = "phone")
     private String phone;
 
     public String getClientId() {
         return clientId;
     }
 
     public void setClientId(String clientId) {
         this.clientId = clientId == null ? null : clientId.trim();
     }
 
     public String getUserName() {
         return userName;
     }
 
     public void setUserName(String userName) {
         this.userName = userName;
     }
 
     public String getPassword() {
         return password;
     }
 
     public void setPassword(String password) {
         this.password = password == null ? null : password.trim();
     }
 
     public String getPhone() {
         return phone;
     }
 
     public void setPhone(String phone) {
         this.phone = phone == null ? null : phone.trim();
     }
 
 }
 ```
 
 Then you finish object-relational mapping,next to create an mapper:
 ```java
package org.easyarch.test.dao;

import org.easyarch.slardar.annotation.sql.SqlParam;
import org.easyarch.test.pojo.User;

import java.util.List;

public interface UserMapper {

    public User findById(@SqlParam(name = "id") String id);
    public List<User> findByUser(User user);

}
 ```
 Use slardar's api: ```java DBSession```,
 create a class:
 ```java
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
}
 ```
 Finally make some configurations:
 First make some configuration to init db,create **resources/db.properties**:
  ```properties
 username = *****
 password = *****
 url = jdbc:mysql://localhost:3306/testdb?useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false
 driverClassName = com.mysql.jdbc.Driver
 
 maxActive =200
 minIdle = 10
 initialSize = 50
 maxWait = 6000
  ```
  and **resources/config.xml**
  ```xml
 <configuration>
     <interface package="org.easyarch.test.dao" />
 
     <mapper location="classpath:mapper/" />
 
     <datasource location="classpath:db.properties" class=""/>
 </configuration>
  ```
  write your sql in **resources/mapper/usermapper.java**
  ```javascript
ctx.namespace = "org.easyarch.test.dao.UserMapper";

function findById(params){
    return "select * from t_user where client_id = $id$";
}
function findByUser(params) {
    var sql = "select * from user" + ctx.where;
    if (params.username != undefined){
        sql += " and username = $username";
    }
    if (params.phone != undefined){
        sql += " and phone = $phone$";
    }
    if (params.client_id != undefined){
        sql += " and client_id = $client_id";
    }
    return sql;
}
  ```
  
  Write a main function to test it:
  ```java
public class Main{
    public static void main(String[] args){
        UserService service = new UserService();
        System.out.println(service.getUser("123456"));
        
        User user = new User();
        user.setUserName("code4j");
        user.setPhone("13600000000");
        System.out.println(service.getUsers(user));
    }
}
  ```
  
  more infomation in Chinese go to: