package org.easyarch.slardar.jdbc.wrapper;/**
 * Description : 
 * Created by YangZH on 16-11-2
 *  下午7:17
 */

import java.sql.ResultSet;
import java.util.List;

/**
 * Description :
 * Created by code4j on 16-11-2
 * 下午7:17
 */

public interface Wrapper<T>  {

    public List<T> list(ResultSet rs, Class<T> type);

    public T bean(ResultSet rs, Class<T> type);

}


