package com.example.clientmom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.activemq.ActiveMQConnection;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientApplication.class.getResource("hello-view.fxml"));
        loader.setResources(ResourceBundle.getBundle("com.example.clientmom.i18n", new Locale("pt_br", "pt_BR")));
        Scene scene = new Scene(loader.load());
        Image image = new Image("file:client.png");
        stage.getIcons().add(image);
        stage.setTitle("Client MOM!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        try {
            Context.getInstance().closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        Context.url = args.length > 0 ? args[0] : ActiveMQConnection.DEFAULT_BROKER_URL;
        launch();
    }
}