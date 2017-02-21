package org.easyarch.test;/**
 * Description : 
 * Created by YangZH on 16-11-3
 *  上午2:04
 */

import org.easyarch.slardar.annotation.entity.Column;

import java.util.HashMap;
import java.util.List;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 上午2:04
 */
//@Table(tableName = "t_user")
public class UserVO {

    @Column(name = "t_id")
    private int id;
    @Column(name = "userName")
    private String username;
    @Column(name = "passwd")
    private String password;
    @Column(name = "mobile")
    private String phone;
    @Column(name = "age")
    private int age;

    private List<String> items;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
//        System.out.println(new Date(1487486791000l));
        System.out.println((9 & (4-1)));
        HashMap map = new HashMap();
    }
}
