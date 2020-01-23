package com.gui;

import com.Main;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static com.Utils.SCENE_HEIGHT;
import static com.Utils.SCENE_WIDTH;

public class StartController implements Initializable {

    private static MenuBox menu;
    private static Label position;
    @FXML
    private Pane mainPane;

    public static void addKeyListener(Scene startScene) {
        startScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (menu.isVisible()) {
                    menu.hide();
                } else {
                    menu.show();
                }
            }
        });

        //startScene.setOnMouseMoved(event -> position.setText("pos = " + event.getX() + " " + event.getY()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        position = new Label();
        position.setLayoutX(SCENE_WIDTH / 2);
        position.setLayoutY(SCENE_HEIGHT / 2);

        //TODO check about MediaPlayer.
        /*MediaPlayer mp = new MediaPlayer(new Media(new File("Resources/Music/HaloReachOverture.mp3").toURI().toString()));
        mp.play();*/
        //mp.setAutoPlay(true);

        try (InputStream is = Files.newInputStream(Paths.get("Resources/Images/HaloReachImg800x600.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(800);
            img.setFitHeight(600);
            mainPane.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        menu = new MenuBox("Modern Pong",
                new MenuItem("Resume Game", event -> {

                }),
                new MenuItem("New Game", event -> {
                    try {
                        Scene gameScene = new Scene(FXMLLoader.load(getClass().getResource("GameScene.fxml")), 800, 600);
                        Main.primaryStage.setScene(gameScene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("Options", event -> {
                    //TODO maybe a new scene options and a back option
                    //also add sound scroller option(mute or bring music level up or low)
                }),
                new MenuItem("Quit", event -> {
                    Platform.exit();
                }));
        mainPane.getChildren().addAll(menu, position);
        mainPane.setFocusTraversable(true);
    }

    private static class MenuBox extends StackPane {
        public MenuBox(String title, MenuItem... items) {
            Rectangle bg = new Rectangle(SCENE_WIDTH / 4, SCENE_HEIGHT);
            bg.setOpacity(0.4);

            DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
            shadow.setSpread(0.8);
            bg.setEffect(shadow);

            Text text = new Text(title + "    ");
            text.setFill(Color.WHITE);
            text.setFont(Font.font(25));

            Line hSep = new Line();
            hSep.setEndX(SCENE_WIDTH / 4);
            hSep.setStroke(Color.DARKGRAY);
            hSep.setOpacity(0.2);

            Line vSep = new Line();
            vSep.setStartX(SCENE_WIDTH / 4);
            vSep.setEndX(SCENE_WIDTH / 4);
            vSep.setEndY(SCENE_HEIGHT);
            vSep.setStroke(Color.DARKGRAY);
            vSep.setOpacity(0.2);

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_RIGHT);
            vBox.setPadding(new Insets(50, 0, 0, 0));
            vBox.getChildren().addAll(text, hSep);
            vBox.getChildren().addAll(items);

            setAlignment(Pos.TOP_RIGHT);
            getChildren().addAll(bg, vSep, vBox);
        }

        public void show() {
            setVisible(true);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(0);
            tt.play();
        }

        public void hide() {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(-(SCENE_WIDTH / 4));
            tt.setOnFinished(event -> setVisible(false));
            tt.play();
        }
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name, EventHandler onClickedEvent) {
            Rectangle bg = new Rectangle(SCENE_WIDTH / 4, 24);
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.BLACK), new Stop(0.2, Color.DARKGRAY));
            bg.setFill(gradient);
            bg.setVisible(false);
            bg.setEffect(new DropShadow(5, 0, 5, Color.BLACK));

            Text text = new Text(name + "      ");
            text.setFill(Color.LIGHTGRAY);
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER_RIGHT);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setVisible(true);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setVisible(false);
                text.setFill(Color.LIGHTGRAY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseReleased(onClickedEvent);

            /*setOnMouseReleased(event -> {
                //instead of returning to the default color setting an event will happen
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });*/
        }

    }
}
