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

    private Server server;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            ConversationManager.loadConversation()
                    .getAllMessages().forEach(msg -> {
                        vBoxMessage.getChildren()
                                        .add(prepareMessageBoxDesign(Pos.CENTER_RIGHT,
                                                msg.getMessageContent(),
                                                BLUE_MESSAGES));
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // TODO: 15.10.2022. SERVER NOT WORKING
        //server = new Server(new ServerSocket(1234));

        vBoxMessage.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPaneMain.setVvalue((Double) newValue));

        //server.receiveMessageFromClient(vBoxMessage);

        sendButton.setOnAction(actionEvent -> {
            String messageToSend = textField.getText();
            if (!messageToSend.isEmpty()){
                vBoxMessage.getChildren()
                        .add(prepareMessageBoxDesign(Pos.CENTER_RIGHT, messageToSend, BLUE_MESSAGES));

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
            } catch (IOException e) {
                throw new RuntimeException(e);
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
        }
    }

    private HBox prepareImageBoxDesign(Image image) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setImage(image);

        hbox.getChildren().add(imageView);

        return hbox;
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
