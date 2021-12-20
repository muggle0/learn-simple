package com.muggle.zk.config;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description
 * Date 2021/12/20
 * Created by muggle
 */
@EnableScheduling
@Component
public class ScheduleTest {

    @Autowired
    private ZooKeeper zkClient;

    @Scheduled(cron = "*/10 * * * * ?")
    public void test(){
        zkClient.create("/test","message".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
}
