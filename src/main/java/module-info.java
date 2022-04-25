module com.example.sensormom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javax.jms;
    requires activemq.client;
    requires java.naming;

    opens com.example.clientmom to javafx.fxml;
    exports com.example.clientmom;
}