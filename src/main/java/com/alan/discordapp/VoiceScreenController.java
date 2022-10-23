package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.models.User;
import com.alan.utils.JavaSoundRecorder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.AudioClip;

import java.net.URL;
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
    private ListView<User> lwVoiceMessages;

    private AudioClip audioSample;

    private boolean isRecording = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        if (audioSample != null && audioSample.isPlaying()){
            return;
        }
        audioSample = new AudioClip(Paths.get("mustSong.mp3").toUri().toString());
        audioSample.play();
    }

    public void stopVoiceMessage(){
        if (audioSample.isPlaying()){
            audioSample.stop();
        }
    }

}
