package com.jarry.rabbitmq.work;

import com.jarry.rabbitmq.QueueEnum;
import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 * Created by jarry on 2018/8/15.
 * 消费者
 */
public class WorkRabbitMQ_Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();

        //声明要关注的队列
        channel.queueDeclare(QueueEnum.SIMPLE_TEST.name(), true, false, false, null);

        //预先获取的数据量
        channel.basicQos(3);

        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");

                try {
                    Thread.sleep(10000);
                    doWork(message);
                } catch (Exception e) {

                } finally {
                    System.out.println(" Done well ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        //监听队列
        channel.basicConsume(QueueEnum.SIMPLE_TEST.name(), false, consumer);

    }

    private static void doWork(String message) {

        for (char ch : message.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
