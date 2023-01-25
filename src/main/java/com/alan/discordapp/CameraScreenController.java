package com.alan.discordapp;

import com.alan.utils.AlertUtils;
import com.alan.utils.CameraCaptureUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CameraScreenController implements Initializable {

    @FXML
    private ImageView ivVideo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private VideoCapture videoCapture = null;
    private final Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();

    public void startCamera(){
        videoCapture = new VideoCapture(0);
        DaemonThread daemonThread = new DaemonThread();
        Thread thread = new Thread(daemonThread);
        thread.setDaemon(true);
        daemonThread.runnable = true;
        thread.start();
    }

    public void saveImage(){
        if (videoCapture.isOpened()){
            String capturedImagePath = "capturedPictures";
            Imgcodecs.imwrite(capturedImagePath + File.separator + LocalDateTime.now(), frame);
            AlertUtils.showInfoMessage("Successfully saved to " + capturedImagePath + " folder");
        }
    }

    class DaemonThread implements Runnable{

        // modify the value of a variable by different threads
        public volatile boolean runnable = false;
        @Override
        public void run() {
            synchronized (this){
                while (runnable){
                    if (videoCapture.grab()){
                        try {
                            videoCapture.retrieve(frame);
                            Imgcodecs.imencode(".jpg", frame, mem);
                            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                            ivVideo.setImage(CameraCaptureUtils.convertToFxImage(bufferedImage));

                            if (!runnable){
                                System.out.println("Waiting...");
                                this.wait();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
