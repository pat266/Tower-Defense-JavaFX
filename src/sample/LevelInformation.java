package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A class to store the information regarding the game state.
 */
public class LevelInformation implements Serializable {
    private static LevelInformation levelInformation;
    // constant to describe state of the game
    // game is active
    public static final int IS_RUNNING = 1;
    // game is temporarily not active
    public static final int IS_PAUSED = 0;
    // game is over
    public static final int IS_OVER = -1;

    // list of tower objects on the map
    private ArrayList<Tower> towers;
    // list of enemy objects that are alive and on the map
    private ArrayList<Enemy> enemyAlive;

    // list of enemy objects that are alive and on the map
    private Stack<Projectile> projectiles;

    // store status of the game, if its running, pause, or quit
    private int status;
    // resources that the player has
    private int gold;
    // current wave that are being spawned
    private int wave;
    // health from the Monument object
    private int monumentHealth;
    // kill points + perfect level points
    private int score;
    // current timer
    private int time;
    // current enemyIndex
    private int enemyIndex;
    // the cost of upgrade a tower
    private int upgradeCost;
    // check to see if the screen has already been transitioned
    private boolean toEndGame;

    // total number of enemies killed
    private int enemiesKilled;
    // total amount of gold earned
    private int goldEarned;
    // total gold spent
    private int goldSpent;
    // total time spent in game
    private int timeSpent;



    // set it to private so that we have to use it for class object
    public LevelInformation(Monument monument, Money money) {
        this.status = IS_PAUSED;
        this.gold = (int) money.getBalance();
        this.wave = 0;
        this.monumentHealth = monument.getHealth();
        this.score = 0;
        this.towers = new ArrayList<>();
        this.enemyAlive = new ArrayList<>();
        this.projectiles = new Stack<>();
        this.time = 0;
        this.enemiesKilled = 0;
        this.goldEarned = 0;
        this.goldSpent = 0;
        this.timeSpent = 0;
        this.upgradeCost = 0;
        this.toEndGame = false;
    }

    /**
     * Getter for LevelInformation
     * @return LevelInformation object containing monument's health and player's money
     */
    public LevelInformation getLevelInformation() {
        if (levelInformation == null) {
            throw new NullPointerException("LevelInformation object has not been created.");
        }
        return levelInformation;
    }

    /**
     * The following methods are setters and getters methods
     * @return information requested by getters including ArrayList of Tower, ArrayList of Enemy,
     * int, and boolean
     */



    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void addTower(Tower tower) {
        this.towers.add(tower);
    }

    public void removeTower(Tower tower) {
        this.towers.remove(tower);
    }

    public ArrayList<Enemy> getEnemyAlive() {
        return enemyAlive;
    }

    public void addEnemy(Enemy enemy) {
        this.enemyAlive.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        this.enemyAlive.remove(enemy);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getMonumentHealth() {
        return monumentHealth;
    }

    public void deductMonumentHealth(int monumentHealth) {
        this.monumentHealth -= monumentHealth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isRunning() {
        return this.status == IS_RUNNING;
    }

    public boolean isPaused() {
        return this.status == IS_PAUSED;
    }

    public boolean isOver() {
        return this.status == IS_OVER;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEnemyIndex() {
        return enemyIndex;
    }

    public void setEnemyIndex(int enemyIndex) {
        this.enemyIndex = enemyIndex;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(int goldEarned) {
        this.goldEarned = goldEarned;
    }

    public int getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(int goldSpent) {
        this.goldSpent = goldSpent;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public boolean isToEndGame() {
        return toEndGame;
    }

    public void setToEndGame(boolean toEndGame) {
        this.toEndGame = toEndGame;
    }

    public void increaseGoldFromDiff(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        switch (difficulty) {
        case EASY:
            this.gold += 5;
            this.goldEarned += 5;
            break;
        case MEDIUM:
            this.gold += 4;
            this.goldEarned += 4;
            break;
        default:
            // case HARD
            this.gold += 2;
            this.goldEarned += 2;
            break;
        }
    }

    public Stack<Projectile> getProjectiles() {
        return projectiles;
    }
}
