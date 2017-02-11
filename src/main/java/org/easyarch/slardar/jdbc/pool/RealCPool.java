package org.easyarch.slardar.jdbc.pool;

import java.util.HashSet;
import java.util.Set;

/**
 * Description :
 * Created by xingtianyu on 16-12-31
 * 下午11:27
 * description:
 */

class RealCPool {
    private static final Set<ConnectionWrapper> realconns = new HashSet<>();

    public static Set<ConnectionWrapper> getConnections(){
        return realconns;
    }
}
