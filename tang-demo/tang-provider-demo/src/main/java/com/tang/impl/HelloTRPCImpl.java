package com.tang.impl;

import com.tang.HelloTRPC;

public class HelloTRPCImpl implements HelloTRPC {
    @Override
    public String sayHi(String msg) {
        return "hi consumer"+msg;
    }
}
