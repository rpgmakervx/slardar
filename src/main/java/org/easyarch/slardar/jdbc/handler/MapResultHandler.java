package org.easyarch.slardar.jdbc.handler;

import org.easyarch.slardar.jdbc.wrapper.MapWrapper;
import org.easyarch.slardar.jdbc.wrapper.Wrapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-1-25
 * 下午4:17
 * description:
 */

public class MapResultHandler implements ResultSetHandler<List<Map<String,Object>>> {

    protected Wrapper wrapper;

    public MapResultHandler() {
        this.wrapper = new MapWrapper();
    }

    @Override
    public List<Map<String, Object>> handle(ResultSet rs) throws Exception {
        return wrapper.list(rs,Object.class);
    }
}
