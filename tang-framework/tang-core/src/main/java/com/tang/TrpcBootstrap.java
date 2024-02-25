package com.tang;


import com.tang.discovery.Registry;
import com.tang.discovery.RegistryConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TrpcBootstrap {

    // trpcBootstrap是个单例，我们希望每个应用程序只有一个实例
    private static final TrpcBootstrap trpcBootstrap = new TrpcBootstrap();
    private String appName = "default";
    private RegistryConfig registryConfig;
    private ProtocolConfig protocolConfig;
    private int port = 8088;
    //维护一个zookeeper实例
    //todo待处理
    private Registry registry;
    //维护已经发布且暴露的服务列表 key———》interface的全限定名 value -》 serviceconfig
    private static final Map<String,ServiceConfig<?>> SERVERS_LIST = new ConcurrentHashMap<>(16);



    private TrpcBootstrap() {
        // 构造启动引导程序，时需要做一些什么初始化的事
    }

    public static TrpcBootstrap getInstance() {
        return trpcBootstrap;
    }

    /**
     * 用来定义当前应用的名字
     *
     * @param appName 应用的名字
     * @return this当前实例
     */
    public TrpcBootstrap application(String appName) {
        this.appName = appName;
        return this;
    }

    /**
     * 用来配置一个注册中心
     *
     * @param registryConfig 注册中心
     * @return this当前实例
     */
    public TrpcBootstrap registry(RegistryConfig registryConfig) {
        //这里维护以一个zookeeper实例，但是，如果这样写就会将zookeeper和当前工程耦合
        //我们其实是更希望以后可以扩展更多种不同的实现
        //尝试使用 registryConfig  获取一个注册中心，有点工程设计模式的意思了
        this.registry =registryConfig.getRegistry();
        return this;
    }

    /**
     * 配置当前暴露的服务使用的协议
     *
     * @param protocolConfig 协议的封装
     * @return this当前实例
     */
    public TrpcBootstrap protocol(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
        if (log.isDebugEnabled()) {
            log.debug("当前工程使用了：{}协议进行序列化", protocolConfig.toString());
        }
        return this;
    }


    /**
     * ---------------------------服务提供方的相关api---------------------------------
     */

    /**
     * 发布服务，将接口-》实现，注册到服务中心
     *
     * @param service 封装的需要发布的服务
     * @return this当前实例
     */
    public TrpcBootstrap publish(ServiceConfig<?> service) {
        //我们抽象类注册中心的概念，使用了注册中心一个实现完成注册
        registry.register(service,port);

        //1、当服务调用方，通过接口、方法名，具体的方法参数列表发起调用，提供怎么知道使用哪一个实现
        // （1）new 一个 （2） spring beanFactory.getBean（class）（3）自己维护映射关系
        SERVERS_LIST.put(service.getInterface().getName(),service);
        return this;
    }

    /**
     * 批量发布
     *
     * @param services 封装的需要发布的服务集合
     * @return this当前实例
     */
    public TrpcBootstrap publish(List<ServiceConfig<?>> services) {
        for (ServiceConfig<?> service : services) {
            this.publish(service);
        }
        return this;
    }

    /**
     * 启动netty服务
     */
    public void start() {
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * ---------------------------服务调用方的相关api---------------------------------
     */
    public TrpcBootstrap reference(ReferenceConfig<?> reference) {

        // 在这个方法里我们是否可以拿到相关的配置项-注册中心
        // 配置reference，将来调用get方法时，方便生成代理对象
        return this;

    }
}
