package com.jarry.rabbitmq.work;

import com.jarry.rabbitmq.QueueEnum;
import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 * Created by jarry on 2018/8/15.
 * 生产者
 */
public class WorkRabbitMQ_Product {

    private static String[] msgs = {"张三.", "李四..", "王五...", "刘六....", "郭七....."};

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明队列
        /*
        queue 队列名,
        durable 是否持久化该队列,
        exclusive,
        autoDelete 是否自动删除,
        Map<String, Object> arguments
         */
        channel.queueDeclare(QueueEnum.SIMPLE_TEST_01.name(), true, false, false, null);
        //消息内容
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            //发送消息到队列中
            /*
            exchange 交换机,
            routingKey 队列名,
            props ：{
                MessageProperties.PERSISTENT_TEXT_PLAIN : 持久化数据
            }
            body 发送的消息
             */
            channel.basicPublish("", QueueEnum.SIMPLE_TEST_01.name(), null, msg.getBytes("UTF-8"));

            System.out.println("发送消息内容：" + msg);
        }

        channel.close();
        connection.close();
    }
}
