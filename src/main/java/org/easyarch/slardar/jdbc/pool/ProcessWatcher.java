package org.easyarch.slardar.jdbc.pool;/**
 * Description : 
 * Created by YangZH on 16-11-7
 *  下午8:23
 */

import org.easyarch.slardar.jdbc.exec.ConnectionUtils;

/**
 * Description :
 * Created by code4j on 16-11-7
 * 下午8:23
 */

public class ProcessWatcher{
    static Runtime rt = Runtime.getRuntime();
    static{
        System.out.println("watcher is ready.");
        rt.addShutdownHook(new Thread() {
            public void run() {
//                System.out.println("programme exit,ready to close all dbcpool connections ... ");
//                System.out.println("connections count is :"+ RealCPool.getConnections().size());
                kill();
            }
        });
    }

    private static void kill() {
        for (ConnectionWrapper wrapper : RealCPool.getConnections()) {
            ConnectionUtils.close(wrapper.connection());
        }
    }
}
