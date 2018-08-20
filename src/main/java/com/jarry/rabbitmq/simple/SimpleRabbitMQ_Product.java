package com.jarry.rabbitmq.simple;

import com.jarry.rabbitmq.QueueEnum;
import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jarry on 2018/8/15.
 * 生产者
 */
public class SimpleRabbitMQ_Product {

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明队列
        channel.queueDeclare(QueueEnum.SIMPLE_TEST.name(),false, false, false, null);
        //消息内容
        String msg = "妹子你好！";

        //发送消息到队列中
        channel.basicPublish("", QueueEnum.SIMPLE_TEST.name(), null, msg.getBytes("UTF-8"));

        System.out.println("发送消息内容：" + msg);
        channel.close();
        connection.close();
    }
}
