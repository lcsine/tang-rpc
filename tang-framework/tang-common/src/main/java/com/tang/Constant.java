package com.tang;

public class Constant {
    // zookeeper的默认连接地址
    public static final String DEFAULT_ZK_CONNECT="127.0.0.1:2181";
    // zookeeper的默认超时时间
    public static final int TIME_OUT = 10000;
    //服务提供方 在注册中心的路径
    public static final String BASE_PROVIDERS_PATH = "/trpc-metadata/providers";
    //服务调用方 在注册中心的路径
    public static final String BASE_CONSUMERS_PATH = "/trpc-metadata/consumers";
}
