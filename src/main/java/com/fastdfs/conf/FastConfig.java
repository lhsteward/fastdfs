package com.fastdfs.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * FastDFS 属性
 */
@Component
public class FastConfig {

    @Value("${fdfs.host}")
    private String resHost;

    @Value("${fdfs.port}")
    private String storagePort;

    public String getResHost() {
        return resHost;
    }

    public void setResHost(String resHost) {
        this.resHost = resHost;
    }

    public String getStoragePort() {
        return storagePort;
    }

    public void setStoragePort(String storagePort) {
        this.storagePort = storagePort;
    }
}
