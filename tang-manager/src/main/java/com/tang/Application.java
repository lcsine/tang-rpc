package com.tang;

import com.tang.utils.zookeeper.ZookeeperNode;
import com.tang.utils.zookeeper.ZookeeperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.List;

/**
 * 注册中心的管理页面
 *
 * @author tang
 * @date 2024/02/23
 */
@Slf4j
public class Application {
    public static void main(String[] args) {


            ZooKeeper zooKeeper = ZookeeperUtils.createZookeeper();

            //定义节点和数据
            String basePath = "/trpc-metadata";
            String providePath = basePath + "/providers";
            String consumerPath = basePath + "/consumers";


            ZookeeperNode baseNode = new ZookeeperNode(basePath, null);
            ZookeeperNode provideNode = new ZookeeperNode(providePath, null);
            ZookeeperNode consumerNode = new ZookeeperNode( consumerPath, null);
            List.of(baseNode, provideNode, consumerNode).forEach(node -> {
                    ZookeeperUtils.createNode(zooKeeper,node,null,CreateMode.PERSISTENT);
            });
            ZookeeperUtils.close(zooKeeper);

    }
}


