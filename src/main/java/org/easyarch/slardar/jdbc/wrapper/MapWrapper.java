package org.easyarch.slardar.jdbc.wrapper;/**
 * Description : 
 * Created by YangZH on 16-11-6
 *  下午6:09
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description :
 * Created by code4j on 16-11-6
 * 下午6:09
 */

public class MapWrapper extends WrapperAdapter<Map<String,Object>> implements Wrapper<Map<String,Object>> {

    public MapWrapper() {
        super(null);
    }

    @Override
    public List<Map<String, Object>> list(ResultSet rs, Class<Map<String, Object>> type) {
        List<Map<String, Object>> list = new CopyOnWriteArrayList<Map<String, Object>>();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                list.add(createMap(rs,meta));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String,Object> createMap(ResultSet rs, ResultSetMetaData meta) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            int count = meta.getColumnCount();
            for (int i = 0; i < count; i++) {
                Object value = rs.getObject(i + 1);
                resultMap.put(rs.getCursorName(), value);
            }
            return resultMap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
