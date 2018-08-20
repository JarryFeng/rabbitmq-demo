package com.jarry.rabbitmq.topic;

import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式routing
 * Created by jarry on 2018/8/15.
 * 生产者
 */
public class TopicRabbitMQ_Product {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明交换机 交换机名称：logs ，类型：fanout 展开,direct 直接, topic 主题, headers 标题
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //消息内容
        String msg = "我这消息随便发";
        channel.basicPublish(EXCHANGE_NAME, "username.zhangsan", null, msg.getBytes("UTF-8"));
        System.out.println("发送消息内容：" + msg);



        channel.close();
        connection.close();
    }
}
