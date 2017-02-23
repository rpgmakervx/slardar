package org.easyarch.slardar.utils.file;

import java.io.File;

/**
 * Description :
 * Created by xingtianyu on 16-12-26
 * 下午4:20
 * description:
 */

public interface FileFilter {

    public boolean accept(File file);
}
