package com.player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.Utils.*;

public final class Ball extends Circle {
    private static final IntegerProperty leftPlayerScore, rightPlayerScore;
    private static int numOfBalls;

    static {
        leftPlayerScore = new SimpleIntegerProperty(0);
        rightPlayerScore = new SimpleIntegerProperty(0);
        numOfBalls = 1;
    }

    private double speedX, speedY;

    public Ball(Pane primaryPane) {
        super(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, 7, Color.BLACK);
        primaryPane.getChildren().add(this);
        resetBallSpeed();
    }

    public static int getRightPlayerScore() {
        return rightPlayerScore.getValue();
    }

    public static int getLeftPlayerScore() {
        return leftPlayerScore.getValue();
    }

    public static void resetScore() {
        leftPlayerScore.set(0);
        rightPlayerScore.set(0);
    }

    public void update(Player player1, Player player2) {
        if (!isColliding(player1, player2)) {
            setCenterX(getCenterX() + speedX);
            setCenterY(getCenterY() + speedY);
            speedX += ((speedX > 0) ? 1 : -1) * 0.01;
            speedY += ((speedY > 0) ? 1 : -1) * 0.01;
        }
    }

    private boolean isColliding(Player player1, Player player2) {
        if ((getCenterY() - getRadius() <= 0) || (getCenterY() + getRadius() >= SCENE_HEIGHT)) {
            speedY *= -1;
            return true;
        }
        if (collision(player1) || collision(player2)) {
            speedX *= -1;
            return true;
        }
        if ((getCenterX() - getRadius() <= 0) || (this.getCenterX() + this.getRadius() >= SCENE_WIDTH)) {
            if (getCenterX() - getRadius() <= 0) {
                rightPlayerScore.setValue(rightPlayerScore.getValue() + 1);
            } else {
                leftPlayerScore.setValue(leftPlayerScore.getValue() + 1);
            }
            resetBall();
            return true;
        }
        return false;
    }

    private boolean collision(Player player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }

    private void resetBall() {
        this.setCenterX(SCENE_WIDTH / 2);
        this.setCenterY(SCENE_HEIGHT / 2);
        resetBallSpeed();
    }

    private void resetBallSpeed() {
        this.speedX = rnd.nextBoolean() ? 1 : -1 * rnd.nextInt(5);
        this.speedY = rnd.nextBoolean() ? 1 : -1 * rnd.nextInt(5);
        if (Math.abs(speedX) < 2) {
            this.speedX += (this.speedX > 0) ? 1 : -1 * 2;
        }
        if (Math.abs(speedY) < 2) {
            this.speedY += (this.speedY > 0) ? 1 : -1 * 2;
        }
    }
}