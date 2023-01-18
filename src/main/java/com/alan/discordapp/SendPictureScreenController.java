package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.models.ImageMessage;
import com.alan.models.Message;
import com.alan.rmi.PictureService;
import com.alan.rmi.PictureServiceImpl;
import com.alan.rmi.RMIServer;
import com.alan.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class SendPictureScreenController implements Initializable {

    @FXML
    private ScrollPane scrollPanePictures;
    @FXML
    private VBox vBoxPictures;

    @FXML
    private Button uploadAndSendButton;

    private Registry serverRegistry;
    private PictureService pictureService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            serverRegistry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            pictureService = (PictureService) serverRegistry.lookup("test");
            loadListeners();
            loadImages();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadImages() {
        try {
            pictureService
                    .receiveAllSentPictures()
                    .forEach(imageMessage -> {
                        vBoxPictures.getChildren().add(prepareImageBoxDesign(imageMessage.getSentFile()));
                    });
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadListeners() {
        vBoxPictures.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPanePictures.setVvalue((Double) newValue));
    }

    private final FileChooser fileChooser = new FileChooser();
    public void uploadAndSendPicture() throws RemoteException {
        fileChooser.setTitle("Select Picture to Send");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            HBox imageBoxDesign = prepareImageBoxDesign(file);
            vBoxPictures.getChildren().add(imageBoxDesign);
            pictureService.sendPicture(new ImageMessage(UserManager.getLoggedInUser(), file));

        }
    }

    private HBox prepareImageBoxDesign(File file) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5,5,5,10));

        Text text = new Text();
        text.setText("Picture: ");
        hbox.getChildren().add(text);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setImage(new Image(file.toURI().toString()));

        hbox.getChildren().add(imageView);

        Button downloadButton = new Button();
        downloadButton.setText("Download");
        downloadButton.setOnAction(actionEvent -> {
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
            File savedFile = fileChooser.showSaveDialog(vBoxPictures.getScene().getWindow());
            if (savedFile != null) {
                saveFileToSystem(savedFile, file);
            }
        });

        hbox.getChildren().add(downloadButton);

        return hbox;
    }

    private void saveFileToSystem(File savedFile, File file) {
        try {
            FileUtils.copyFileContentToAnotherFile(file, savedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
