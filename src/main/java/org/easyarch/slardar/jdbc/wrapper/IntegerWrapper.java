package org.easyarch.slardar.jdbc.wrapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description :
 * Created by xingtianyu on 17-2-12
 * 下午10:18
 * description:
 */

public class IntegerWrapper extends WrapperAdapter<Integer> {

    public IntegerWrapper() {
        super(null);
    }

    @Override
    public Integer bean(ResultSet rs, Class<Integer> type) {
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
