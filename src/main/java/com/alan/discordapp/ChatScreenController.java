package com.alan.discordapp;

import com.alan.businessLayer.ConversationManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.Client;
import com.alan.models.Conversation;
import com.alan.models.TextMessage;
import com.alan.utils.AlertUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
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
    private ListView<Conversation> lwSavedConversations;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadConversations();

        client = new Client(UserManager.getLoggedInUser());
        initListeners();

        client.listenForMessage(vBoxMessage);

    }

    private void loadConversations() {
        File dir = new File(Paths.get("savedChats").toUri());
        File[] savedChats = dir.listFiles();

        if (savedChats.length != 0) {
            try {
                List<Conversation> allConversations = ConversationManager.loadAllConversations(savedChats);
                lwSavedConversations.setItems(FXCollections.observableArrayList(allConversations));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initListeners() {
        vBoxMessage.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPaneMain.setVvalue((Double) newValue));

        sendButton.setOnAction(actionEvent -> {
            String messageToSend = textField.getText();
            if (!messageToSend.isEmpty()){
                vBoxMessage.getChildren()
                        .add(prepareMessageBoxDesign(Pos.CENTER_RIGHT, messageToSend, BLUE_MESSAGES));

                TextMessage message = new TextMessage(UserManager.getLoggedInUser(), messageToSend);
                ConversationManager.addMessage(message);
                UserManager.getLoggedInUser().addMessage();
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

    public void saveConversation(){
        try {
            ConversationManager.saveCurrentUserConversations(UserManager.getLoggedInUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadConversation(){
        if (lwSavedConversations.getSelectionModel().getSelectedItem() == null){
            AlertUtils.showInfoMessage("Please select conversation to load");
            return;
        }
        Conversation selectedConversation = lwSavedConversations.getSelectionModel().getSelectedItem();
        selectedConversation.getAllMessages().forEach(msg -> {
            if (msg.getMessageSender().equals(selectedConversation.getUserConversation())){
                vBoxMessage.getChildren().add(prepareMessageBoxDesign(Pos.CENTER_RIGHT, msg.getMessageContent(), BLUE_MESSAGES));
            } else {
                vBoxMessage.getChildren().add(prepareMessageBoxDesign(Pos.CENTER_LEFT, msg.getMessageContent(), GREY_MESSAGES));
            }
        });
    }

}
