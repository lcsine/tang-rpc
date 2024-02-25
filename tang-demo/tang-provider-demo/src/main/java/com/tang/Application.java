package com.tang;

import com.tang.discovery.RegistryConfig;
import com.tang.impl.HelloTRPCImpl;

public class Application {
    public static void main(String[] args) {
        ServiceConfig<HelloTRPC> service = new ServiceConfig<>();
        service.setInterface(HelloTRPC.class);
        service.setRef(new HelloTRPCImpl());
        // 2、定义注册中心

        // 3、通过启动引导程序，启动服务提供方
        //   （1） 配置 -- 应用的名称 -- 注册中心 -- 序列化协议 -- 压缩方式
        //   （2） 发布服务
        TrpcBootstrap.getInstance()
                .application("first-yrpc-provider")
                // 配置注册中心
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .protocol(new ProtocolConfig("jdk"))
                // 发布服务
                .publish(service)
                // 启动服务
                .start();
    }
    }
