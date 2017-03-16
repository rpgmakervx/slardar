package org.easyarch.slardar.session;

import org.easyarch.slardar.cache.SqlMapCache;
import org.easyarch.slardar.cache.TemplateCache;
import org.easyarch.slardar.cache.factory.SqlMapCacheFactory;
import org.easyarch.slardar.cache.factory.TemplateCacheFactory;
import org.easyarch.slardar.cache.mode.CacheMode;
import org.easyarch.slardar.entity.CacheEntity;
import org.easyarch.slardar.entity.SqlEntity;
import org.easyarch.slardar.jdbc.cfg.ConnConfig;
import org.easyarch.slardar.jdbc.cfg.PoolConfig;
import org.easyarch.slardar.jdbc.pool.DBCPool;
import org.easyarch.slardar.jdbc.pool.DBCPoolFactory;
import org.easyarch.slardar.mapping.MapperScanner;
import org.easyarch.slardar.parser.JSParser;
import org.easyarch.slardar.utils.*;
import org.easyarch.slardar.utils.file.FileUtils;

import javax.sql.DataSource;
import java.io.*;
import java.util.*;

/**
 * Description :
 * Created by xingtianyu on 17-1-23
 * 上午12:41
 * description:
 */

public class Configuration {

    public static final String JADE = "jade";
    public static final String INTERFACE = "interface";
    public static final String PACKAGE = "package";
    public static final String MAPPER = "mapper";
    public static final String LOCATION = "location";
    public static final String DATASOURCECLASS = "class";
    public static final String DATASOURCE = "datasource";
    public static final String CACHE = "sqlMapCache";
    public static final String ENABLE = "enable";
    public static final String MODE = "mode";
    public static final String SIZE = "size";
    public static final String LEVEL = "level";

    //存放datasource内部配置db信息用
    public static final String LIST = "list";

    public static final String CLASSPATH = "classpath:";
    public static final String JS = ".js";
    public static final String JSON = ".json";

    private volatile Map<String, Map<String, String>> mappedSqls = new HashMap<>();

    private volatile Map<String,Object> configMap;

    //配置文件所在路径
    private String configFilePath;

    private DataSource dataSource;

    private List<Reader> sqlMapperReaders;
    private List<File> sqlMapperFiles;
    private SqlMapCache sqlMapCache;
    private TemplateCache templateCache;
    public Configuration(String configPath,Map<String,Object> content) {
        this.configFilePath = configPath;
        this.configMap = content;
        this.sqlMapCache = SqlMapCacheFactory.getInstance()
                .createCache(getCacheEntity());
        this.templateCache = TemplateCacheFactory.getInstance()
                .createCache(getCacheEntity());
        sqlMapperReaders = new ArrayList<>();
        sqlMapperFiles = new ArrayList<>();
        initMapper();
        initDataSource();
        initMapperTemplate();
    }

