package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
//import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;

//import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import sample.Monument;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.*;

public class MarketController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;


    // testing FXML variable
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Path path;

    @FXML
    private Button startGame;

    @FXML
    private Button upgradeButton;

    private List<Tower> towers = null;
    private ActionListener actionListener;
    private Tower clickedTower = null;
    private Money balance;
    private Difficulty difficulty;
    private Monument monument;
    private Stage mainStage;
    private boolean isUpgrading; // boolean variable to know if it is upgrading or not

    private ArrayList<PathTransition> enemiesPathTransition;

    private AnimationTimer gameLoop; // keep track of the time in the game
    private LevelInformation level1Info; // keep track of the first level information
    private UIResourcesController uiResourcesController; // keep track of the resources and info
    private final Object pause = new Object();

    @FXML
    void subHealthQuit(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/endscene.fxml"));
            Parent root = loader.load();
            //loader.load();
            EndSceneController endSceneController = loader.getController();
            endSceneController.setValues(mainStage, false, level1Info);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void startTheGame(MouseEvent event) {
        startGame.setVisible(false);
        startGame.setDisable(true);
        level1Info.setStatus(LevelInformation.IS_RUNNING);
        Platform.exitNestedEventLoop(pause, null);
    }

    @FXML
    void clickedUpgrade(MouseEvent event) {
        clickedTower = null;
        if (!isUpgrading) {
            isUpgrading = true;
            Image customCursor = new Image("/img/upgradeIcon.png");
            mainStage.getScene().setCursor(new ImageCursor(customCursor));
        } else {
            isUpgrading = false;
            mainStage.getScene().setCursor(Cursor.DEFAULT);
        }

    }

    public Stage getMainStage() {
        return mainStage;
    }

    public UIResourcesController getUiResourcesController() {
        return uiResourcesController;
    }

    public MarketController() {
        // this.towers = Level1.generateTowers(Difficulty.EASY);
        this.towers = generateFiveSampleTowers();
    }

    public String getTowerName(int x) {
        return towers.get(x).getName();
    }

    public String getTowerDescription(int x) {
        return towers.get(x).getDescription();
    }

    private void setClickedTower(Tower tower) {
        this.clickedTower = tower;
    }

    public void setValues(Money balance, Difficulty difficulty, Stage stage) {
        this.balance = balance;
        this.difficulty = difficulty;
        this.monument = new Monument();
        this.monument.setHealthFromDifficulty(difficulty);
        this.mainStage = stage;
        for (int i = 0; i < this.towers.size(); i++) {
            towers.get(i).setPriceDifficulty(difficulty);
        }
        level1Info = new LevelInformation(this.monument, this.balance);
        setUpgradeCost(difficulty);
        this.upgradeButton.setText(this.upgradeButton.getText() +
                " (" + level1Info.getUpgradeCost() + ")");
    }

    private void setUpgradeCost(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        switch (difficulty) {
        case EASY:
            level1Info.setUpgradeCost(100);
            break;
        case MEDIUM:
            level1Info.setUpgradeCost(120);
            break;
        default:
            // case HARD
            level1Info.setUpgradeCost(150);
            break;
        }
    }

    /**
     * generate 5 sample towers for the shop
     * @return a list of towers
     */
    private ArrayList<Tower> generateFiveSampleTowers() {
        ArrayList<Tower> towers = new ArrayList<>();
        Tower tower;
        for (int i = 0; i <= 4; i++) {
            tower = new Tower();
            tower.setAttackRange(150);
            tower.setName("Sample" + i);
            tower.setDescription("Sample" + i + " throws things");
            tower.setPrice(250 + i);
            tower.setAttackFrequency(1);
            String urlImg = "/img/level1/towers" + (i + 1) + ".png";
            tower.setImgSrc(urlImg);
            towers.add(tower);
        }
        return towers;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                this.towers = Level1.generateTowers(difficulty);

                Platform.enterNestedEventLoop(pause);

                // load the UI
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/fxml/UIResources.fxml"));
                Node gameUI = loader.load();
                this.anchorPane.getChildren().add(gameUI);
                uiResourcesController = loader.getController();
                actionListener = new ActionListener() {
                    @Override
                    public void onClickListener(Tower tower) {
                        isUpgrading = false;
                        mainStage.getScene().setCursor(Cursor.DEFAULT);
                        setClickedTower(tower);
                    }
                };

                for (int i = 0; i < this.towers.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/tower.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    TowerController towerController = fxmlLoader.getController();
                    towerController.setTowerInfo(towers.get(i), actionListener);
                    //towerController
                    grid.addRow(i, anchorPane);
                    towerController.getTowerImg().setId("tower" + i);
                    GridPane.setMargin(anchorPane, new Insets(5));
                }

                initializeTowerPlacement();
                int[] enemyWave = {1, 2, 3};
                startGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void initializeTowerPlacement() {
        // Changing the image when mouse hovering over it
        TowerPlacement[] towerPlacement = new TowerPlacement[Level1.getNumTower()];
        int[] xCor = {200, 375, 375, 475, 655};
        int[] yCor = {250, 250, 400, 640, 575};
        if (xCor.length != towerPlacement.length) {
            throw new IllegalStateException("The number of TowerPlacement"
                    + " does not match # of x_coordinate");
        } else if (xCor.length != yCor.length) {
            throw new IllegalStateException("The # of x_coordinate"
                    + " does not match # of y_coordinate");
        }
        for (int i = 0; i < towerPlacement.length; i++) {
            towerPlacement[i] = new TowerPlacement(xCor[i], yCor[i]);
            // set id for the TestFX
            towerPlacement[i].getTowerPlacement().setId("towerPlacement" + i);
            final TowerPlacement myPlacement = towerPlacement[i];
            myPlacement.getTowerPlacement().setOnMouseEntered(e -> {
                if (clickedTower != null && !myPlacement.getIsPlaced()) {
                    myPlacement.setTowerImage(clickedTower.getImgSrc());
                }
            });
            myPlacement.getTowerPlacement().setOnMouseExited(e -> {
                if (!myPlacement.getIsClicked()) {
                    myPlacement.setTowerImage("/img/placeHolder.png");
                }
            });
            myPlacement.getTowerPlacement().setOnMouseClicked(e -> {
                if (isUpgrading && myPlacement.getIsPlaced()) {
                    if (level1Info.getGold() < level1Info.getUpgradeCost()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Not enough fund");
                        alert.setContentText("Available fund < upgrade cost");
                        alert.showAndWait();
                    } else {
                        try {
                            myPlacement.getTower().upgradeTower();
                            myPlacement.setTowerImage(myPlacement.getTower().getImgSrc());
                            this.level1Info.setGold(this.level1Info.getGold() - level1Info.getUpgradeCost());
                            // record the number of gold spent from buying/upgrading towers
                            level1Info.setGoldSpent(level1Info.getGoldSpent() + level1Info.getUpgradeCost());
                        } catch (IllegalStateException illegalStateException) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("A tower cannot be upgraded more than twice.");
                            alert.setContentText("Cannot be upgraded any more.");
                            alert.showAndWait();
                        }
                    }
                } else {
                    if (clickedTower != null
                            && this.level1Info.getGold() < clickedTower.getPrice()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Cannot go in debt.");
                        alert.setContentText("Cannot go into debt. Tower Price > Available Fund");
                        alert.showAndWait();
                    } else if (myPlacement.getIsPlaced()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cannot Place Tower");
                        alert.setContentText("A tower is already placed here");
                        alert.showAndWait();
                    } else {
                        if (clickedTower != null) {
                            myPlacement.setIsClicked(true);
                            myPlacement.setIsPlaced(true);
                            // System.out.println(myPlacement.getX() + " " + myPlacement.getY());
                            // System.out.println(level1Info.getLevelInformation());
                            int x = myPlacement.getX();
                            int y = myPlacement.getY();

                            Tower cloneTower = null;
                            try {
                                cloneTower = (Tower) clickedTower.clone();
                            } catch (CloneNotSupportedException cloneNotSupportedException) {
                                cloneNotSupportedException.printStackTrace();
                            }
                            // cloneTower = clickedTower;
                            cloneTower.setCoordinate(new Coordinate(x, y));
                            cloneTower.setLevelInformation(level1Info);
                            cloneTower.findNearbyEnemy();
                            // add the clicked tower to the ArrayList
                            level1Info.getTowers().add(cloneTower);
                            // add tower to the TowerPlacement
                            myPlacement.setTower(cloneTower);
                            // set the image
                            myPlacement.setTowerImage(cloneTower.getImgSrc());
                            this.level1Info.setGold(this.level1Info.getGold()
                                    - (int) cloneTower.getPrice());
                            // record the number of gold spent from buying/upgrading towers
                            level1Info.setGoldSpent(level1Info.getGoldSpent() + (int) cloneTower.getPrice());
                            this.clickedTower = null;
                        }
                    }
                }
            });
            this.anchorPane.getChildren().add(myPlacement.getTowerPlacement());
        }
    }

    private PathTransition newPathTransitionTo(Path path, Enemy enemy, int duration) {
        PathTransition transition = new PathTransition();
        transition.setPath(path);
        transition.setNode(enemy.getImageView());
        transition.setDuration(Duration.seconds(duration));
        return transition;
    }

    /**
     * Update the labels in FXML file UIResources
     */
    private void updateLabels() {
        uiResourcesController.updateLabels(
                Integer.toString(level1Info.getGold()),
                Integer.toString(level1Info.getMonumentHealth()),
                Integer.toString(level1Info.getScore()),
                Integer.toString(level1Info.getWave()),
                Integer.toString(level1Info.getTime())
        );
    }

    /**
     * Update enemies' positions
     */
    private void updateBattleLocation() {
        // update the coordinate of the enemies in the map
        for (int i = 0; i < level1Info.getEnemyAlive().size(); i++) {
            int x = (int) level1Info.getEnemyAlive().get(i).getImageView().getTranslateX();
            int y = (int) level1Info.getEnemyAlive().get(i).getImageView().getTranslateY();
            level1Info.getEnemyAlive().get(i).setCoordinate(x, y);
        }
    }

    /**
     * Create projectiles
     */
    private void createProjectile() {
        // create the projectile (PathTransition) and reduce enemy's health appropriately
        if (level1Info.getProjectiles().size() > 0) {
            Projectile currentProjectile = level1Info.getProjectiles().pop();
            Circle circle = new Circle(5, currentProjectile.getColor());
            if (!anchorPane.getChildren().contains(circle)) {
                Path path = new Path();
                path.getElements().add(new MoveTo(currentProjectile.getStartX(),
                        currentProjectile.getStartY()));
                path.getElements().add(new LineTo(currentProjectile.getEndX(),
                        currentProjectile.getEndY()));
                anchorPane.getChildren().add(circle);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setPath(path);
                pathTransition.setNode(circle);
                pathTransition.setDuration(Duration.millis(500));
                pathTransition.play();

                pathTransition.setOnFinished(e -> {
                    circle.setVisible(false);
                    currentProjectile.getEnemy().takeDamage(currentProjectile.getTowerDamage());
                    if (currentProjectile.getEnemy().getHealth() <= 0) {
                        // increment the number of enemies killed
                        level1Info.setEnemiesKilled(level1Info.getEnemiesKilled() + 1);
                        // stop the path transition
                        for (int i = 0; i < enemiesPathTransition.size(); i++) {
                            if (currentProjectile.getEnemy().getImageView().equals(
                                    enemiesPathTransition.get(i).getNode())) {
                                enemiesPathTransition.get(i).stop();
                                enemiesPathTransition.remove(i);
                            }
                        }
                        // delete the current enemy
                        currentProjectile.getEnemy().getImageView().setVisible(false);
                        level1Info.getEnemyAlive().remove(currentProjectile.getEnemy());
                        level1Info.setGold(level1Info.getGold()
                                + currentProjectile.getEnemy().getRewards());
                        level1Info.setGoldEarned(level1Info.getGoldEarned()
                                + currentProjectile.getEnemy().getRewards());
                    }
                });
            }
        }
    }

    /**
     * Go to the EndSceneController. Set Win or Lose.
     * @param won determine whether the game was won or not
     */
    private void transitionEndScene(boolean won) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/endscene.fxml"));
            Parent root = loader.load();
            EndSceneController endSceneController = loader.getController();
            endSceneController.setValues(mainStage, won, level1Info);
            this.gameLoop.stop();
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Release a new wave whenever the wave is clear
     */
    private void startGame() {
        // store the second value in the animation timer
        LongProperty second = new SimpleLongProperty(0);
        // store the tenth_second value in the animation timer
        // used for updating the movement of the ImageView
        LongProperty tenthSecond = new SimpleLongProperty(0);
        long conversionSecond = 1000000000;
        long conversion10ThSecond = 100000000;
        int initialWait = 5; // the initial wait
        final int waitDuration = 15; // 15 seconds wait time between waves
        level1Info.setStatus(LevelInformation.IS_RUNNING);
        level1Info.setTime(initialWait);
        ArrayList<ArrayList<Enemy>> enemyWaves = Level1.createEnemyWaves(difficulty);
        level1Info.setWave(0);
        level1Info.setEnemyIndex(0);
        enemiesPathTransition = new ArrayList<>();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // update every second
                if (second.get() != currentNanoTime / conversionSecond) {
                    // increase the gold value over time based on difficulty level
                    level1Info.increaseGoldFromDiff(difficulty);
                    // start spawning enemies
                    if (level1Info.getTime() == 0) {
                        int enemyIndex = level1Info.getEnemyIndex();
                        int waveNum = level1Info.getWave();
                        if (waveNum < enemyWaves.size()) {
                            // spawn all enemies in the current wave ArrayList
                            if (enemyIndex >= enemyWaves.get(waveNum).size()) {
                                // reset the enemy index
                                level1Info.setEnemyIndex(0);
                                // increment the wave number
                                level1Info.setWave(level1Info.getWave() + 1);
                                if (level1Info.getWave() < enemyWaves.size()) {
                                    level1Info.setTime(waitDuration);
                                }
                            } else {
                                Enemy currEnemy = enemyWaves.get(waveNum).get(enemyIndex);
                                currEnemy.getImageView().setVisible(false);
                                anchorPane.getChildren().add(currEnemy.getImageView());
                                level1Info.getEnemyAlive().add(currEnemy);
                                // create a path transition
                                PathTransition currTransition = newPathTransitionTo(
                                        Level1.createEnemyPath(), currEnemy, 30);
                                currTransition.setOnFinished(e -> {
                                    level1Info.deductMonumentHealth(currEnemy.getAttack());
                                    level1Info.getEnemyAlive().remove(currEnemy);
                                    currEnemy.setVisible(false);
                                    anchorPane.getChildren().remove(currEnemy.getImageView());
                                    // end the game
                                    if (level1Info.getMonumentHealth() <= 0) {
                                        gameLoop.stop();
                                        if (!level1Info.isToEndGame()) {
                                            level1Info.setToEndGame(true);
                                            transitionEndScene(false);
                                            level1Info.setStatus(LevelInformation.IS_OVER);
                                        }
                                    }
                                });
                                currEnemy.getImageView().setVisible(true);
                                currTransition.play();
                                enemiesPathTransition.add(currTransition);
                                // increase the enemy index
                                level1Info.setEnemyIndex(enemyIndex + 1);
                            }
                        }
                    }
                    // increment the total time
                    level1Info.setTimeSpent(level1Info.getTimeSpent() + 1);
                    // decrease the wait duration
                    if (level1Info.getTime() - 1 < 0) {
                        level1Info.setTime(0);
                    } else {
                        level1Info.setTime(level1Info.getTime() - 1);
                    }
                    // check for Win: if the wave reaches criteria and no more enemies on map
                    if (level1Info.getWave() >= enemyWaves.size() && level1Info.getEnemyAlive().size() == 0) {
                        if (!level1Info.isToEndGame()) {
                            level1Info.setToEndGame(true);
                            transitionEndScene(true);
                            level1Info.setStatus(LevelInformation.IS_OVER);
                        }
                    }
                }
                // update the game state every tenth of a second
                if (tenthSecond.get() != currentNanoTime / conversionSecond) {
                    updateBattleLocation();
                    createProjectile();
                    updateLabels();
                }
                second.set(currentNanoTime / conversionSecond);
                tenthSecond.set(currentNanoTime / conversion10ThSecond);
            }
        };
        this.gameLoop = timer;
        this.gameLoop.start();
    }
}
