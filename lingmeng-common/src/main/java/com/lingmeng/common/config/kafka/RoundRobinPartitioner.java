package com.lingmeng.common.config.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author skin
 * @createTime 2021年03月20日
 * @Description
 */
public class RoundRobinPartitioner implements Partitioner {
    // 计数器，每次生产一条消息+1
    private AtomicInteger counter = new AtomicInteger();
    private String topic = "";

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        // 获取分区数量
        Integer partitions = cluster.partitionCountForTopic(s);

        int curPartition = counter.incrementAndGet() % partitions;

        if(counter.get() > 65535) {
            counter.set(0);
        }

        return curPartition;
    }

    @Override
    public void close() {

    }


    @Override
    public void configure(Map<String, ?> map) {

    }
}
