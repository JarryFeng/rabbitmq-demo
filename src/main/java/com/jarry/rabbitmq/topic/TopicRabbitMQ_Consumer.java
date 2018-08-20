package com.jarry.rabbitmq.topic;

import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅Publish-Subscribe
 * Created by jarry on 2018/8/15.
 * 消费者
 */
public class TopicRabbitMQ_Consumer {

    private static final String EXCHANGE_NAME = "topic_logs";

    /**
     *
     * @param args 路由的键值
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //获取随机的队列名
        String queueName = channel.queueDeclare().getQueue();
        //将队列绑定要交换机上
        String routeKey = "user#";
        System.out.println("我要获取交换机中routeKey为" + routeKey + "的数据");
        channel.queueBind(queueName, EXCHANGE_NAME, routeKey);


        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("receive: " + message);
            }
        };

        //监听队列
        channel.basicConsume(queueName, true, consumer);

    }
}
