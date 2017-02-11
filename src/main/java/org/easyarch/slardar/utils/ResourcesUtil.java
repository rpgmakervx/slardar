package org.easyarch.slardar.utils;

import java.io.InputStream;

/**
 * Description :
 * Created by xingtianyu on 17-2-10
 * 上午12:52
 * description:
 */

public class ResourcesUtil {

    public static String getClassPath() {
        return ResourcesUtil.class.getClassLoader().getResource("").getPath();
    }

    public static String getResources(String resource) {
        return ResourcesUtil.class.getClassLoader().getResource(resource).getPath();
    }

    public static InputStream getResourceAsStream(String resource) {
        return ResourcesUtil.class.getResourceAsStream(resource);
    }
}
