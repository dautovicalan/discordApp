package com.alan.discordapp;

import com.alan.rmi.VoiceService;
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
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.ResourceBundle;

public class VoiceScreenController implements Initializable {
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
    private Button refreshButton;
    @FXML
    private ListView<File> lwVoiceMessages;
    @FXML
    private ListView<File> lwRmiVoiceMessages;

    private AudioClip audioSample;

    private boolean isRecording = false;

    private Registry serverRegistry;
    private VoiceService voiceService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLocalVoiceMessages();
        loadRmiVoiceMessages();
    }
    private void loadLocalVoiceMessages() {

        new Thread(() -> {
            File dir = new File(Paths.get("voiceMessages").toUri());
            File[] voiceMessages = dir.listFiles();

            for (File f :
                    voiceMessages) {
                System.out.println(f.getName());
                lwVoiceMessages.getItems().add(f);
            }
        }).start();
    }

    private void loadRmiVoiceMessages() {
        try {
            serverRegistry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            voiceService = (VoiceService) serverRegistry.lookup("voiceService");
            loadVoices();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadVoices() throws RemoteException {
        voiceService
                .receiveAllVoiceMessages()
                .forEach(voiceFile -> {
                    lwRmiVoiceMessages.getItems().add(voiceFile);
                });
    }

    public void sendVoiceMessage(){
        if (lwVoiceMessages.getSelectionModel().getSelectedItem() == null) {
            AlertUtils.showInfoMessage("Please select local file to send!");
            return;
        }
        try {
            voiceService.sendVoiceMessage(lwVoiceMessages.getSelectionModel().getSelectedItem());
            AlertUtils.showInfoMessage("Success sending voice message over RMI");
            refreshRmiVoices();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private JavaSoundRecorder javaSoundRecorder = JavaSoundRecorder.createRecorder();
    private Thread runningThread = new Thread(javaSoundRecorder);
    public void recordVoiceMessage() {
        if (!isRecording){
            isRecording = true;
            recordVoiceMessage.setText("Stop recording");
            runningThread.start();
        } else {
            Optional<File> file = javaSoundRecorder.stopRecording();
            if (file.isPresent()){
                lwVoiceMessages.getItems().add(file.get());
            }
            recordVoiceMessage.setText("Start recording");
            isRecording = false;
        }
    }

    public void playVoiceMessage(){
        if (lwVoiceMessages.getSelectionModel().getSelectedItem() == null){
            AlertUtils.showInfoMessage("Please select local file to play");
            return;
        }
        playSound(lwVoiceMessages.getSelectionModel().getSelectedItem());
    }

    public void playRmiVoiceMessage(){
        if (lwRmiVoiceMessages.getSelectionModel().getSelectedItem() == null){
            AlertUtils.showInfoMessage("Please select RMI file to play");
            return;
        }
        playSound(lwRmiVoiceMessages.getSelectionModel().getSelectedItem());
    }

    private void playSound(File file){
        if (audioSample != null && audioSample.isPlaying()){
            AlertUtils.showInfoMessage("Already playing something...");
            return;
        }
        audioSample = new AudioClip(file.toURI().toString());
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

    public void refreshRmiVoices() {
        lwRmiVoiceMessages.getItems().clear();
        try {
            loadVoices();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
