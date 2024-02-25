package com.tang;

import com.tang.discovery.RegistryConfig;

public class Application {
    public static void main(String[] args) {
        //想尽一切办法获取代理对象,使用 ReferenceConfig进行封装
        // reference一定用生成代理的模版方法，get()
        ReferenceConfig<HelloTRPC> reference = new ReferenceConfig<>();
        reference.setInterface(HelloTRPC.class);

        //代理做了是吗
        //1、连接注册中心
        //2、拉取服务列表
        //3、选择一个服务并建立连接
        //4、发送请求，携带一些信息（接口名，参数列表，方法的名字），获得结果
        TrpcBootstrap.getInstance()
                .application("first-trpc-consumer")
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(reference);
        //获取一个代理对象

        HelloTRPC helloRPC = reference.get();
        helloRPC.sayHi("你好");
    }
}




