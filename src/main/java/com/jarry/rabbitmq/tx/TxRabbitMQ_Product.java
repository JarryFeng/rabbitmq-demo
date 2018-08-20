package com.jarry.rabbitmq.tx;

import com.jarry.rabbitmq.QueueEnum;
import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jarry on 2018/8/15.
 * 生产者-事务
 * 是否将数据插入到rabbitmq中
 * 此种模式很耗时，降低了rabbitmq的消息吞吐量
 */
public class TxRabbitMQ_Product {

    private static final String QUEUE_NAME = "tx_queue_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明队列
        channel.queueDeclare(QUEUE_NAME,false, false, false, null);
        //消息内容
        String msg = "妹子你好！";

        try{
            //开始事务
            channel.txSelect();
            //发送消息到队列中
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
            //int i= 1/0;
            channel.txCommit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            channel.txRollback();
            System.out.println("回滚事务");
        }


        System.out.println("发送消息内容：" + msg);
        channel.close();
        connection.close();
    }
}
