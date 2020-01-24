package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Publisher extends Thread {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException,
            IOException, TimeoutException, InterruptedException {

            /**DA:
             * Methoden werden als prozess mittels multithreading aufgerufen 
             * Diese MEthoden rufen den Producer Prozess auf -> weiteren Publisher, Klassen, Methoden automatisch auf
             */

            new Thread(() -> {
                try {
                    sensorOne();
                } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException | IOException
                        | InterruptedException | TimeoutException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    sensorTwo();
                } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException | IOException
                        | InterruptedException | TimeoutException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();
            
        }
        public static void sensorOne() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, TimeoutException {
        
            /**DA:
             * Es wird eine exchange queue definiert 
             * Die Qus werden durch docker und rabbitmq lokal gehostet -> RabbitMQ web Monitor 
             */

            String exchangeNameOne = "TemperaturSensorOne";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://guest:guest@localhost");
            factory.setConnectionTimeout(30000);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            /**DA:
             * try Anweisung 
             * Strukur greift auf vordefinierte KLasse TemperaturSensor zurueck  
             * string Methode formuliert published message
             * Fuer jeden publish wird auch der 2. Publisher aufgerufen
             * Mit der TemperaturSensor Klasse werden entsprechende Daten generiert
             */

            try {

            TemperaturSensor temp = new TemperaturSensor(7, "Muehlacker");
            String message = temp.ToString();
            channel.exchangeDeclare(exchangeNameOne, "fanout");

            int i = 0;

            while (i == 0){
                java.lang.Thread.sleep(10000);
                temp.getTemperatureLocationA();
                message = temp.ToString();
                channel.basicPublish(exchangeNameOne, "", null, message.getBytes());
                System.out.println("Sent '" + message + "'");
                }
            } finally {
                System.out.println("publicStaticVoid.SensorOne().Try.Finally" );
            }
    }

        public static void sensorTwo() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException, TimeoutException {
            
            /**DA:
             * Es wird eine 2. exchange queue definiert 
             * Die Qu befindet wird durch docker und rabbitmq lokal gehostet -> RabbitMQ web Monitor 
             */

            String exchangeNameTwo = "TemperaturSensorTwo";

            /**DA:
             * Aus vorherigen Publisher:
             * try Anweisung 
             * Strukur greift auf vordefinierte KLasse TemperaturSensor zurueck  
             * string Methode formuliert published message
             * Fuer jeden publish wird auch der 2. Publisher aufgerufen
             * Mit der TemperaturSensor Klasse werden entsprechende Daten generiert
             */

            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://guest:guest@localhost");
            factory.setConnectionTimeout(30000);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            try {
            
            TemperaturSensor temp = new TemperaturSensor(5, "Betten");
            String message = temp.ToString();
            channel.exchangeDeclare(exchangeNameTwo, "fanout");

            int i = 0;

            while (i == 0){
                java.lang.Thread.sleep(10000);
                temp.getTemperatureLocationB();
                message = temp.ToString();
                channel.basicPublish(exchangeNameTwo, "", null, message.getBytes());
                System.out.println("Sent '" + message + "'");
                }
            } finally {
                System.out.println("publicStaticVoid.SensorTwo().Try.Finally" );
            }
        
        
    }

}

