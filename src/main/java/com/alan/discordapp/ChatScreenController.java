package com.alan.discordapp;

import com.alan.businessLayer.ConversationManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.Client;
import com.alan.models.Message;
import com.alan.models.Server;
import com.alan.models.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import java.lang.Object;

public class ChatScreenController implements Initializable {

    private final static String BLUE_MESSAGES = "-fx-color: rgb(239, 242, 255); " +
            "-fx-background-color: rgb(15,125,242); " +
            "-fx-background-radius: 20px;";

    private final static String GREY_MESSAGES = "-fx-background-color: rgb(233,233,235); " +
            "-fx-background-radius: 20px;";

    @FXML
    private TextField textField;

    @FXML
    private Button sendPicture;
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
        loadConversations();
        initListeners();

        client.listenForMessage(vBoxMessage);
        // vBoxOnlineUsers.getChildren().add(prepareOnlineUserBoxDesign(Pos.CENTER, new User("Alan", "Dautovic")));
        client.listenForNewOnlineUsers(vBoxOnlineUsers);


    }

    private void initListeners() {
        vBoxMessage.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPaneMain.setVvalue((Double) newValue));

        sendButton.setOnAction(actionEvent -> {
            String messageToSend = textField.getText();
            if (!messageToSend.isEmpty()){
                vBoxMessage.getChildren()
                        .add(prepareMessageBoxDesign(Pos.CENTER_RIGHT, messageToSend, BLUE_MESSAGES));

                Message message = Message.createMessage(messageToSend, UserManager.getLoggedInUser());
                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textField.clear();

                ConversationManager.addMessage(message);
            }
        });

        saveConvoButton.setOnAction(actionEvent -> {
            try {
                ConversationManager.saveConversation();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot save this convo, contact support");
            }
        });
    }

    private void loadConversations() {
        try {
            ConversationManager.loadConversation()
                    .getAllMessages().forEach(msg -> {
                        vBoxMessage.getChildren()
                                .add(prepareMessageBoxDesign(Pos.CENTER_RIGHT,
                                        msg.getMessageContent(),
                                        BLUE_MESSAGES));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private final FileChooser fileChooser = new FileChooser();
    public void sendPicture(){
        fileChooser.setTitle("Select Picture to Send");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null){
            Image image = new Image(file.toURI().toString());
            HBox imageBoxDesign = prepareImageBoxDesign(image);
            vBoxMessage.getChildren().add(imageBoxDesign);
            ConversationManager.addMessage(Message.createMessage(file, UserManager.getLoggedInUser()));
        }
    }

    private HBox prepareImageBoxDesign(Image image) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(5,5,5,10));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setImage(image);

        hbox.getChildren().add(imageView);

        return hbox;
    }

}
