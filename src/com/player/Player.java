package com.player;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.Utils.*;


public final class Player extends Rectangle {
    private static double speedY;

    public Player(Pane primaryPane, double x, Paint paint, List<KeyCode> keyCodeList) {
        super(x, (SCENE_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        primaryPane.getChildren().add(this);
        setStroke(paint);
        Movement.addListeners(primaryPane);
        speedY = PADDLE_START_SPEED_Y;
    }

    public static void move(Player leftPlayer, Player rightPlayer) {
        if (!(Movement.isUpLeftPaddleProperty().getValue() == Movement.isDownLeftPaddleProperty().getValue())) {
            if (Movement.isUpLeftPaddleProperty().getValue() && (leftPlayer.getY() > 0)) {
                leftPlayer.setY(leftPlayer.getY() - speedY);
            } else if (Movement.isDownLeftPaddleProperty().getValue() && ((leftPlayer.getY() + PADDLE_HEIGHT) < SCENE_HEIGHT)) {
                leftPlayer.setY(leftPlayer.getY() + speedY);
            }
        }

        if (!(Movement.isUpRightPaddleProperty().getValue() == Movement.isDownRightPaddleProperty().getValue())) {
            if (Movement.isUpRightPaddleProperty().getValue() && (rightPlayer.getY() > 0)) {
                rightPlayer.setY(rightPlayer.getY() - speedY);
            } else if (Movement.isDownRightPaddleProperty().getValue() && ((rightPlayer.getY() + PADDLE_HEIGHT) < SCENE_HEIGHT)) {
                rightPlayer.setY(rightPlayer.getY() + speedY);
            }
        }
    }

    public void resetPlayer(double x) {
        setX(x);
        setY((SCENE_HEIGHT - PADDLE_HEIGHT) / 2);
        speedY = PADDLE_START_SPEED_Y;
    }
}