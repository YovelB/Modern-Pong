package com.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Movement {
    private static BooleanProperty isUpLeftPaddle, isDownLeftPaddle, isUpRightPaddle, isDownRightPaddle;

    static {
        isUpLeftPaddle = new SimpleBooleanProperty(false);
        isDownLeftPaddle = new SimpleBooleanProperty(false);
        isUpRightPaddle = new SimpleBooleanProperty(false);
        isDownRightPaddle = new SimpleBooleanProperty(false);
    }

    public static void addListeners(Pane primaryPane) {
        EventHandler<KeyEvent> keyPressedEvent = keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP:
                    isUpRightPaddle.setValue(true);
                    break;
                case W:
                    isUpLeftPaddle.setValue(true);
                    break;
                case DOWN:
                    isDownRightPaddle.setValue(true);
                    break;
                case S:
                    isDownLeftPaddle.setValue(true);
                    break;
            }
        };
        EventHandler<KeyEvent> keyReleasedEvent = keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP:
                    isUpRightPaddle.setValue(false);
                    break;
                case W:
                    isUpLeftPaddle.setValue(false);
                    break;
                case DOWN:
                    isDownRightPaddle.setValue(false);
                    break;
                case S:
                    isDownLeftPaddle.setValue(false);
                    break;
            }
        };
        primaryPane.setOnKeyPressed(keyPressedEvent);
        primaryPane.setOnKeyReleased(keyReleasedEvent);
    }

    public static BooleanProperty isUpLeftPaddleProperty() {
        return isUpLeftPaddle;
    }

    public static BooleanProperty isDownLeftPaddleProperty() {
        return isDownLeftPaddle;
    }

    public static BooleanProperty isUpRightPaddleProperty() {
        return isUpRightPaddle;
    }

    public static BooleanProperty isDownRightPaddleProperty() {
        return isDownRightPaddle;
    }
}
