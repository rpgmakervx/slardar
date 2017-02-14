package org.easyarch.slardar.parser;

import org.easyarch.slardar.build.SqlBuilder;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.parser.script.JSContext;
import org.easyarch.slardar.session.Configuration;
import org.easyarch.slardar.utils.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static org.easyarch.slardar.parser.script.JSContext.CONTEXT;
import static org.easyarch.slardar.parser.script.JSContext.NAMESPACE;

/**
 * Description :
 * Created by xingtianyu on 17-1-27
 * 下午6:38
 * description:
 */

public class JSParser extends ParserAdapter<SqlEntity> {

    public static final String JAVASCRIPT = "javascript";

    private ScriptEngineManager engineManager ;

    private SqlBuilder sqlBuilder;

    private Reader reader;

    private JSContext ctx;

    private Configuration configuration;

    private static Map<String,SqlEntity> sqlValues = new HashMap<>();
    private static Map<String,ScriptEngine> jsFunctions = new HashMap<>();

    public JSParser(Reader reader,Configuration configuration){
        engineManager = new ScriptEngineManager();
        this.sqlBuilder = new SqlBuilder();
        this.configuration = configuration;
        this.reader = reader;
        ctx = new JSContext();
    }

    @Override
    public void parse(SqlEntity entity) {
        try {
            ScriptEngine engine = jsFunctions.get(entity.getPrefix());
            String namespace = null;
            if (engine == null){
                engine = engineManager.getEngineByName(JAVASCRIPT);
                engine.put(JSContext.CONTEXT,ctx.getCtx());
                engine.eval(reader);
                namespace = String.valueOf(((Map<String,Object>)engine.get(CONTEXT)).get(NAMESPACE));
                if (StringUtils.isEmpty(namespace)){
                    return;
                }
                jsFunctions.put(entity.getPrefix(),engine);
            }else{
                namespace = String.valueOf(((Map<String,Object>)engine.get(CONTEXT)).get(NAMESPACE));
            }
            if (!entity.getPrefix().equals(namespace)){
                return ;
            }
            Invocable func = (Invocable) engine;
            Map<String,Object> params = entity.getParams();
            String sql = (String) func.invokeFunction(entity.getSuffix(),params);
            configuration.addMappedSql(namespace,entity.getSuffix(),sql);
            sqlBuilder.buildSql(sql);
            sqlBuilder.buildParams(params);
            SqlEntity sqlEntity = sqlBuilder.buildEntity(entity.getBinder());
            sqlValues.put(namespace,sqlEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
