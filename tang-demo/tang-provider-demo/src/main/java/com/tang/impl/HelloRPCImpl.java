package com.tang.impl;

import com.tang.HelloRPC;

public class HelloRPCImpl implements HelloRPC {
    @Override
    public String sayHi(String msg) {
        return "hi consumer"+msg;
    }
}
