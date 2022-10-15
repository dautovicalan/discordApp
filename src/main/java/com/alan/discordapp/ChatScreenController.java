package com.alan.discordapp;

import com.alan.models.Server;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private Button sendButton;
    @FXML
    private VBox vBoxMessage;
    @FXML
    private ScrollPane scrollPaneMain;

    private Server server;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new Server(new ServerSocket(1234));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating server");
        }

        vBoxMessage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                scrollPaneMain.setVvalue((Double) newValue);
            }
        });

        server.receiveMessageFromClient(vBoxMessage);

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = textField.getText();
                if (!messageToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239, 242, 255); " +
                            "-fx-background-color: rgb(15,125,242); " +
                            "-fx-background-radius: 20px;");

                    textFlow.setPadding(new Insets(5,5,5,10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vBoxMessage.getChildren().add(hBox);

                    server.sendMessageToClient(messageToSend);
                    textField.clear();
                }
            }
        });
    }

    public static void addMessageToComponent(String messageFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5,5,5,10));
        text.setFill(Color.color(0.934, 0.945, 0.996));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });

    }

}
