package com.mustafa.Producer.service;

import com.mustafa.Producer.config.ConnectionConfig;
import com.mustafa.Producer.model.enums.ExchangeType;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class HeadersProducerService {
    //---------------------------------------------------------------------Variables
    private Connection connection;
    private Channel channel;

    public static String EXCHANGE_NAME_HEADER = "header-exchange";


    private final static String MESSAGE_1 = "First Header Message Example";
    private final static String MESSAGE_2 = "Second Header Message Example";
    private final static String MESSAGE_3 = "Third Header Message Example";

    public static String QUEUE_NAME_1 = "header-queue-1";
    public static String QUEUE_NAME_2 = "header-queue-2";
    public static String QUEUE_NAME_3 = "header-queue-3";

    private static String ROUTING_KEY = "";

    //---------------------------------------------------------------------Methods
    public void createExchangeAndQueue() throws IOException, TimeoutException {
        Map<String, Object> map = null;
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();

            //Create Exchange
            channel.exchangeDeclare(EXCHANGE_NAME_HEADER, ExchangeType.HEADER.getExchangeName(), true);

            //Create First Queue with Bind
            map = new HashMap<String, Object>();
            map.put("x-match", "any");
            map.put("First", "A");
            map.put("Fourth", "D");
            //Create Queue
            channel.queueDeclare(QUEUE_NAME_1, true, false, false, null);
            channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME_HEADER, ROUTING_KEY, map);

            //Create Second Queue with Bind
            map = new HashMap<String, Object>();
            map.put("x-match", "any");
            map.put("Fourth", "D");
            map.put("Third", "C");
            //Create Queue
            channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);
            channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME_HEADER, ROUTING_KEY, map);

            //Create Third Queue with Bind
            map = new HashMap<String, Object>();
            map.put("x-match", "all");
            map.put("First", "A");
            map.put("Third", "C");
            //Create Queue
            channel.queueDeclare(QUEUE_NAME_3, true, false, false, null);
            channel.queueBind(QUEUE_NAME_3, EXCHANGE_NAME_HEADER, ROUTING_KEY, map);

            channel.close();
            connection.close();
        }
    }

    /**
     * “First Header Message Example” with header values as “First, A“, “Fourth, D“.
     * “Second Header Message Example” with header values as “Third, C“.
     * “Third Header Message Example” with header values as “First, A“, “Third, C“.
     */

    public void publish() throws IOException, TimeoutException {
        Map<String, Object> map = null;
        BasicProperties properties = null;
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();

            //First Message
            properties = new BasicProperties();
            map = new HashMap<String, Object>();
            map.put("First", "A");
            map.put("Fourth", "D");
            properties = properties.builder().headers(map).build();
            channel.basicPublish(EXCHANGE_NAME_HEADER, ROUTING_KEY, properties, MESSAGE_1.getBytes());
            System.out.println("Message Sent '" + MESSAGE_1 + "'");

            // Second message
            properties = new BasicProperties();
            map = new HashMap<String, Object>();
            map.put("Third", "C");
            properties = properties.builder().headers(map).build();
            channel.basicPublish(EXCHANGE_NAME_HEADER, ROUTING_KEY, properties, MESSAGE_2.getBytes());
            System.out.println(" Message Sent '" + MESSAGE_2 + "'");

            // Third message
            map = new HashMap<String, Object>();
            properties = new BasicProperties();
            map.put("First", "A");
            map.put("Third", "C");
            properties = properties.builder().headers(map).build();
            channel.basicPublish(EXCHANGE_NAME_HEADER, ROUTING_KEY, properties, MESSAGE_3.getBytes());
            System.out.println(" Message Sent '" + MESSAGE_3 + "'");

            channel.close();
            connection.close();
        }
    }

    public void createQueue(String queueName, String existExchangeName, String propS1, String propS2,String propO1, String propO2, boolean allOrAny) throws IOException, TimeoutException {
        Map<String, Object> map = null;//  Key, Value
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();

            //Edit Map
            map = new HashMap<>();
            if (allOrAny)//if allOrAny is true   ALL
                map.put("x-match", "all");
            if (!allOrAny)//if allOrAny is false ANY
                map.put("x-match", "any");
            map.put(propS1, propO1);
            map.put(propS2, propO2);

            //Create Queue
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, existExchangeName, "", map);

            channel.close();
            connection.close();
        }
    }

    public void sendMessage(String message, String existExchangeName, String propS1, String propS2, String propO1, String propO2) throws IOException, TimeoutException {
        Map<String, Object> map = null;
        BasicProperties properties = null;
        connection = ConnectionConfig.getConnection();

        if (connection != null) {
            channel = connection.createChannel();

            properties = new BasicProperties();
            map = new HashMap<>();
            map.put(propS1, propO1);
            if (propS2 != null && propO2 != null)
                map.put(propS2, propO2);


            properties = properties.builder().headers(map).build();
            channel.basicPublish(existExchangeName, "", properties, message.getBytes());

            channel.close();
            connection.close();

        }

    }
}
