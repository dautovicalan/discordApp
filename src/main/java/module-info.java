module com.alan.discordapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.xml;
    requires javafx.media;
    requires java.desktop;
    requires java.rmi;
    requires java.naming;
    requires opencv;

    opens com.alan.discordapp to javafx.fxml;
    exports com.alan.rmi;
    exports com.alan.discordapp;
}