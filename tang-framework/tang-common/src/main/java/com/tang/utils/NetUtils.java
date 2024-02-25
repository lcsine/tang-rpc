package com.tang.utils;

import com.tang.exceptions.NetworkException;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Enumeration;



@Slf4j
public class NetUtils {
    public static String getIp() {
        try {
            //获取所有的网卡信息
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                //过滤非回环接口和虚拟接口
                if (iface.isLoopback() || iface.isVirtual() || !iface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> address = iface.getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress addr = address.nextElement();
                    if (addr instanceof Inet6Address || addr.isLoopbackAddress()) {
                        continue;
                    }
                    String ipAddress = addr.getHostAddress();
                    System.out.println("局域网IP地址" + ipAddress);
                    return ipAddress;
                }

            }
            throw new NetworkException();
        } catch (SocketException e) {
            log.error("获取局域网ip时发生异常",e);
            throw new NetworkException(e);
        }

    }

    public static void main(String[] args) {

        String ip = NetUtils.getIp();
        System.out.println("ip = "+ip);
    }
}
