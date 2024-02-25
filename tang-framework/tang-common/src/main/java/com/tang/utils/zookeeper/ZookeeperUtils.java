package com.tang.utils.zookeeper;

import com.tang.exceptions.ZookeeperException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static com.tang.Constant.DEFAULT_ZK_CONNECT;
import static com.tang.Constant.TIME_OUT;
@Slf4j
public class ZookeeperUtils {

    /**
     * 使用默认配置创建zookeeper实例
     * @param connectString 连接地址
     * @param timeOut 超时时间
     * @return zookeeper
     */
    public static ZooKeeper createZookeeper(String connectString,int timeOut){
//        ZooKeeper zooKeeper;
        CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            final ZooKeeper zooKeeper = new ZooKeeper(connectString, timeOut, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("客户端已经连接成功");
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();

          return  zooKeeper;
        } catch (IOException | InterruptedException e) {
            log.error("创建zookeeper实例时发生异常",e);
            throw new ZookeeperException();
        }
    }
    public static ZooKeeper createZookeeper(){

        //定义连接参数
        String connectString = DEFAULT_ZK_CONNECT;

        int timeOut = TIME_OUT;

        return createZookeeper(connectString, timeOut);
    }

    /**
     * 创建一个节点的工具方法
     * @param zooKeeper zookeeper实力
     * @param node 节点
     * @param watcher watcher是咧
     * @param createMode 节点类型
     * @return ture: 成功创建 false：已经存在 异常：抛出
     */
    public static boolean createNode(ZooKeeper zooKeeper,ZookeeperNode node,Watcher watcher,CreateMode createMode){

        try {
            if (zooKeeper.exists(node.getNodePath(),watcher) == null){
                String result = zooKeeper.create(node.getNodePath(), node.getData(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
                log.info("根节点{}成功创建",result);
                return true;
            }else {
                if (log.isDebugEnabled()){
                    log.info("根节点{}已经存在无需创建",node.getNodePath());
                }
            }
            return false;
        } catch (KeeperException | InterruptedException e) {
            log.error("创建基础目录是发生异常",e);
            throw new ZookeeperException();
        }

    }

    /**
     * 判断节点是否存在
     * @param zooKeeper zk实例
     * @param node 节点
     * @param watcher watcher
     * @return ture 存在 |false 不存在
     */
    public static boolean exists(ZooKeeper zooKeeper,String node ,Watcher watcher){
        try {
            return zooKeeper.exists(node,watcher)==null;
        } catch (KeeperException | InterruptedException e) {
            log.error("判断节点[{}]是否存在时异常",node,e);
            throw new ZookeeperException(e);
        }
    }
    public static void close(ZooKeeper zooKeeper){
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            log.error("关闭zookeeper时发生问题",e);
            throw new ZookeeperException();
        }
    }

}
