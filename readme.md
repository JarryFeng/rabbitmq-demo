##RabbitMQ入门学习

### <font color=red>1.简单队列</font>
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/simple-queue.jpg)  
缺点：耦合性高，生产者一一对应消费者，对列名变更时，生产者消费者都随之改变,当消息一直未被确认时，会一直占用内存。

### <font color=red>2.工作队列</font>
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/work-queue.jpg)

优点：可以很方便的扩展消费者，进行消息的消费, 当某个消费者挂掉后，会将该消息发送给其他消费者
缺点：耦合性高，对列名变更时，生产者消费者都随之改变,当消息一直未被确认时，会一直占用内存。

默认来说，RabbitMQ会按顺序把队列中的消息发送给每个消费者（consumer）。平均每个消费者都会收到同等数量得消息。这种发送消息得方式叫做——轮询（round-robin）

##
## 消息交换机的策略有：
direct exchange(直连型交换机)   
Fanout exchange(扇型交换机)  
Topic exchange(主题交换机)   
Headers exchange(头交换机)  
##


### <font color=red>3.发布订阅-交换机类型类 fanout</font>
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/publish-subscribe.jpg)

    1.将消息路由给绑定到它身上的所有队列。
    2.不同于直连交换机，路由键在此类型上不启任务作用。如果N个队列绑定到某个扇型交换机上，当有消息发送给此扇型交换机时，交换机会将消息的发送给这所有的N个队列

### <font color=red>4.路由-交换机类型类 direct</font>
(1)一个队列绑定多个路由
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/direct.jpg)
(2)多个队列绑定一个路由
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/direct_2.jpg)
(3)多个队列绑定多个路由
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/direct_3.jpg)

    【直连型交换机(direct exchange)】是根据消息携带的路由键（routing key）将消息投递给对应队列的，步骤如下：
    1.将一个队列绑定到某个交换机上，同时赋予该绑定一个路由键（routing key）
    2.当一个携带着路由值为R的消息被发送给直连交换机时，交换机会把它路由给绑定值同样为R的队列。

### <font color=red>4.主题-交换机类型类 topic</font>
![avatar](https://github.com/JarryFeng/rabbitmq-demo/blob/master/src/main/resources/static/picture/topic.jpg)

    1.队列通过路由键绑定到交换机上，然后，交换机根据消息里的路由值，将消息路由给一个或多个绑定队列。
    2.扇型交换机和主题交换机异同：   
        2.1 对于扇型交换机路由键是没有意义的，只要有消息，它都发送到它绑定的所有队列上   
        2.2 对于主题交换机，路由规则由路由键决定，只有满足路由键的规则，消息才可以路由到对应的队列上
<font color=green>主题与路由最大的区别就是主题的routeKey包含*,#</font>

### <font color=red>5.头-交换机类型类 headers</font>   
    1. 类似主题交换机，但是头交换机使用多个消息属性来代替路由键建立路由规则。通过判断消息头的值能否与指定的绑定相匹配来确立路由规则。 
    2. 此交换机有个重要参数：”x-match”
        2.1 当”x-match”为“any”时，消息头的任意一个值被匹配就可以满足条件
        2.2 当”x-match”设置为“all”的时候，就需要消息头的所有值都匹配成功
