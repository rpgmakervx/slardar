package org.easyarch.slardar.entity;

import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 上午12:03
 * description:
 */

public class TemplateEntity {

    private String json;

    private String namespace;

    private List<Map<String,String>> sqlMappers;

    public TemplateEntity(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<Map<String, String>> getSqlMappers() {
        return sqlMappers;
    }

    public void setSqlMappers(List<Map<String, String>> sqlMappers) {
        this.sqlMappers = sqlMappers;
    }
}
