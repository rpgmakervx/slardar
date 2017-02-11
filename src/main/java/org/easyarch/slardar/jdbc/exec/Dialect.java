package org.easyarch.slardar.jdbc.exec;

/**
 * Description :
 * Created by code4j on 16-12-25
 * 下午9:00
 * description:
 */

public enum Dialect {
    MYSQL(0),
    ORACLE(1),
    SQLSERVER(2);

    private int code;

    Dialect(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
