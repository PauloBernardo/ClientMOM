package com.example.clientmom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ClientController extends ResizableView {
    @FXML
    public FlowPane parameterCheckBox;
    ArrayList<CheckBox> checkBoxes;

    ResourceBundle bundle = ResourceBundle.getBundle("com.example.clientmom.i18n", new Locale("pt_br", "pt_BR"));

    @FXML
    public void initialize() throws JMSException, InterruptedException {
        this.onReloadTopics(null);
    }

    @FXML
    protected void onReloadTopics(MouseEvent event)  throws JMSException {
        checkBoxes = new ArrayList<>();
        parameterCheckBox.getChildren().clear();
        Context context = Context.getInstance();
        Set<ActiveMQTopic> topics = context.getConnection().getDestinationSource().getTopics();
        for(ActiveMQTopic topic: topics) {
            CheckBox checkBox = new CheckBox(topic.getPhysicalName());
            checkBox.setPadding(new Insets(10));
            parameterCheckBox.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
        }
        if(topics.isEmpty()) {
            Label label = new Label("Não há tópicos disponíveis no momento!");
            label.setPadding(new Insets(10));
            parameterCheckBox.getChildren().add(label);
        }
    }

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws JMSException {
        ArrayList<String> topics = new ArrayList<>();
        for (CheckBox checkBox: checkBoxes) {
            if (checkBox.isSelected()) {
                topics.add(checkBox.getText());
            }
        }
        try {
            Context.getInstance().setTopics(topics);
            this.switchBetweenScreen(((Node) event.getSource()).getScene(), "client-view.fxml");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("hello.setTopicTitleError"));
            alert.getDialogPane().setContent( new Label(bundle.getString("hello.setTopicTextError")));
            alert.show();
        }
    }
}