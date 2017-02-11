package org.easyarch.slardar.jdbc.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by code4j on 16-12-25
 * 下午10:04
 * description:
 *
 CHAR            String
 VARCHAR         String
 LONGVARCHAR     String
 BOOL            boolean
 TINYINT         byte
 SMALLINT        short
 INTEGER         int
 BIGINT          long
 REAL            float
 FLOAT           double
 DOUBLE          double
 BINARY          byte[]
 VARBINARY       byte[]
 LONGVARBINARY   byte[]
 DATE            java.sql.Date
 TIME            java.sql.Time
 TIMESTAMP       java.sql.Tiimestamp
 */

public enum JDBCType {

    CHAR(0),
    VARCHAR(1),
    LONGVARCHAR(2),
    NUMERIC(3),
    DECIMAL(4),
    BOOL(5),
    TINYINT(6),
    SMALLINT(7),
    INTEGER(8),
    BIGINT(9),
    FLOAT(10),
    DOUBLE(11),
    BINARY(12),
    BLOB(13),
    DATE(14),
    DATETIME(15),
    TIMESTAMP(16);

    public final int TYPE_CODE;
    private static Map<Integer,JDBCType> codeLookup = new HashMap<Integer,JDBCType>();

    static {
        for (JDBCType type : JDBCType.values()) {
            codeLookup.put(type.TYPE_CODE, type);
        }
    }

    JDBCType(int code) {
        this.TYPE_CODE = code;
    }

    public static JDBCType forCode(int code)  {
        return codeLookup.get(code);
    }
}
