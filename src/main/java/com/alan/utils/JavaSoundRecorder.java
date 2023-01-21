package com.alan.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.sound.sampled.*;

public class JavaSoundRecorder implements Runnable {

    private AudioFormat audioFormat;
    private DataLine.Info dataInfo;
    private TargetDataLine targetLine;

    private JavaSoundRecorder(){
        try {
            audioFormat = buildAudioFormatInstance();
            dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            if (!AudioSystem.isLineSupported(dataInfo)){
                System.out.println("System line not supported");
                System.exit(-1);
            }
            targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open();

        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static JavaSoundRecorder createRecorder(){
        return new JavaSoundRecorder();
    }

    private AudioFormat buildAudioFormatInstance() {
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        return new AudioFormat(encoding, 44100, 16, 2, 4, 44100, false);
    }

    private File recordedFile;
    @Override
    public void run() {
        try {
            System.err.println("Starting recording...");
            targetLine.start();
            AudioInputStream recordingStream = new AudioInputStream(targetLine);
            File outputFile = new File("voiceMessages/" + UUID.randomUUID() + ".wav");
            AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
            recordedFile = outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    public Optional<File> stopRecording(){
        targetLine.stop();
        targetLine.close();
        System.err.println("Stopping recording");
        if (this.recordedFile != null){
            return Optional.of(recordedFile);
        }
        return Optional.empty();
    }

    private void closeEverything() {
        targetLine.stop();
        targetLine.close();
        System.err.println("Stopping recording");
    }
}