package org.easyarch.slardar.parser;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.easyarch.slardar.build.SqlBuilder;
import org.easyarch.slardar.cache.SqlMapCache;
import org.easyarch.slardar.cache.factory.SqlMapCacheFactory;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.utils.JsonUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-20
 * 下午10:55
 * description:
 */

public class TemplateParser extends ParserAdapter<SqlEntity>{

    private static final String NAME = "_default_template_key";

    private Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    private SqlMapCache sqlMapCache;


    public TemplateParser(String templateContent, org.easyarch.slardar.session.Configuration configuration) {
        this.sqlMapCache = SqlMapCacheFactory.getInstance()
                .createCache(configuration.getCacheEntity());
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(NAME,templateContent);
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
    }

    /**
     * 解析前检查一下缓存中有没有sql
     * @param entity
     */
    @Override
    public void parse(SqlEntity entity) {

        Map<String,Object> tmp = entity.getParams();
        StringWriter writer = new StringWriter();
        try {
            Template template = cfg.getTemplate(NAME);
            template.process(tmp,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> jsonMap = JsonUtils.json2Map(writer.toString());
        Map<String,Object> sqlMap = (Map<String, Object>) jsonMap.get(entity.getPrefix());
        String sql = (String) sqlMap.get(entity.getSuffix());
        SqlBuilder builder = new SqlBuilder();
        builder.buildSql(sql);
        builder.buildParams(tmp);
        builder.buildParams();
        SqlEntity se = builder.buildEntity(entity.getBinder());
        System.out.println("entity param:"+se.getParams());
        sqlMapCache.addSqlEntity(se);
    }

    public static void main(String[] args) {
        Map<String,Object> tmp = new HashMap<>();
        tmp.put("user","邢天宇");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(NAME,"hello ${user}");
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
        try {
            Template template = cfg.getTemplate(NAME);
            StringWriter writer = new StringWriter();
            template.process(tmp,writer);
            System.out.println(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
