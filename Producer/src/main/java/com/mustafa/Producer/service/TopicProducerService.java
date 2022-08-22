package com.mustafa.Producer.service;

import com.mustafa.Producer.config.ConnectionConfig;
import com.mustafa.Producer.model.enums.ExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class TopicProducerService {
    //---------------------------------------------------------------------Variables
    private Connection connection;
    private Channel channel;

    public static String EXCHANGE_NAME_TOPIC = "topic_exchange";

    private final static String MESSAGE_1 = "First Topic Message Example";
    private final static String MESSAGE_2 = "Second Topic Message Example";
    private final static String MESSAGE_3 = "Third Topic Message Example";

    public static String QUEUE_NAME_1 = "topic-queue-1";
    public static String QUEUE_NAME_2 = "topic-queue-2";
    public static String QUEUE_NAME_3 = "topic-queue-3";

    public static String ROUTING_PATTERN_1 = "asia.china.*";
    public static String ROUTING_PATTERN_2 = "asia.china.#";
    public static String ROUTING_PATTERN_3 = "asia.*.*";

    public static String ROUTING_KEY_1 = "asia.china.nanjing";
    public static String ROUTING_KEY_2 = "asia.china";
    public static String ROUTING_KEY_3 = "asia.china.beijing";

    public void createExchangeAndQueue() throws IOException, TimeoutException {
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME_TOPIC, ExchangeType.TOPIC.getExchangeName(), true);

            //First Queue
            channel.queueDeclare(QUEUE_NAME_1, true, false, false, null);
            channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME_TOPIC, ROUTING_PATTERN_1);

            //Second Queue
            channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);
            channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME_TOPIC, ROUTING_PATTERN_2);

            //Third Queue
            channel.queueDeclare(QUEUE_NAME_3, true, false, false, null);
            channel.queueBind(QUEUE_NAME_3, EXCHANGE_NAME_TOPIC, ROUTING_PATTERN_3);

            channel.close();
            connection.close();
        }
    }

    /**
     * “First Topic Message Example” with routing key as “asia.china.nanjing“.
     * “Second Topic Message Example” with routing key as “asia.china“.
     * “Third Topic Message Example” with routing key as “asia.china.beijing“.
     */
    public void publish() throws IOException, TimeoutException {
        connection = ConnectionConfig.getConnection();
        if (connection != null) {
            channel = connection.createChannel();

            //First message sent by using ROUTING_KEY_1
            channel.basicPublish(EXCHANGE_NAME_TOPIC, ROUTING_KEY_1, null, MESSAGE_1.getBytes());
            System.out.println("Messagee Sent '" + MESSAGE_1 + "'");

            //Second message sent by using ROUTING_KEY_2
            channel.basicPublish(EXCHANGE_NAME_TOPIC, ROUTING_KEY_2, null, MESSAGE_2.getBytes());
            System.out.println("Message Sent '" + MESSAGE_2 + "'");

            //Third message sent by using ROUTING_KEY_3
            channel.basicPublish(EXCHANGE_NAME_TOPIC, ROUTING_KEY_3, null, MESSAGE_3.getBytes());
            System.out.println("Message Sent '" + MESSAGE_3 + "'");

            channel.close();
            connection.close();
        }
    }

    public void createQueue(String queueName, String existExchangeName, String routingPatternName) throws IOException, TimeoutException {
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();

            channel.queueDeclare(queueName, true, false, false, null);//create queue - Kuyruk üret
            channel.queueBind(queueName, existExchangeName, routingPatternName);      //bind queue to exchange with RK-RB - Varolan-üretilen kuyruğu varolan exchange tipine RK-RB ile bağla-bind et

            channel.close();
            connection.close();
        }

    }

    /**
     * Bu sadece normal bir kuyruk üretmek için, Yukarıda ise kuyruk üretip buna exchange tipini ve routingPattern veriyoruz ve bunları bind ediyoruz
     * public void createQueue(String queueName) throws IOException, TimeoutException {
     * connection = DirectProducerService.getConnection();
     * <p>
     * <p>
     * if (connection != null) {
     * channel = connection.createChannel();
     * channel.queueDeclare(queueName, true, false, false, null);
     * channel.close();
     * connection.close();
     * }
     * }
     */

    public void sendMessage(String newMessage, String newRoutingKeyName, String existExchangeName) throws IOException, TimeoutException {
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();
            channel.basicPublish(existExchangeName, newRoutingKeyName, null, newMessage.getBytes());

            channel.close();
            connection.close();
        }
    }
}
