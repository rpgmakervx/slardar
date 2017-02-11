package org.easyarch.slardar.parser;

import java.io.InputStream;
import java.io.Reader;

/**
 * Description :
 * Created by xingtianyu on 17-1-19
 * 上午10:01
 * description:
 */

public interface Parser<T> {

    public void parse(String src);

    public void parse(InputStream is);

    public void parse(Reader reader);

    public void parse(T obj);

    public T parse();

}
