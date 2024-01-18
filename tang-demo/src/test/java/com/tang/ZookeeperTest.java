package com.tang;

import com.tang.netty.MyWatcher;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest {
    ZooKeeper zooKeeper;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    @Before
    public void createZK(){
        //定义连接参数
//        String connectString = "127.0.0.1:2181";
        String connectString = "192.168.200.130:2181,192.168.200.131:2181,192.168.200.132:2181";
        int sessionTimeOut = 10000;
        try {
//            zooKeeper = new ZooKeeper(connectString,sessionTimeOut,new MyWatcher());
            zooKeeper = new ZooKeeper(connectString,sessionTimeOut,event->{
                if (event.getState()== Watcher.Event.KeeperState.SyncConnected){
                    System.out.println("客户端已经连接成功");
                    countDownLatch.countDown();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testCreatePNode(){
        try {
            countDownLatch.await();
            String result = zooKeeper.create("/ydlclasss", "tyb".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("result = " + result);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if (zooKeeper!=null){
                    zooKeeper.close();
                }

            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }

    @Test
    public void testDeletePNode(){
        try {
            zooKeeper.delete("/ydlclasss", -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if (zooKeeper!=null){
                    zooKeeper.close();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testExistsPNode(){
        try {
            Stat exists = zooKeeper.exists("/ydlclass", null);
            int version = exists.getVersion();
            System.out.println("version = " + version);
            int aversion = exists.getAversion();
            System.out.println("aversion = " + aversion);
            int cversion = exists.getCversion();
            System.out.println("cversion = " + cversion);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if (zooKeeper!=null){
                    zooKeeper.close();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testWatcher(){
        try {
            Stat exists = zooKeeper.exists("/ydlclass", true);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                if (zooKeeper!=null){
                    zooKeeper.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
