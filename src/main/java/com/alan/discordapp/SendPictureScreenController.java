package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.rmi.PictureServiceImpl;
import com.alan.rmi.RMIServer;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class SendPictureScreenController implements Initializable {

    @FXML
    private ScrollPane scrollPanePictures;
    @FXML
    private VBox vBoxPictures;

    @FXML
    private Button uploadAndSendButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadListeners();
    }

    private void loadListeners() {
        vBoxPictures.heightProperty().addListener((observableValue, number, newValue)
                -> scrollPanePictures.setVvalue((Double) newValue));
    }

    private final FileChooser fileChooser = new FileChooser();
    public void uploadAndSendPicture(){
        fileChooser.setTitle("Select Picture to Send");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            HBox imageBoxDesign = prepareImageBoxDesign(
                    new Image(file.toURI().toString())
            );
            vBoxPictures.getChildren().add(imageBoxDesign);
        }
    }

    private HBox prepareImageBoxDesign(Image image) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5,5,5,10));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setImage(image);

        hbox.getChildren().add(imageView);

        Button downloadButton = new Button();
        downloadButton.setText("Download");
        downloadButton.setOnAction(actionEvent -> {
            // TODO downlaod implementation on local pc
        });

        hbox.getChildren().add(downloadButton);

        return hbox;
    }

}
