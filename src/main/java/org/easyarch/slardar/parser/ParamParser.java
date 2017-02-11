package org.easyarch.slardar.parser;

import static org.easyarch.slardar.parser.Token.KEY;
import static org.easyarch.slardar.parser.Token.POINT;

/**
 * Description :
 * Created by code4j on 17-1-19
 * 上午10:00
 * description:
 */

public class ParamParser extends ParserAdapter {

    private String []paramTokens;

    @Override
    public void parse(String src) {
        int beginIndex = KEY.length();
        String word = src.substring(beginIndex, src.length() - beginIndex);
        paramTokens = word.split(POINT);
    }

    public int getLevel(){
        return paramTokens.length;
    }

}
