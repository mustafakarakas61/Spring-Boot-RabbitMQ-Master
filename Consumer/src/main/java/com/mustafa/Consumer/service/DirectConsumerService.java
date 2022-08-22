package com.mustafa.Consumer.service;

import com.mustafa.Consumer.config.ConnectionConfig;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class DirectConsumerService {

    //-----------------------------------------------------------Variables
    /*
    todo : Burada Geliştirme Yapılacak
    todo : uygulama kuyruktan mesajı çeksin
     */
    private Connection connection;
    private Channel channel;
    private Consumer consumer, consumer1, consumer2, consumer3;
    private String messageFromQueue;
    public static String QUEUE_NAME = "direct-queue";
    public static String QUEUE_NAME_1 = "direct-queue-1";
    public static String QUEUE_NAME_2 = "direct-queue-2";
    public static String QUEUE_NAME_3 = "direct-queue-3";

    //-----------------------------------------------------------Methods for consumer[1,2,3]
    public void receive() throws IOException, TimeoutException {

        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();

            //Consumer reading from queue 1
            consumer1 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Message Received Queue 1 '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME_1, true, consumer1);

            //Consumer reading from queue 2
            consumer2 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Message Received Queue 2 '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME_2, true, consumer2);

            //Consumer reading from queue 3
            consumer3 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Message Received Queue 3 '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME_3, true, consumer3);
            channel.close();
            connection.close();

        }

    }


    public String startConsumerForThisQueue(String queueName) throws IOException, TimeoutException {

        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();
            consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Messaj " + queueName + " kuyruğundan " + "ulaştı, Mesaj içeriği : '" + message + "'");
                    messageFromQueue = "Messaj " + queueName + " kuyruğundan çekilen son mesaj" + "ulaştı, Son Mesaj içeriği : '" + message + "'";
                }
            };
            channel.basicConsume(queueName, true, consumer);
            channel.close();
            connection.close();
        }
        return messageFromQueue ;
    }

    //"direct-queue" kuyruğu mevcut olmalıdır. Sadece o kuyruğa atılan mesajları çekecek.
    public void receiveConsumer() throws IOException, TimeoutException{
        connection = ConnectionConfig.getConnection();

        if(connection != null){
            channel = connection.createChannel();
            //Consumer reading from queue
            consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
                    String message = new String(body, "UTF-8");
                    System.out.println("Message Received Queue '"+ message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
            channel.close();
            connection.close();

        }

    }


}
