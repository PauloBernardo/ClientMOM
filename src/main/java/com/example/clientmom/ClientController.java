package com.example.clientmom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javax.jms.JMSException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientController extends ResizableView {

    ResourceBundle bundle = ResourceBundle.getBundle("com.example.clientmom.i18n", new Locale("pt_br", "pt_BR"));

    @FXML
    public void initialize() {}

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws JMSException {
        try {
            Context.getInstance();
            this.switchBetweenScreen(((Node) event.getSource()).getScene(), "client-view.fxml");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("hello.setTopicTitleError"));
            alert.getDialogPane().setContent( new Label(bundle.getString("hello.setTopicTextError")));
            alert.show();
        }
    }
}