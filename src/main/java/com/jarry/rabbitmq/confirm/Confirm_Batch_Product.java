package com.jarry.rabbitmq.confirm;

import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jarry on 2018/8/15.
 * 生产者-confirm
 * 编程模式：批量
 */
public class Confirm_Batch_Product {

    private static final String QUEUE_NAME = "confirm_plain";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明队列
        channel.queueDeclare(QUEUE_NAME,false, false, false, null);
        //消息内容
        String msg = "妹子你好！";
        //设置为确认模式
        channel.confirmSelect();
        //发送消息到队列中-循环发送
        for(int i=0;i <10;i++){
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
            System.out.println("发送消息内容：" + msg);
        }


        //对消息进行确认
        if(channel.waitForConfirms()){
            System.out.println("发送消息到队列中成功");
        }else{
            System.out.println("发送消息到队列中失败");
        }

        channel.close();
        connection.close();
    }
}
