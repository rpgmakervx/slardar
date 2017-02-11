package org.easyarch.slardar.jdbc.type;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 16-12-26
 * 下午4:57
 * description:
 *
 CHAR            String
 VARCHAR         String
 LONGVARCHAR     String
 BOOL            boolean
 TINYINT         int
 SMALLINT        short
 INTEGER         int
 BIGINT          long
 FLOAT           float
 DOUBLE          double
 BINARY          byte[]
 VARBINARY       byte[]
 LONGVARBINARY   byte[]
 DATE            java.sql.Date
 TIMESTAMP       java.sql.Timestamp
 */

public class TypeMapper {

    public static Map<JDBCType,JAVAType> typeMapper = new HashMap<>();

    static {
        typeMapper.put(JDBCType.CHAR,JAVAType.CHAR);
        typeMapper.put(JDBCType.VARCHAR,JAVAType.STRING);
        typeMapper.put(JDBCType.LONGVARCHAR,JAVAType.STRING);
        typeMapper.put(JDBCType.BOOL,JAVAType.BOOL);
        typeMapper.put(JDBCType.TINYINT,JAVAType.INT);
        typeMapper.put(JDBCType.SMALLINT,JAVAType.SHORT);
        typeMapper.put(JDBCType.INTEGER,JAVAType.INT);
        typeMapper.put(JDBCType.BIGINT,JAVAType.LONG);
        typeMapper.put(JDBCType.FLOAT,JAVAType.FLOAT);
        typeMapper.put(JDBCType.DOUBLE,JAVAType.DOUBLE);
        typeMapper.put(JDBCType.BINARY,JAVAType.BYTEARRAY);
        typeMapper.put(JDBCType.BLOB,JAVAType.BYTEARRAY);
        typeMapper.put(JDBCType.DATE,JAVAType.DATE);
        typeMapper.put(JDBCType.TIMESTAMP,JAVAType.TIMESTAMP);
    }

    public static void test(Object ... param){
        System.out.println(param.length);
    }
    public static void main(String[] args) throws Exception {
        Object[] objs = new Object[]{new Date(),new Date(),new Date()};
        test(objs);
    }
}
