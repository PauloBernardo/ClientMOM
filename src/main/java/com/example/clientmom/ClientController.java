package com.example.clientmom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientController extends ResizableView {
    @FXML
    public CheckBox checkBox1;
    @FXML
    public CheckBox checkBox2;
    @FXML
    public CheckBox checkBox3;

    ResourceBundle bundle = ResourceBundle.getBundle("com.example.clientmom.i18n", new Locale("pt_br", "pt_BR"));

    @FXML
    public void initialize() {}

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException, JMSException {
        ArrayList<String> topics = new ArrayList<>();
        if(checkBox1.isSelected()) {
            topics.add(bundle.getString("parameter.one"));
        }
        if(checkBox2.isSelected()) {
            topics.add(bundle.getString("parameter.two"));
        }
        if(checkBox3.isSelected()) {
            topics.add(bundle.getString("parameter.three"));
        }
        Context.getInstance().setTopics(topics);
        this.switchBetweenScreen(((Node) event.getSource()).getScene(), "client-view.fxml");
    }
}