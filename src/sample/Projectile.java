package sample;

import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;

public class Projectile {


    private final int startX;   // Starting location of the projectile
    private final int startY;
    private final int endX;   // Ending location of the projectile
    private final int endY;
    private final int towerDamage;
    private final Enemy enemy;
    private final Color color;

    public Projectile(int towerX, int towerY, int towerDmg, Enemy enemy, Color color) {
        // super(towerX, towerY, 5, color);
        startX = towerX;
        startY = towerY;
        endX = enemy.getCoordinate().getX();
        endY = enemy.getCoordinate().getY();
        this.enemy = enemy;
        this.color = color;
        this.towerDamage = towerDmg;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Color getColor() {
        return color;
    }

    public int getTowerDamage() {
        return towerDamage;
    }
}
