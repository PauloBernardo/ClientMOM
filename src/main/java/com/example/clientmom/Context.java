package com.example.clientmom;

import java.io.IOException;
import java.util.ArrayList;
import javax.jms.*;

import javafx.application.Platform;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Context implements MessageListener {
    private static Context instance = null;
    private final ArrayList<ResizableView> listeners;
    private ArrayList<String> topics;
    private final ArrayList<MessageConsumer> consumers;
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private final Session session;
    private final Connection connection;

    private Context() throws JMSException {
        super();
        listeners = new ArrayList<>();
        consumers = new ArrayList<>();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
        for(MessageConsumer consumer: consumers) {
            consumer.close();
        }
        session.close();
        connection.close();
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) throws JMSException {
        for(MessageConsumer consumer: consumers) {
            consumer.close();
        }
        consumers.clear();
        for (String topic : topics) {
            try {
                Destination dest = session.createTopic(topic);
                /*
                 * Criando Consumidor
                 */
                MessageConsumer subscriber = session.createConsumer(dest);

                /*
                 * Setando Listener
                 */
                subscriber.setMessageListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.topics = topics;
    }

    @Override
    public void onMessage(Message message) {
        for(ResizableView resizableView: listeners) {
            Platform.runLater(() -> {
                try {
                    resizableView.manipulateMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