    /**
     * 从配置文件路径读取mapper配置文件信息（注意只能是精确到目录不能是文件）
     * mapper未配置时，不加载该配置
     * 支持配置路径如下：
     * 1.classpath:mapper/                              (走classpath)
     * 2./opt/web/webapp/admin/WEB-INF/classes/mapper/  (走绝对路径)
     * 3.mapper/                                        (走当前配置文件的相对路径,
     *                                                  如果配置文件在resources/config,
     *                                                  则mapper目录就是resources/config/mapper/)
     */
    private void initMapper(){
        try {
            String basePath = getMapperLocation();
            if (StringUtils.isEmpty(basePath)){
                return;
            }
            File baseDir = new File(basePath);
            List<File> mapperFiles = null;
            if (basePath.startsWith(CLASSPATH)){
                //去掉classpath:关键字
                basePath = basePath.replace(CLASSPATH,"");
                //连接classpath目录和用户输入目录
                basePath = ResourcesUtil.getResources(basePath);
                //遍历相对路径下的所有目录中的文件
                mapperFiles = FileUtils.listFileRecursive(basePath);
                for (File file : mapperFiles){
                    String filename = file.getPath();
                    if (!filename.endsWith(JS)&&!filename.endsWith(JSON)){
                        continue;
                    }
                    System.out.println("config filename:"+filename);
                    //获得具体文件在classpath下的相对路径
                    String relativePath = filename.substring(MapperScanner.CLASSPATH.length(),filename.length());
                    Reader reader = new InputStreamReader(
                            getClass().getClassLoader().getResourceAsStream(relativePath));
                    if (filename.endsWith(JS)){
                        sqlMapperReaders.add(reader);
                        continue;
                    }else if (filename.endsWith(JSON)){
                        sqlMapperFiles.add(file);
                    }
                }
                return ;
            }else if(baseDir.isAbsolute()){
                mapperFiles = FileUtils.listFileRecursive(baseDir);
            }else{
                //prefixPath 就是最底层目录路径
                String prefixPath = FileUtils.getBottomDir(configFilePath);
                basePath = prefixPath + basePath;
                mapperFiles = FileUtils.listFileRecursive(basePath);
            }
            for (File file:mapperFiles){
                if (!file.getPath().endsWith(JS)&&!file.getPath().endsWith(JSON)){
                    continue;
                }
                FileReader reader = new FileReader(file);
                if (file.getName().endsWith(JS)){
                    sqlMapperReaders.add(reader);
                    continue;
                }else if (file.getName().endsWith(JSON)){
                    sqlMapperFiles.add(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 规则同mapper
     */
    private void initDataSource(){
        Map<String,Object> mapper = (Map<String, Object>) configMap.get(DATASOURCE);
        Properties prop = (Properties) mapper.get(LIST);
        String classname = String.valueOf(mapper.get(DATASOURCECLASS));
        try {
            if (prop == null){
                String baseFilePath = String.valueOf(mapper.get(LOCATION));
                File baseFile = new File(baseFilePath);
                if (baseFilePath.startsWith(CLASSPATH)){
                    //去掉classpath:关键字
                    baseFilePath = baseFilePath.replace(CLASSPATH,"");
                    //连接classpath目录和用户输入目录
                    baseFilePath = ResourcesUtil.getResources(baseFilePath);
                }else if(!baseFile.isAbsolute()){
                    //prefixPath 就是最底层目录路径
                    String prefixPath = FileUtils.getBottomDir(configFilePath);
                    baseFilePath = prefixPath + baseFilePath;
                }
                prop.load(new FileInputStream(baseFilePath));
            }


            //引用外部的数据库连接池
            PoolConfig config = PoolConfig.config(prop);
            if (StringUtils.isNotEmpty(classname)&&!classname.equals(DBCPool.class.getName())){
                Object dataSource = ReflectUtils.newInstance(classname);
                ReflectUtils.setter(dataSource, ConnConfig.DRIVERNAME,prop.get(ConnConfig.DRIVERNAME));
                ReflectUtils.setter(dataSource, ConnConfig.URL,prop.get(ConnConfig.URL));
                ReflectUtils.setter(dataSource, ConnConfig.PASSWORD,prop.get(ConnConfig.PASSWORD));
                ReflectUtils.setter(dataSource, ConnConfig.USERNAME,prop.get(ConnConfig.USERNAME));
                ReflectUtils.setter(dataSource, PoolConfig.INITIAL_SIZE, config.getMaxIdle());
                ReflectUtils.setter(dataSource, PoolConfig.MINIDLE, config.getMinIdle());
                ReflectUtils.setter(dataSource, PoolConfig.MAXACTIVE, config.getMaxActive());
                ReflectUtils.setter(dataSource, PoolConfig.MAXWAIT, config.getMaxWait());
                this.dataSource = (DataSource) dataSource;
                return ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dataSource = DBCPoolFactory.newConfigedDBCPool(prop);
    }

    private void initMapperTemplate(){
        for (File file:sqlMapperFiles){
            try {
                String content = FileUtils.cat(file);
                Map<String,Object> jsonMap = JsonUtils.json2Map(content);
                for (String namespace:jsonMap.keySet()){
                    templateCache.set(namespace,content);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getPacakge(){
        Map<String,Object> pkg = (Map<String, Object>) configMap.get(INTERFACE);
        if (pkg == null){
            return null;
        }
        return pkg.get(PACKAGE)+"";
    }

    public String getMapperLocation(){
        Map<String,Object> mapper = (Map<String, Object>) configMap.get(MAPPER);
        if (mapper == null){
            return null;
        }
        return String.valueOf(mapper.get(LOCATION));
    }

    /**
     * 支持其他第三方连接池（配置文件信息和本框架不同）
     * @return
     */
    public DataSource getDataSource(){
        return this.dataSource;
    }

    /**
     * 之前add过后不会不命中
     * @param namespace
     * @param id
     * @param params
     * @return
     */
    public SqlEntity getMappedSql(String namespace, String id,Collection params) {
        return sqlMapCache.getSqlEntity(namespace,id,params.toArray());
    }

    /**
     * 从所有js文件中挑选符合namespace的那个文件，得到sql实体，并保存
     * @param sqlEntity
     */
    public void parseMappedSql(SqlEntity sqlEntity){
        if (sqlMapCache.isHit(sqlEntity.getPrefix()
                ,sqlEntity.getSuffix(),sqlEntity.getParams().values())){
            return ;
        }
//        String content = templateCache.get(sqlEntity.getPrefix());
//        TemplateParser parser = new TemplateParser(content,this);
//        parser.parse(sqlEntity);
        for (Reader reader:sqlMapperReaders){
            JSParser parser = new JSParser(reader,this);
            parser.parse(sqlEntity);
        }
    }

    private boolean enableCache(){
        Map<String,Object> cache = (Map<String, Object>) configMap.get(CACHE);
        if (cache==null){
            return false;
        }
        Object value = cache.get(ENABLE);
        if (value !=null){
            if (StringUtils.isEmpty(String.valueOf(value))){
                return false;
            }
        }
        return Boolean.valueOf(String.valueOf(value));
    }

    /**
     * 缓存大小为0表示不使用缓存
     * @return
     */
    private int getCacheSize(){
        Map<String,Object> cache = (Map<String, Object>) configMap.get(CACHE);
        if (cache==null){
            return 0;
        }
        Object value = cache.get(SIZE);
        if (value !=null){
            if (StringUtils.isEmpty(String.valueOf(value))){
                return 0;
            }
        }
        int size = Integer.parseInt(String.valueOf(value));
        return size<=0?0:size;
    }

    private CacheMode getCacheMode(){
        Map<String,Object> cache = (Map<String, Object>) configMap.get(CACHE);
        if (cache==null){
            return null;
        }
        Object value = cache.get(MODE);
        if (value !=null){
            if (StringUtils.isEmpty(String.valueOf(value))){
                return null;
            }
        }
        return CacheMode.valueOf(String.valueOf(value).toUpperCase());
    }

    public CacheEntity getCacheEntity(){
        CacheEntity entity = new CacheEntity(
                getCacheSize(),getCacheMode(),enableCache());
        return entity;
    }
}
