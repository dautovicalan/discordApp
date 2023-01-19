package com.alan.discordapp;

import com.alan.businessLayer.SettingsManager;
import com.alan.businessLayer.UserManager;
import com.alan.utils.ResolutionChangerUtil;
import com.alan.utils.XMLUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameScreenController implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button statsButton;
    @FXML
    private Button profileButton;

    @FXML
    private Button rankButton;

    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;

    @FXML
    private MenuItem miGenerateDocumentation;

    @FXML
    private MenuItem miSaveToXml;


    private static final String CLASS_EXTENSION = ".class";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        miSaveToXml.setOnAction(e -> {
            try {
                XMLUtils.saveConfigurationToXmlFile();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("XML Success");
                alert.setContentText("Saved to XML successfully");
                alert.show();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            } catch (TransformerException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void showHomeScene(){
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(homeView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStatsScene(){
        try {
            Parent statsView = FXMLLoader.load(getClass().getResource("statsScreen.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(statsView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProfileScene(){
        try {
            Parent profileView = FXMLLoader.load(getClass().getResource("profileScreen.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(profileView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showRankScene() throws IOException {
        Parent settingView = FXMLLoader.load(getClass().getResource("leaderboardScreen.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(settingView);
    }
    public void showSettingsScene() throws IOException {
        Parent settingView = FXMLLoader.load(getClass().getResource("settingsScreen.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(settingView);
    }

    public void logout() {
        UserManager.logout();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 500, 500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = MainApplication.getMainStage();
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        stage.centerOnScreen();
        ResolutionChangerUtil.changeResolution(SettingsManager.getCurrenSettings().getResolutionType());
        stage.show();
    }

    public void generateDocumentation() {
        File documentationFile = new File("documentation.html");
        try {

            FileWriter writer = new FileWriter(documentationFile);

            writer.write("<!DOCTYPE html>");
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>Project documentation</title>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<h1>Project documentation</h1>");
            writer.write("<p>Class list:</p>");

            List<Path> paths = Files.walk(Paths.get("."))
                    .filter(path -> path.getFileName().toString().endsWith(CLASS_EXTENSION))
                    .collect(Collectors.toList());

            for (Path path : paths) {
                //System.out.println("Path: " + path);
                String[] tokens = path.toString().split(Pattern.quote(System.getProperty("file.separator")));

                Boolean startBuildingPath = false;

                StringBuilder sb = new StringBuilder();

                for (String token : tokens) {
                    if ("classes".equals(token)) {
                        startBuildingPath = true;
                        continue;
                    }

                    if (startBuildingPath) {
                        if (token.endsWith(CLASS_EXTENSION)) {
                            sb.append(token.substring(0, token.indexOf(".")));
                        } else {
                            sb.append(token);
                            sb.append(".");
                        }
                    } else {
                        continue;
                    }
                }

                if ("module-info".equals(sb.toString())) {
                    continue;
                }

                System.out.println("Fully qualified name: " + sb.toString());

                try {
                    Class<?> clazz = Class.forName(sb.toString());

                    writer.write("<h2>" + Modifier.toString(clazz.getModifiers()) + " " + clazz.getName() + "</h2>");

                    StringBuilder classFieldString = new StringBuilder();

                    for (Field classField : clazz.getDeclaredFields()) {
                        Annotation[] annotations = classField.getAnnotations();
                        if (annotations.length != 0) {
                            for (Annotation a : annotations) {
                                classFieldString.append(a.toString());
                                classFieldString.append("<br />");
                            }
                        }
                        classFieldString.append(Modifier.toString(classField.getModifiers()));
                        classFieldString.append(" ");
                        classFieldString.append(classField.getType().getSimpleName());
                        classFieldString.append(" ");
                        classFieldString.append(classField.getName());
                        classFieldString.append(" ");
                        classFieldString.append("<br /><br />");
                    }

                    writer.write("<h3>Fields</h3>");

                    writer.write("<h4>" + classFieldString + "</h4>");

                    Constructor[] constructors = clazz.getConstructors();

                    writer.write("<h3>Constructors:</h3>");

                    for (Constructor c : constructors) {

                        String constructorParams = generateDocumentation(c);

                        writer.write("<h4>Constructor:" + Modifier.toString(c.getModifiers()) + " " + c.getName()
                                + "(" + constructorParams + ")" + "</h4>");
                    }

                    Method[] methods = clazz.getMethods();

                    writer.write("<h3>Methods:</h3>");

                    for (Method m : methods) {

                        String methodsParams = generateDocumentation(m);

                        StringBuilder exceptionsBuilder = new StringBuilder();

                        for (int i = 0; i < m.getExceptionTypes().length; i++) {
                            if (exceptionsBuilder.isEmpty()) {
                                exceptionsBuilder.append(" throws ");
                            }

                            Class exceptionClass = m.getExceptionTypes()[i];
                            exceptionsBuilder.append(exceptionClass.getSimpleName());

                            if (i < m.getExceptionTypes().length - 1) {
                                exceptionsBuilder.append(", ");
                            }
                        }

                        writer.write("<h4>Method:" + Modifier.toString(m.getModifiers())
                                + " " + m.getReturnType().getSimpleName()
                                + " " + m.getName() + "(" + methodsParams + ")"
                                + " " + exceptionsBuilder.toString()
                                + "</h4>");
                    }

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            writer.write("</body>");
            writer.write("</html>");
            writer.close();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while generating documentation!");
            alert.setHeaderText("Cannot find the files");
            alert.setContentText("The class files cannot be accessed.");

            alert.showAndWait();
        }
    }

    private <T extends Executable> String generateDocumentation(T executable) {
        Parameter[] params = executable.getParameters();

        StringBuilder methodsParams = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            String modifierString = Modifier.toString(params[i].getModifiers());

            if (!modifierString.isEmpty()) {
                methodsParams.append(modifierString);
                methodsParams.append(" ");
            }
            methodsParams.append(params[i].getType().getSimpleName());
            methodsParams.append(" ");
            methodsParams.append(params[i].getName());

            if (i < (params.length - 1)) {
                methodsParams.append(", ");
            }
        }
        return methodsParams.toString();
    }
}
