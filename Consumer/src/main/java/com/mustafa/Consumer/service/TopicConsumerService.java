package com.mustafa.Consumer.service;

import com.mustafa.Consumer.config.ConnectionConfig;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class TopicConsumerService {

    private Connection connection;
    private Channel channel;

    private Consumer consumer1, consumer2, consumer3;

    public static String QUEUE_NAME_1 = "topic-queue-1";
    public static String QUEUE_NAME_2 = "topic-queue-2";
    public static String QUEUE_NAME_3 = "topic-queue-3";

    public void receive() throws IOException, TimeoutException {

        connection = ConnectionConfig.getConnection();

        if(connection != null) {
            channel = connection.createChannel();

            //Consumer reading from queue 1
            consumer1 = new DefaultConsumer(channel){
              @Override
              public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
                  String message = new String(body, "UTF-8");
                  System.out.println("Message Received Queue 1 '"+message+"'");
              }
            };
            channel.basicConsume(QUEUE_NAME_1, true, consumer1);

            //Consumer reading from queue 2
            consumer2 = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws  IOException{
                String message = new String(body, "UTF-8");
                    System.out.println("Message Received Queue 2 '"+message+"'");
                }
            };
            channel.basicConsume(QUEUE_NAME_2, true, consumer2);

            //Consumer reading from queue 3
            consumer3 = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws  IOException{
                    String message = new String(body, "UTF-8");
                    System.out.println("Message Received Queue 3 '"+message+"'");
                }
            };
            channel.basicConsume(QUEUE_NAME_3, true, consumer3);

            channel.close();
            connection.close();
        }

    }


}
