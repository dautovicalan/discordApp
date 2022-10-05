module com.alan.discordapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.alan.discordapp to javafx.fxml;
    exports com.alan.discordapp;
}