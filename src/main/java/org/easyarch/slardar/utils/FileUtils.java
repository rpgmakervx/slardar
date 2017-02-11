package org.easyarch.slardar.utils;/**
 * Description : 
 * Created by YangZH on 16-10-27
 *  下午7:42
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-10-27
 * 下午7:42
 */

public class FileUtils {

    public static String getBottomDir(String filePath){
        return getBottomDir(new File(filePath)).getPath() + File.separator;
    }

    /**
     * 根据当前文件获得其最底层目录
     * 先把当前文件名去掉，得到的就是最底层目录了
     * @param file
     * @return
     */
    public static File getBottomDir(File file){
        String filePath = file.getPath();
        int lastSeparatorIndex = filePath.lastIndexOf(File.separator) + 1;
        String configFileName = filePath.substring(
                lastSeparatorIndex,filePath.length());
        int fileNameIndex = filePath.lastIndexOf(configFileName);
        String bottomDir = filePath.substring(0,fileNameIndex);
        return new File(bottomDir);
    }

    /**
     * 递归遍历
     * @param file
     * @param includeDir
     * @return
     */
    private static List<File> listRecursive(File file,boolean includeDir){
        if (file == null){
            return null;
        }
        List<File> paths = new ArrayList<File>();
        File[] files = file.listFiles();
        for (File f:files){
            if (f.isDirectory()){
                if (includeDir)
                    paths.add(f);
                paths.addAll(listRecursive(f,includeDir));
            }else{
                paths.add(f);
            }
        }
        return paths;
    }

    public static List<File> listFileRecursive(File file){
        return listRecursive(file,false);
    }
    public static List<File> listFileRecursive(String path){
        return listFileRecursive(new File(path));
    }

    public static List<File> listDirectoryRecursive(File file){
        return listRecursive(file,true);
    }
    public static List<File> listDirectoryRecursive(String path){
        return listDirectoryRecursive(new File(path));
    }


}
