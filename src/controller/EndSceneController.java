package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.LevelInformation;
import sample.Main;

public class EndSceneController {
    private Stage mainstage;

    @FXML
    private Button egame;

    @FXML
    private Label enemiesKilled;

    @FXML
    private Label goldEarned;

    @FXML
    private Label goldSpent;

    @FXML
    private Button rhome;

    @FXML
    private Label totalTime;

    @FXML
    private Label winOrLose;

    @FXML
    void exitGame(ActionEvent event) {
        mainstage.close();
        // System.exit(0);
    }

    @FXML
    void returnHome(ActionEvent event) throws Exception {
        Main m = new Main(mainstage);
        mainstage.setScene(m.createWelcomeScene());
        mainstage.show();
    }

    public void setValues(Stage stage, boolean won, LevelInformation levelInformation) {
        this.mainstage = stage;
        if (won) {
            this.winOrLose.setText("Congrats. You Won!");
        } else {
            this.winOrLose.setText("You Lost!");
        }
        this.enemiesKilled.setText(Integer.toString(levelInformation.getEnemiesKilled()));
        this.goldEarned.setText(Integer.toString(levelInformation.getGoldEarned()));
        this.goldSpent.setText(Integer.toString(levelInformation.getGoldSpent()));
        this.totalTime.setText(Integer.toString(levelInformation.getTimeSpent()));
    }
}