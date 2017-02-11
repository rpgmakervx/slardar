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

    private ScriptEngine engine;

    private SqlBuilder sqlBuilder;

    private Reader reader;

    private JSContext ctx;

    private Configuration configuration;

    private static Map<String,SqlEntity> sqlValues = new HashMap<>();
    private static Map<String,Invocable> jsFunctions = new HashMap<>();

    public JSParser(Reader reader,Configuration configuration){
        engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName(JAVASCRIPT);
        this.sqlBuilder = new SqlBuilder();
        this.configuration = configuration;
        this.reader = reader;
        ctx = new JSContext();
    }

    @Override
    public void parse(SqlEntity entity) {
        try {
            Invocable func = jsFunctions.get(entity.getPrefix());
            String namespace = null;
            System.out.println("suffix:"+entity.getSuffix());
            if (func == null){
                engine.put(JSContext.CONTEXT,ctx.getCtx());
                engine.eval(reader);
                func = (Invocable)engine;
                namespace = String.valueOf(((Map<String,Object>)engine.get(CONTEXT)).get(NAMESPACE));
                if (StringUtils.isEmpty(namespace)){
                    return;
                }
                jsFunctions.put(entity.getPrefix(),func);
            }
            if (!entity.getPrefix().equals(namespace)){
                return ;
            }
            Map<String,Object> params = entity.getFlatParams();
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
