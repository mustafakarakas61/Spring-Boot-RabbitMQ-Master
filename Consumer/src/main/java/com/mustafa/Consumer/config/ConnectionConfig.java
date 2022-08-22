package com.mustafa.Consumer.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class ConnectionConfig {
    private static Connection connection;
    private static ConnectionFactory factory = new ConnectionFactory();

    public static Connection getConnection() throws IOException, TimeoutException {
        //factory.setUsername("");
        //factory.setPassword("");
        //factory.setVirtualHost("");
        factory.setHost("localhost");
        //factory.setPort(15672);
        connection = (Connection) factory.newConnection();
        return connection;
    }

}
