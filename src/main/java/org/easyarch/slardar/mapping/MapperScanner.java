package org.easyarch.slardar.mapping;

import org.easyarch.slardar.annotation.sql.Mapper;
import org.easyarch.slardar.cache.InterfaceCache;
import org.easyarch.slardar.cache.factory.InterfaceCacheFactory;
import org.easyarch.slardar.session.Configuration;
import org.easyarch.slardar.utils.file.FileUtils;
import org.easyarch.slardar.utils.ResourcesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import static org.easyarch.slardar.parser.Token.ANY;
import static org.easyarch.slardar.parser.Token.POINT;

/**
 * Description :
 * Created by xingtianyu on 17-1-21
 * 下午11:31
 * description:
 */

public class MapperScanner {

    public static final String CLASSPATH = ResourcesUtil.getClassPath();

    public static final String CLASS = ".class";

    private InterfaceCache interfaceCache;

    private static boolean scaned = false;

    public MapperScanner(Configuration configuration){
        interfaceCache = InterfaceCacheFactory.getInstance()
                .createCache(configuration.getCacheEntity());
    }

    /**
     * 扫描整个工程
     * @throws Exception
     */
    private void scan() throws Exception {
        List<File> classFiles = FileUtils.listFileRecursive(CLASSPATH);
        int endPoint = CLASSPATH.lastIndexOf(File.separator);

        for (File cf:classFiles){
            String filename = cf.getPath();
            if (!filename.endsWith(CLASS)){
                continue;
            }
            String packagePath = filename.substring(endPoint + 1,filename.length());
            String interfaceName = packagePath.substring(0, filename.lastIndexOf("."))
                    .replace(File.separator,POINT);
            URL[] urls = new URL[]{new URL("file:"+filename)};
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<?> clazz = classLoader.loadClass(interfaceName);
            addClass(clazz);
        }
    }

    /**
     * 路径扫描，目前不支持通配符*扫描
     * 路径配置文件不存在时则不扫描
     * 系统启动后只加载一次
     * @param packagePath  包名，空字符或单个*符号代表当前工程下所有包
     * @throws Exception
     */
    public void scan(String packagePath) throws Exception {
        if (scaned){
            return;
        }
        scaned = true;
        if (packagePath == null){
            return;
        }

        if (ANY.equals(packagePath)){
            scan();
            return;
        }
        if(packagePath.contains(ANY)){
            packagePath = packagePath.replaceAll("\\"+POINT+ANY,"");
        }
        String copyPath = packagePath.replace(POINT,File.separator);
        String classpath = CLASSPATH + copyPath + File.separator;
        File file = new File(classpath);
        if (!file.exists()){
            throw new FileNotFoundException("package "+packagePath+" not found");
        }
        if (file.isFile()){
            throw new IllegalArgumentException("please offer a package name");
        }
        //包名第一层作为前缀
        String prefix = packagePath.split("\\"+POINT)[0];
        List<File> classFiles = FileUtils.listFileRecursive(classpath);
        for (File cf : classFiles){
            if (!cf.getPath().endsWith(CLASS)){
                continue;
            }
            String filename = cf.getPath();
            String copyFileName = filename.substring(0, filename.lastIndexOf(".")).replace(File.separator,".");
            String interfaceName = copyFileName.substring(copyFileName.indexOf(prefix),copyFileName.length());
            URL[] urls = new URL[]{new URL("file:"+filename)};
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<?> clazz = classLoader.loadClass(interfaceName);
            addClass(clazz);
        }
    }

    private void addClass(Class<?> clazz){
        if (clazz.isInterface()){
            Mapper mapper = clazz.getAnnotation(Mapper.class);
            String namespace = clazz.getName();
            if (mapper != null && !mapper.namespace().isEmpty()){
                namespace = mapper.namespace();
            }
            ClassItem classItem = new ClassItem(namespace,clazz,clazz.getDeclaredMethods());
            interfaceCache.set(clazz,classItem);
        }
    }

    public static void main(String[] args) throws Exception {
//        File file = new File("/home/code4j/58daojia/com/daojia/dao/entity/classs.java");
//        String filename = file.getPath();
//        filename = filename.substring(0, filename.lastIndexOf(".")).replace(File.separator,".");
//        System.out.println(filename);
//        System.out.println(filename.substring(filename.indexOf("com"),filename.length()));
//        MapperScanner scanner = new MapperScanner();
//        scanner.scan("org.easyarch.myutils.db");
//        for (ClassItem item : ClassItemPool.getInterfaces()){
//            System.out.println(item.getItemName());
//        }
//        System.out.println(CLASSPATH);
//        String interfaceFile = "/home/code4j/IDEAWorkspace/myutils/myutils-db/target/classes/org/easyarch/myutils/db/wrapper/Wrapper.class";
//        int endPoint = CLASSPATH.lastIndexOf(File.separator);
//        String packagePath = interfaceFile.substring(endPoint + 1,interfaceFile.length());
//        System.out.println("packagePath:"+packagePath);
//        int prefixEndPoint = packagePath.indexOf(File.separator);
//        System.out.println("prefixEndPoint:"+prefixEndPoint);
//        String prefix = packagePath.substring(0,prefixEndPoint);
//        System.out.println(prefix);

        System.out.println(MapperScanner.class.getClassLoader().getResource("mapper/sqlmapper.js").getPath());
        String basePath = "/home/code4j/IDEAWorkspace/myutils/myutils-db/target/classes/";
        String mapper = "mapper/user/";
        String fullPath = "/home/code4j/IDEAWorkspace/myutils/myutils-db/target/classes/mapper/user/usermapper.js";
        System.out.println(fullPath.substring(basePath.length(),fullPath.length()));;
    }
}
