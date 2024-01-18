package com.tang;

public interface HelloRPC {
    /**
     * 通用接口，server和client都需要依赖
     * @param msg 具体发送的消息
     * @return {@link String}
     */
    String sayHi(String msg);
}
