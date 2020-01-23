package com;

import com.gui.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Scene startScene = new Scene(FXMLLoader.load(getClass().getResource("gui/StartScene.fxml")), 800, 600);
        primaryStage.setTitle("Modern Pong");
        StartController.addKeyListener(startScene);
        primaryStage.setScene(startScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.requestFocus();
    }
}
