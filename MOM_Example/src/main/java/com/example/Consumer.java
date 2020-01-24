package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

    public static void main(String[] args)
            throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException,
            InterruptedException {

            /**DA:
            * Kanaele werden entsprechend definiert
            * Die Qus werden durch docker und rabbitmq lokal gehostet -> RabbitMQ web Monitor
            */

            String exchangeNameOne = "TemperaturSensorOne";
            String exchangeNameTwo = "TemperaturSensorTwo";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://guest:guest@localhost");
            factory.setConnectionTimeout(30000);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeNameOne,"fanout");
            channel.exchangeDeclare(exchangeNameTwo,"fanout");
            String queueName = channel.queueDeclare().getQueue();
            String queueName1 = channel.queueDeclare().getQueue();
            channel.queueBind(queueName,exchangeNameOne,"");
            channel.queueBind(queueName1,exchangeNameTwo,"");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            channel.basicConsume(queueName1, true, deliverCallback, consumerTag -> { });
        }

}

