package com.gui;

import com.player.Ball;
import com.player.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.Utils.*;

public class GameController implements Initializable {

    //fps parameters
    private final long[] frameTimes = new long[100];
    @FXML
    private BorderPane mainBorderPane;
    private Player paddleRight, paddleLeft;
    private Ball ball;
    private Label winnerLabel, fpsLabel, position;
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private Pane primaryPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        fpsLabel = new Label();
        //TODO add DropShadow to the labels
        position = new Label();
        position.setLayoutX(SCENE_WIDTH / 2);
        position.setLayoutY(SCENE_HEIGHT / 2);
        position.setVisible(true);

        primaryPane = new Pane();
        paddleRight = new Player(primaryPane, SCENE_WIDTH - PADDLE_WIDTH - PADDLE_OFFSET_X,
                Color.BLACK, Arrays.asList(KeyCode.UP, KeyCode.DOWN));
        paddleLeft = new Player(primaryPane, PADDLE_OFFSET_X,
                Color.BLACK, Arrays.asList(KeyCode.W, KeyCode.S));
        ball = new Ball(primaryPane);

        winnerLabel = new Label("Left Player wins");
        winnerLabel.setFont(Font.font(60));
        winnerLabel.setLayoutX(SCENE_WIDTH / 2 - 100);
        winnerLabel.setLayoutY(SCENE_HEIGHT / 2 - 50);
        winnerLabel.setVisible(false);
        Label scoreBoard = new Label();
        scoreBoard.setFont(Font.font(30));
        scoreBoard.setLayoutX(SCENE_WIDTH / 2 - 30);
        scoreBoard.setLayoutY(10);

        primaryPane.getChildren().addAll(scoreBoard, winnerLabel, position, fpsLabel);
        addListenerPos(primaryPane);
        primaryPane.setFocusTraversable(true);
        mainBorderPane.setCenter(primaryPane);

        /**
         * GameLoop called every frame and checks which player wins and moves the players and the balls, and fps and labels.
         */
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                calculateFPS(now);

                if (Ball.getLeftPlayerScore() >= WIN_SCORE || Ball.getRightPlayerScore() >= WIN_SCORE) {
                    if (Ball.getLeftPlayerScore() >= WIN_SCORE) {
                        //left player wins
                        //TODO TranslateTransition of the text saying that the player wins
                    } else {
                        // right player wins
                    }
                    //reset game
                    resetGame();
                } else {

                    scoreBoard.setText(Ball.getLeftPlayerScore() + " : " + Ball.getRightPlayerScore());
                    Player.move(paddleLeft, paddleRight);
                    ball.update(paddleLeft, paddleRight);
                    /*while (it.hasNext()) {
                        Ball ball = it.next();
                        ball.move(primaryPane, ballList, it, paddleLeft, paddleRight);
                    }*/
                }
            }
        }.start();
    }

    private void calculateFPS(long now) {
        long oldFrameTime = frameTimes[frameTimeIndex];
        frameTimes[frameTimeIndex] = now;
        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
        if (frameTimeIndex == 0) {
            arrayFilled = true;
        }
        if (arrayFilled) {
            long elapsedNanos = now - oldFrameTime;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
            double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
            fpsLabel.setText(String.format("FPS: %.3f", frameRate));
        }
    }

    private void addListenerPos(Pane primaryPane) {
        primaryPane.setOnMouseMoved(event -> position.setText("pos = " + event.getX() + " " + event.getY()));
    }

    private void resetGame() {
        paddleRight.resetPlayer(SCENE_WIDTH - PADDLE_WIDTH - PADDLE_OFFSET_X);
        paddleLeft.resetPlayer(PADDLE_OFFSET_X);
        Ball.resetScore();

        //primaryPane.getChildren().removeAll(ballList);
        /*ballList.clear();
        ballList.add(new Ball(primaryPane));
        it = ballList.listIterator();*/

        //Ball.resetBalls(primaryPane, ballList, it);
    }
}