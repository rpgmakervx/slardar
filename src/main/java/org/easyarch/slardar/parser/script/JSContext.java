package org.easyarch.slardar.parser.script;

import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-1-27
 * 下午11:19
 * description:
 */

public class JSContext {

    public static final String CONTEXT = "ctx";

    public static final String WHERE = "where";
    public static final String WHERE_CONTENT = " where 1 = 1 ";
    //默认namespace为空，需要用户自己设置
    public static final String NAMESPACE = "namespace";

    public Map<String,Map<String ,Object>> defaultVars;

    public JSContext(){
        defaultVars = new HashMap<>();
        init();
    }

    private void init(){
        Map<String,Object> vars = new HashMap<>();
        vars.put(WHERE,WHERE_CONTENT);
        vars.put(NAMESPACE,"");
        defaultVars.put(CONTEXT,vars);
    }

    public Map<String,Object> getCtx(){
        return defaultVars.get(CONTEXT);
    }
}
