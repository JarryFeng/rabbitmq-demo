package com.jarry.rabbitmq.confirm;

import com.jarry.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * Created by jarry on 2018/8/15.
 * 生产者-confirm
 * 编程模式：异步
 */
public class Confirm_Aysc_Product {

    private static final String QUEUE_NAME = "confirm_aysc";

    final static SortedSet sortedSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

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

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {

                if(multiple){
                    System.out.println("......11111");
                    //清除集合
                    sortedSet.headSet(deliveryTag + 1).clear();
                }else {
                    System.out.println(".......22222");
                    sortedSet.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("处理确认失败的数据");
                if(multiple){
                    //清除集合
                    //sortedSet.headSet(deliveryTag + 1).clear();
                    System.out.println("123");
                }else {
                    //sortedSet.remove(deliveryTag);
                    System.out.println("321");
                }
            }
        });



        //发送消息到队列中-循环发送
        while(true){
            long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
            System.out.println("发送消息内容：" + msg);
            sortedSet.add(nextPublishSeqNo);
        }



        //channel.close();
        //connection.close();
    }
}
