package org.easyarch.slardar.parser;

import java.io.InputStream;
import java.io.Reader;

/**
 * Description :
 * Created by xingtianyu on 17-1-27
 * 下午4:19
 * description:
 */

public class ParserAdapter<T> implements Parser<T> {

    @Override
    public void parse(String src) {

    }

    @Override
    public void parse(InputStream is) {

    }
    @Override
    public void parse(Reader reader) {

    }
    @Override
    public void parse(T obj) {

    }

    @Override
    public T parse() {
        return null;
    }
}
