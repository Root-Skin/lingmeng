package com.lingmeng.common.config.kafka

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

/**
 * @author skin
 * @createTime 2021年03月21日 
 * @Description
 */
object GlobalConfigUtil {

  private val config: Config = ConfigFactory.load()

  /*------------------
   * Kafka配置
   *------------------*/

  val bootstrapServers = config.getString("bootstrap.servers")
  val zookeeperConnect = config.getString("zookeeper.connect")
  val inputTopic = config.getString("input.topic")
  val groupId = config.getString("group.id")
  val enableAutoCommit = config.getString("enable.auto.commit")
  val autoCommitIntervalMs = config.getString("auto.commit.interval.ms")
  val autoOffsetReset = config.getString("auto.offset.reset")


  // 测试配置文件读取类
  def main(args: Array[String]): Unit = {
    println(bootstrapServers)
    println(zookeeperConnect)
    println(inputTopic)
    println(groupId)
    println(enableAutoCommit)
    println(autoCommitIntervalMs)
    println(autoOffsetReset)

    // 初始化Flink运行环境
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    // 设置流处理的时间为EventTime
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    // 设置并行度
//    env.setParallelism(1)
//
//    // 从本地集合中创建DataStream
//    val testDataStream: DataStream[String] = env.fromCollection(
//      List("hadoop", "hive", "spark")
//    )
//    // 打印测试结果
//    testDataStream.print()
//
//    env.execute("App")
  }

}
