package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UIResourcesController {
    @FXML
    private Label goldValue;

    @FXML
    private Label healthValue;

    @FXML
    private Label scoreValue;

    @FXML
    private Label waveValue;

    @FXML
    private Label timeValue;

    public void updateLabels(String goldValue, String healthValue,
                             String scoreValue, String waveValue, String timeValue) {
        this.goldValue.setText(goldValue);
        this.healthValue.setText(healthValue);
        this.scoreValue.setText(scoreValue);
        this.waveValue.setText(waveValue);
        this.timeValue.setText(timeValue);
    }

    public String getGoldValue() {
        return goldValue.getText();
    }

    public String getHealthValue() {
        return healthValue.getText();
    }

    public String getScoreValue() {
        return scoreValue.getText();
    }

    public String getWaveValue() {
        return waveValue.getText();
    }
}
