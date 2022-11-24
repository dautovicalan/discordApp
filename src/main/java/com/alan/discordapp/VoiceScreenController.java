package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.models.User;
import com.alan.utils.AlertUtils;
import com.alan.utils.JavaSoundRecorder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class VoiceScreenController implements Initializable {
    //http://www.java2s.com/Tutorial/Java/0120__Development/CapturingAudiowithJavaSoundAPI.htm
    @FXML
    private Button sendVoiceMessage;
    @FXML
    private Button recordVoiceMessage;
    @FXML
    private Button playVoiceMessage;
    @FXML
    private Button stopVoiceMessage;
    @FXML
    private Button importAudio;
    @FXML
    private Button deleteAudioButton;
    @FXML
    private ListView<File> lwVoiceMessages;

    private AudioClip audioSample;

    private boolean isRecording = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File dir = new File(Paths.get("voiceMessages").toUri());
        File[] voiceMessages = dir.listFiles();

        for (File f :
                voiceMessages) {
            System.out.println(f.getName());
            lwVoiceMessages.getItems().add(f);
        }
    }

    public void sendVoiceMessage(){
        User loggedInUser = UserManager.getLoggedInUser();
    }

    public void recordVoiceMessage(){
        JavaSoundRecorder javaSoundRecorder = new JavaSoundRecorder();
        if (!isRecording){
            Thread thread = new Thread(javaSoundRecorder);
            thread.start();

            recordVoiceMessage.setText("Stop recording");
            isRecording = true;
        } else {
            javaSoundRecorder.finish();
            javaSoundRecorder.cancel();

            recordVoiceMessage.setText("Start recording");
            isRecording = false;
        }

    }

    public void playVoiceMessage(){
        if (lwVoiceMessages.getSelectionModel().getSelectedItem() == null){
            AlertUtils.showInfoMessage("Please select file to play");
            return;
        }
        if (audioSample != null && audioSample.isPlaying()){
            return;
        }
        audioSample = new AudioClip(lwVoiceMessages.getSelectionModel().getSelectedItem().toURI().toString());
        audioSample.play();
    }

    public void stopVoiceMessage(){
        if (audioSample != null && audioSample.isPlaying()){
            audioSample.stop();
        }
    }

    private final FileChooser fileChooser = new FileChooser();
    public void importAudio() throws IOException {
        fileChooser.setTitle("Select audio files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*wav"));
        File chosenFile = fileChooser.showOpenDialog(null);

        if (chosenFile != null){
            Path newFile = Files.copy(Paths.get(chosenFile.toURI()), Paths.get("voiceMessages", chosenFile.getName()));
            lwVoiceMessages.getItems().add(new File(newFile.toUri()));
        }
    }

    public void deleteAudio() throws IOException {
        if (lwVoiceMessages.getSelectionModel().getSelectedItem() == null){
            AlertUtils.showInfoMessage("Please select file to delete");
            return;
        }
        if (audioSample != null && audioSample.isPlaying()){
            audioSample.stop();
        }
        Files.deleteIfExists(Path.of(lwVoiceMessages.getSelectionModel().getSelectedItem().getPath()));
        lwVoiceMessages.getItems()
                .remove(lwVoiceMessages.getSelectionModel().getSelectedIndex());
    }

}
