package org.easyarch.test.pojo;

import org.easyarch.slardar.annotation.entity.Column;
import org.easyarch.slardar.annotation.entity.Table;

/**
 * Description :
 * Created by xingtianyu on 17-2-19
 * 下午7:52
 * description:
 */

@Table(tableName = "user")
public class Query {

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    private int pageIndex;

    private int pageSize;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
