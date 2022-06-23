package com.example.clientmom;

import java.util.ArrayList;
import java.util.Set;
import javax.jms.*;

import javafx.application.Platform;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class Context implements MessageListener {
    private static Context instance = null;
    private final ArrayList<ResizableView> listeners;
    private ArrayList<String> topics;
    private final ArrayList<MessageConsumer> consumers;
    static String url;
    private final Session session;
    private final ActiveMQConnection connection;

    private Context() throws JMSException {
        super();
        listeners = new ArrayList<>();
        consumers = new ArrayList<>();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = session.createTopic("chatMediator");
        /*
         * Criando Consumidor
         */
        MessageConsumer subscriber = session.createConsumer(dest);

        /*
         * Setando Listener
         */
        subscriber.setMessageListener(this);
    }

    public static Context getInstance() throws JMSException {
        if (instance == null) {
            try {
                instance = new Context();
            } catch (Exception e) {
                e.printStackTrace();
                instance = null;
                throw e;
            }
        }
        return instance;
    }

    public void addListening(ResizableView contextListening) {
        listeners.add(contextListening);
    }

    public void removeListening(ResizableView contextListening) {
        listeners.remove(contextListening);
    }

    public void closeConnection() throws JMSException {
        for (MessageConsumer consumer : consumers) {
            consumer.close();
        }
        session.close();
        connection.close();
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    @Override
    public void onMessage(Message message) {
        for (ResizableView resizableView : listeners) {
            Platform.runLater(() -> {
                try {
                    resizableView.manipulateMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public Session getSession() {
        return session;
    }

    public ActiveMQConnection getConnection() {
        return connection;
    }
}
