package com.jarry.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jarry on 2018/8/15.
 */
public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置ip地址
        connectionFactory.setHost("101.132.40.203");
        //设置端口
        connectionFactory.setPort(5672);
        //设置虚拟主机-类似数据库
        connectionFactory.setVirtualHost("/vhost_jarry");
        //设置用户名
        connectionFactory.setUsername("jarry");
        //设置密码
        connectionFactory.setPassword("jarry");

        Connection connection = connectionFactory.newConnection();
        System.out.println("创建连接成功");
        return connection;
    }
}
