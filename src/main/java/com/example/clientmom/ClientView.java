package com.example.clientmom;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Message;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientView extends ResizableView {
    @FXML
    public VBox alarmsBox;

    ResourceBundle bundle = ResourceBundle.getBundle("com.example.clientmom.i18n", new Locale("pt_br", "pt_BR"));

    @FXML
    public void initialize() {

        try {
            Context context = Context.getInstance();
            context.addListening(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    @Override
    public void manipulateMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String textMessage = ((TextMessage) message).getText();
                Date date = new Date(message.getJMSTimestamp());

                Label labelProducerId = new Label("Sensor ID: " + ((ActiveMQTextMessage) message).getProducerId().toString());
                //Setting font to the label
                labelProducerId.getStyleClass().add("messagePlayerLabel");
                alarmsBox.getChildren().add(labelProducerId);

                Label labelTipicName = new Label("Tópico: " + ((ActiveMQTextMessage) message).getDestination().getPhysicalName());
                //Setting font to the label
                labelTipicName.getStyleClass().add("messagePlayerLabel");
                alarmsBox.getChildren().add(labelTipicName);
                //Creating a Label
                SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                Label labelDatetime = new Label("Data: " + dt1.format(date));
                //Setting font to the label
                labelDatetime.getStyleClass().add("messagePlayerLabel");
                alarmsBox.getChildren().add(labelDatetime);

                //Creating a Label
                Label label1 = new Label("Mensagem:" + textMessage);
                label1.getStyleClass().add("messagePlayerLabel");
                //Setting the position
                alarmsBox.getChildren().add(label1);
                alarmsBox.getChildren().add(new Label("\n"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}