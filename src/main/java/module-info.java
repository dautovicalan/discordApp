module com.alan.discordapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.xml;


    opens com.alan.discordapp to javafx.fxml;
    exports com.alan.discordapp;
}