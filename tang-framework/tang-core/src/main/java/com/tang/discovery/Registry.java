package com.tang.discovery;

import com.tang.ServiceConfig;

import javax.sound.sampled.Port;

/**
 * 注册中心应该具有什么样的能力
 * @author tang
 * @date 2024/02/24
 */
public interface Registry {
    /**
     * 注册服务的配置内容
     * @param serviceConfig 服务的配置内容
     */
    void register(ServiceConfig<?> serviceConfig, int port);
}
