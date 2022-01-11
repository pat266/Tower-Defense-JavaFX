package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.Tower;
import sample.ActionListener;

public class TowerController {
    private final String currency = "$";

    @FXML
    private ImageView towerImg;

    @FXML
    private Label towerName;

    @FXML
    private Label towerPrice;

    private ActionListener actionListener;

    private Tower tower;

    public void setTowerInfo(Tower tower, ActionListener actionListener) {
        this.tower = tower;
        this.actionListener = actionListener;
        this.towerName.setText(tower.getName());
        // remove the decimal places from the price
        this.towerPrice.setText(this.currency + Double.valueOf(tower.getPrice()).intValue());
        Image image = new Image(getClass().getResourceAsStream(tower.getImgSrc()));
        this.towerImg.setImage(image);
        this.towerImg.prefHeight(150);
        this.towerImg.prefWidth(150);
    }

    @FXML
    private void click(MouseEvent event) {
        actionListener.onClickListener(tower);
    }

    public ImageView getTowerImg() {
        return towerImg;
    }

}