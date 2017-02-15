package org.easyarch.slardar.entity;

import org.easyarch.slardar.cache.mode.CacheMode;

/**
 * Description :
 * Created by xingtianyu on 17-2-16
 * 上午12:47
 * description:
 */

public class CacheEntity {

    private int size;

    private CacheMode mode;

    private boolean enable;

    public CacheEntity(int size, CacheMode mode, boolean enable) {
        this.size = size;
        this.mode = mode;
        this.enable = enable;
        if (size == 0){
            this.enable = false;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public CacheMode getMode() {
        return mode;
    }

    public void setMode(CacheMode mode) {
        this.mode = mode;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
