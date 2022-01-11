package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TowerPlacement extends Node {
    private Button towerPlacement;
    private boolean isClicked;
    private boolean isPlaced;
    private Tower tower; // store the current tower placed


    public TowerPlacement(int x, int y) {
        this.isClicked = false;
        this.isPlaced = false;
        this.towerPlacement = createPlaceholder(x, y);
    }
    public Button createPlaceholder(int x, int y) {
        //Creating a graphic (image)
        Image img = new Image("/img/placeHolder.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setFitWidth(50);
        view.setPreserveRatio(true);
        view.isPickOnBounds();
        //Creating a Button
        Button button = new Button();
        //Setting the location of the button
        button.setTranslateX(x);
        button.setTranslateY(y);
        //Setting the size of the button
        button.setPrefSize(50, 50);
        button.setManaged(false);
        //Setting a graphic to the button
        button.setGraphic(view);
        return button;
    }
    public Button getTowerPlacement() {
        return this.towerPlacement;
    }
    public boolean getIsClicked() {
        return this.isClicked;
    }
    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }
    public boolean getIsPlaced() {
        return this.isPlaced;
    }
    public void setIsPlaced(boolean isPlaced) {
        this.isPlaced = isPlaced;
    }
    public void setTowerImage(String urlImage) {
        //Creating a graphic (image)
        Image img = new Image(urlImage);
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setFitWidth(50);
        view.setPreserveRatio(true);
        view.isPickOnBounds();
        this.towerPlacement.setGraphic(view);
    }
    public void setX(int x) {
        this.towerPlacement.setTranslateX(x);
    }

    public void setY(int y) {
        this.towerPlacement.setTranslateY(y);
    }

    public int getX() {
        return (int) this.towerPlacement.getTranslateX();
    }

    public int getY() {
        return (int) this.towerPlacement.getTranslateY();
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }
}
