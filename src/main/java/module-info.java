module com.alan.discordapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.xml;
    requires javafx.media;
    requires java.desktop;

    opens com.alan.discordapp to javafx.fxml;
    exports com.alan.discordapp;
}