package org.easyarch.slardar.cache.mode;

/**
 * Description :
 * Created by xingtianyu on 17-2-15
 * 下午7:54
 * description:
 */

public enum CacheMode {
    LRU("lru"),
    FIFO("fifo"),
    TIMEOUT("timeout");
    private String mode;

    CacheMode(String mode) {
        this.mode = mode;
    }

    public String getMode(){
        return mode;
    }
}
