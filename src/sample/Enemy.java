package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    private double health;
    private Difficulty difficulty;
    private ImageView imageView;

    private Coordinate coordinate;
    private int attack; // damage the minion deals to the monument
    private int rewards; // give gold when defeated
    private boolean isBoss; // determine whether the enemy is a boss or not

    public Enemy(Difficulty difficulty) {
        this.imageView = new ImageView(new Image("/img/tempEnemy.png"));
        this.imageView.setFitHeight(60);
        this.imageView.setFitWidth(60);
        this.difficulty = difficulty;
        this.isBoss = false;
        setValues(difficulty);
        updateImage();
    }

    private void setValues(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        switch (difficulty) {
        case EASY:
            this.attack = 10;
            this.health = 10;
            this.rewards = 10;
            break;
        case MEDIUM:
            this.attack = 20;
            this.health = 20;
            this.rewards = 7;
            break;
        default:
            // case HARD
            this.attack = 130;
            this.health = 30;
            this.rewards = 5;
            break;
        }
    }

    public void setBossValues(int attack, int health, int rewards, Difficulty difficulty) {
        this.attack = attack;
        this.health = health;
        this.rewards = rewards;
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        switch (difficulty) {
            case EASY:
                this.attack *= 0.8;
                this.health *= 0.8;
                break;
            case MEDIUM:
                // do nothing in medium
                break;
            default:
                // case HARD
                this.attack *= 1.2;
                this.health *= 1.2;
                break;
        }
    }

    public void setHealth(int health) {
        this.health = (double) health;
        updateImage();
    }

    public void setDimension(int width, int height) {
        this.imageView.setFitHeight(width);
        this.imageView.setFitWidth(height);
    }

    public double getHealth() {
        return this.health;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void changeImgSource(String imgURL) {
        this.imageView.setImage(new Image(imgURL));
    }

    public void changeImageSize(double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public void setVisible(boolean visible) {
        this.imageView.setVisible(visible);
    }

    public int getAttack() {
        return this.attack;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setCoordinate(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    /**
     *
     * @param damage Amount of damage reduce from the tower's attack
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        updateImage();
    }

    /**
     * Update the image representation according to the health
     */
    private void updateImage() {
        if (!isBoss) {
            String replacingImageURL = null;
            if (((int) health) / 10 > 0) {
                replacingImageURL = "/img/level1/enemies/level1Enemy"
                        + Integer.toString(((int) health) / 10) + ".png";
            } else {
                replacingImageURL = "/img/tempEnemy.png";
            }
            Image replacingImage = new Image(replacingImageURL);
            this.imageView.setImage(replacingImage);
        }
    }

    public int getRewards() {
        return rewards;
    }
}
