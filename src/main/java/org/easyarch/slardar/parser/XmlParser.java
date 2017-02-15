package org.easyarch.slardar.parser;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyarch.slardar.mapping.MapperScanner;
import org.easyarch.slardar.session.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

import static org.easyarch.slardar.session.Configuration.DATASOURCE;
import static org.easyarch.slardar.session.Configuration.LIST;
import static org.easyarch.slardar.session.Configuration.LOCATION;

/**
 * Description :
 * Created by xingtianyu on 17-1-26
 * 下午8:47
 * description:
 */

public class XmlParser extends ParserAdapter<Configuration> {

    private static final String NAME = "name";
    private static final String VALUE = "value";

    private String xmlPath;
    private SAXReader saxReader = new SAXReader();
    private Document document = null;
    private Element root = null;

    private Map<String,Object> content = new HashMap<>();

    public XmlParser(String path){
        this.xmlPath = path;
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            document = saxReader.read(is);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XmlParser(InputStream is){
        try {
            this.xmlPath = MapperScanner.CLASSPATH;
            document = saxReader.read(is);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XmlParser(Reader reader){
        try {
            this.xmlPath = MapperScanner.CLASSPATH;
            document = saxReader.read(reader);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Iterator<Element> elementIterator = root.elementIterator();
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            List<Attribute> attributes = element.attributes();
            Attribute location = element.attribute(LOCATION);

            Map<String,Object> attribute = new HashMap<>();
            for (Attribute attr:attributes){
                attribute.put(attr.getName(),attr.getData());
            }
            content.put(element.getName(),attribute);
            //datasource支持在节点内配置db信息
            if (DATASOURCE.equals(element.getName())
                    &&location==null){
                Iterator<Element> elemIt = element.elementIterator();
                Properties prop = new Properties();
                while (elemIt.hasNext()){
                    Element elem = elemIt.next();
                    String name = String.valueOf(elem.attribute(NAME).getData());
                    String value = String.valueOf(elem.attribute(VALUE).getData());
                    prop.setProperty(name,value);
                }
                attribute.put(LIST,prop);
            }
//            if (DATASOURCE.equals(element.getName())
//                    &&location==null){
//
//            }else{
//                Map<String,Object> attribute = new HashMap<>();
//                for (Attribute attr:attributes){
//                    attribute.put(attr.getName(),attr.getData());
//                }
//                content.put(element.getName(),attribute);
//            }
        }
    }

    public Map<String,Object> getContent(){
        return content;
    }

    @Override
    public Configuration parse() {
        return new Configuration(xmlPath,content);
    }
}
