package com.alan.discordapp;

import com.alan.businessLayer.ConversationManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.Message;
import com.alan.models.Server;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.xml.bind.JAXBException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private Button sendButton;
    @FXML
    private Button saveConvoButton;
    @FXML
    private VBox vBoxMessage;
    @FXML
    private ScrollPane scrollPaneMain;
    @FXML
    private VBox vBoxAllMessages;

    private Server server;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        prepareMessageHBox(Pos.CENTER, "Miroslav Peric", vBoxAllMessages);
        // TODO: 15.10.2022. SERVER NOT WORKING
            //server = new Server(new ServerSocket(1234));

        vBoxMessage.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPaneMain.setVvalue((Double) newValue));

        //server.receiveMessageFromClient(vBoxMessage);

        sendButton.setOnAction(actionEvent -> {
            String messageToSend = textField.getText();
            if (!messageToSend.isEmpty()){
                prepareMessageHBox(Pos.CENTER_RIGHT, messageToSend, vBoxMessage);
                //server.sendMessageToClient(messageToSend);
                textField.clear();

                addMessageToComponent("Ja sam odgovor na svako pitanje", vBoxMessage);
                saveConversation();
                ConversationManager.addMessage(Message.createMessage(messageToSend,
                        UserManager.getLoggedInUser()));
            }
        });

        saveConvoButton.setOnAction(actionEvent -> {
            try {
                ConversationManager.saveConversation();
            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("Cannot save this convo, contact support xd");
            }
        });
    }

    private void prepareMessageHBox(Pos centerRight, String messageToSend, VBox vBoxMessage) {
        HBox hBox = new HBox();
        hBox.setAlignment(centerRight);
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

        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> vBox.getChildren().add(hBox));
    }

    private void saveConversation(){
        ObservableList<Node> conversation = vBoxMessage.getChildren();
        conversation.forEach(node -> {
            HBox hBox = (HBox) node;
            ObservableList<Node> childrenHbox = hBox.getChildren();
            childrenHbox.forEach(x -> {
                if (x instanceof TextFlow){
                    ObservableList<Node> childrenTextFlow = ((TextFlow) x).getChildren();
                    childrenTextFlow.forEach(y -> {
                        if (y instanceof Text){
                            System.out.println(((Text)y).getText());
                        }
                    });
                }
            });
        });
    }

}
