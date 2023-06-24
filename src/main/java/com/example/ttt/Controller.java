package com.example.ttt;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.*;

import static java.lang.Thread.sleep;

public class Controller {
    @FXML
    private Label playerWins;
    @FXML
    private Label kiWins;
    @FXML
    private Button restartButton;

    @FXML
    private Button fieldOne;
    @FXML
    private Button fieldTwo;
    @FXML
    private Button fieldThree;
    @FXML
    private Button fieldFour;
    @FXML
    private Button fieldFive;
    @FXML
    private Button fieldSix;
    @FXML
    private Button fieldSeven;
    @FXML
    private Button fieldEight;
    @FXML
    private Button fieldNine;
    @FXML
    private Button startKi;
    @FXML
    private Label plays;

    State game = new State();
    Player player = new Player(true);
    Player ki = new Player(false);

    Boolean won = false;

    Boolean kiPlaying = false;

    @FXML
    protected void restart() {
        kiPlaying = false;
        restartButton.setDisable(true);
        enableAll();
        restartGame();
    }

    protected void restartGame() {
        clear(fieldOne, fieldTwo, fieldThree, fieldFour);
        clear(fieldFive, fieldSix, fieldSeven, fieldEight);
        fieldNine.setText("");
        fieldNine.setTextFill(Color.BLACK);
        won = false;
        game = new State();
        kiPlay();
    }

    private void clear(Button fieldOne, Button fieldTwo, Button fieldThree, Button fieldFour) {
        fieldOne.setText("");
        fieldOne.setTextFill(Color.BLACK);
        fieldTwo.setText("");
        fieldTwo.setTextFill(Color.BLACK);
        fieldThree.setText("");
        fieldThree.setTextFill(Color.BLACK);
        fieldFour.setText("");
        fieldFour.setTextFill(Color.BLACK);
    }

    protected void win(boolean b) {
        if (b) {
            playerWins.setText(String.valueOf(Integer.parseInt(playerWins.getText()) + 1));
            System.out.println("<==========================>");
            System.out.println(game);
            System.out.println("<==========================>");
        }
        else {
            kiWins.setText(String.valueOf(Integer.parseInt(kiWins.getText()) + 1));
        }
        if (!kiPlaying) {
            disableAll();
            restartButton.setDisable(false);
        }
        plays.setText(String.valueOf(Integer.parseInt(plays.getText()) + 1));
    }

    protected void testWin() {
        if (game.getWin()) {
            won = true;
            if (game.getMap().get("Ki")) win(false);
            else if (game.getMap().get("Player")) win(true);
        } else if (game.level() == 9) {
            won = true;
            if (!kiPlaying) restartButton.setDisable(false);
            plays.setText(String.valueOf(Integer.parseInt(plays.getText()) + 1));
        }
    }

    protected void disableAll() {
        fieldOne.setDisable(true);
        fieldTwo.setDisable(true);
        fieldThree.setDisable(true);
        fieldFour.setDisable(true);
        fieldFive.setDisable(true);
        fieldSix.setDisable(true);
        fieldSeven.setDisable(true);
        fieldEight.setDisable(true);
        fieldNine.setDisable(true);
    }

    protected void enableAll() {
        fieldOne.setDisable(false);
        fieldTwo.setDisable(false);
        fieldThree.setDisable(false);
        fieldFour.setDisable(false);
        fieldFive.setDisable(false);
        fieldSix.setDisable(false);
        fieldSeven.setDisable(false);
        fieldEight.setDisable(false);
        fieldNine.setDisable(false);
    }

    protected void kiPlay3() {
        if (!won) {
            int step = game.randomStep();
            doStep(step, player);
        }
    }

    protected void kiPlay() {
        if (!won) {
            int step = 0;
            if (game.level() == 8) {
                step = game.endStep();
            } else if(game.level() == 0) {
                Integer[] startMoves = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                //Integer[] startMoves = {1, 3, 7, 9, 5};
                //Integer[] startMoves = {5};
                step = startMoves[(int) Math.floor(Math.random() * startMoves.length)];
            } else {
                step = game.nextStep(game.minMax());
            }
            doStep(step, ki);
        }
    }

    private void doStep(int step, Player player) {
        game.addState(step, player);
        if (!kiPlaying) {
            switch (step) {
                case 1 -> {
                    fieldOne.setText("X");
                    fieldOne.setDisable(true);
                }
                case 2 -> {
                    fieldTwo.setText("X");
                    fieldTwo.setDisable(true);
                }
                case 3 -> {
                    fieldThree.setText("X");
                    fieldThree.setDisable(true);
                }
                case 4 -> {
                    fieldFour.setText("X");
                    fieldFour.setDisable(true);
                }
                case 5 -> {
                    fieldFive.setText("X");
                    fieldFive.setDisable(true);
                }
                case 6 -> {
                    fieldSix.setText("X");
                    fieldSix.setDisable(true);
                }
                case 7 -> {
                    fieldSeven.setText("X");
                    fieldSeven.setDisable(true);
                }
                case 8 -> {
                    fieldEight.setText("X");
                    fieldEight.setDisable(true);
                }
                case 9 -> {
                    fieldNine.setText("X");
                    fieldNine.setDisable(true);
                }
            }
        }
        testWin();
    }

    int countedPlaysKi = 0;
    protected void play() throws InterruptedException {
        while (countedPlaysKi < 1000) {
            Platform.runLater(() -> {
                restartGame();
                while (!won) {
                    kiPlay3();
                    kiPlay();
                }
                countedPlaysKi++;
            });
            Thread.sleep(20);
        }
        countedPlaysKi = 0;
        startKi.setDisable(false);
        restartButton.setDisable(false);
    }

    @FXML
    protected void startButton() {
        disableAll();
        kiPlaying = true;
        startKi.setDisable(true);
        restartButton.setDisable(true);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                play();
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    protected void onePressed() {
        fieldOne.setText("O");
        fieldOne.setDisable(true);
        game.addState(1, player);
        kiPlay();
    }
    @FXML
    protected void twoPressed() {
        fieldTwo.setText("O");
        fieldTwo.setDisable(true);
        game.addState(2, player);
        kiPlay();
    }
    @FXML
    protected void threePressed() {
        fieldThree.setText("O");
        fieldThree.setDisable(true);
        game.addState(3, player);
        kiPlay();
    }
    @FXML
    protected void fourPressed() {
        fieldFour.setText("O");
        fieldFour.setDisable(true);
        game.addState(4, player);
        kiPlay();
    }
    @FXML
    protected void fivePressed() {
        fieldFive.setText("O");
        fieldFive.setDisable(true);
        game.addState(5, player);
        kiPlay();
    }
    @FXML
    protected void sixPressed() {
        fieldSix.setText("O");
        fieldSix.setDisable(true);
        game.addState(6, player);
        kiPlay();
    }
    @FXML
    protected void sevenPressed() {
        fieldSeven.setText("O");
        fieldSeven.setDisable(true);
        game.addState(7, player);
        kiPlay();
    }
    @FXML
    protected void eightPressed() {
        fieldEight.setText("O");
        fieldEight.setDisable(true);
        game.addState(8, player);
        kiPlay();
    }
    @FXML
    protected void ninePressed() {
        fieldNine.setText("O");
        fieldNine.setDisable(true);
        game.addState(9, player);
        kiPlay();
    }
}