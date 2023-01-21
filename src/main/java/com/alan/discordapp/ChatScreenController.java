package com.alan.discordapp;

import com.alan.businessLayer.ConversationManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {

    private final static String BLUE_MESSAGES = "-fx-color: rgb(239, 242, 255); " +
            "-fx-background-color: rgb(15,125,242); " +
            "-fx-background-radius: 20px;";

    private final static String GREY_MESSAGES = "-fx-background-color: rgb(233,233,235); " +
            "-fx-background-radius: 20px;";

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
    private ScrollPane scrollPaneOnlineUsers;
    @FXML
    private VBox vBoxOnlineUsers;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        client = new Client(UserManager.getLoggedInUser());
        initListeners();

        client.listenForMessage(vBoxMessage);
        // vBoxOnlineUsers.getChildren().add(prepareOnlineUserBoxDesign(Pos.CENTER, new User("Alan", "Dautovic")));
        //client.listenForNewOnlineUsers(vBoxOnlineUsers);


    }

    private void initListeners() {
        vBoxMessage.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPaneMain.setVvalue((Double) newValue));

        sendButton.setOnAction(actionEvent -> {
            String messageToSend = textField.getText();
            UserManager.getLoggedInUser().addMessage();
            if (!messageToSend.isEmpty()){
                vBoxMessage.getChildren()
                        .add(prepareMessageBoxDesign(Pos.CENTER_RIGHT, messageToSend, BLUE_MESSAGES));

                TextMessage message = new TextMessage(UserManager.getLoggedInUser(), messageToSend);
                ConversationManager.addMessage(message);
                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textField.clear();
            }
        });
    }

    public static void addMessageToComponent(String messageFromClient, VBox vBox){
        Platform.runLater(()
                -> vBox.getChildren()
                .add(prepareMessageBoxDesign(Pos.CENTER_LEFT, messageFromClient, GREY_MESSAGES)));
    }

    public static void addOnlineUserToComponent(User user, VBox vBox){
        Platform.runLater(() -> {
            vBox.getChildren().add(prepareOnlineUserBoxDesign(Pos.CENTER, user));
        });
    }

    private static HBox prepareOnlineUserBoxDesign(Pos center, User user) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        Text text = new Text(user.getFirstName() + " " + user.getLastName());
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(10, 10, 10, 10));

        textFlow.setStyle(BLUE_MESSAGES);

        hBox.getChildren().add(textFlow);
        return hBox;
    }

    private static HBox prepareMessageBoxDesign(Pos position, String message, String design){
        HBox hBox = new HBox();
        hBox.setAlignment(position);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(design);

        textFlow.setPadding(new Insets(5,5,5,10));

        hBox.getChildren().add(textFlow);

        return hBox;
    }

}
