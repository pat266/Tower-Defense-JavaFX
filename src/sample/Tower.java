package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

import java.util.Stack;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Tower implements Cloneable {
    private String name;
    private double price;
    private String imgSrc;
    private String description;
    private Coordinate coordinate;
    private int attackRange;
    private int attack;
    private Image imageRepresentation;
    private Stack<Path> enemyPath;
    private LevelInformation levelInformation;
    private int level; // level to upgrade

    private double attackFrequency; // shot(s)/sec

    private final ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);
    private ScheduledFuture scheduledFuture;

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return (Tower) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Tower();
        }
    }

    public Tower() {
        attackRange = 150;
        attack = 10;
        attackFrequency = 1;
        enemyPath = new Stack<>();
        level = 1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPriceDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        switch (difficulty) {
        case EASY:
            this.price -= 20;
            break;
        case MEDIUM:
            this.price *= 1;
            break;
        default:
            // case HARD
            this.price += 20;
            break;
        }
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

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Image getImageRepresentation() {
        return imageRepresentation;
    }

    public void setImageRepresentation(Image imageRepresentation) {
        this.imageRepresentation = imageRepresentation;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public LevelInformation getLevelInformation() {
        return levelInformation;
    }

    public void setLevelInformation(LevelInformation levelInformation) {
        this.levelInformation = levelInformation;
    }

    public double getAttackFrequency() {
        return attackFrequency;
    }

    public void setAttackFrequency(double attackFrequency) {
        this.attackFrequency = attackFrequency;
    }

    /**
     * Stop checking for nearby enemy and generate bullet
     */
    public void cancelFindingEnemy() {
        scheduledFuture.cancel(true);
    }

    /**
     * Update every second, look for the nearest enemy
     * Only shoot one enemy
     */
    public void findNearbyEnemy() {
        // this is the task to constantly update
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //System.out.println(this);
                try {
                    // check declared
                    // System.out.println(levelInformation.getEnemyAlive().size());
                    ///**
                    int leftBoundTowerRange = getCoordinate().getX() - getAttackRange();
                    int rightBoundTowerRange = getCoordinate().getX() + getAttackRange();
                    int bottomBoundTowerRange = getCoordinate().getY() - getAttackRange();
                    int topBoundTowerRange = getCoordinate().getY() + getAttackRange();

                    ArrayList<Enemy> enemyArrayList = levelInformation.getEnemyAlive();
                    for (int i = 0; i < enemyArrayList.size(); i++) {
                        Enemy currentEnemy = enemyArrayList.get(i);
                        boolean firstCondition =
                                currentEnemy.getCoordinate().getX() >= leftBoundTowerRange;
                        boolean secondCondition =
                                currentEnemy.getCoordinate().getX() <= rightBoundTowerRange;
                        boolean thirdCondition =
                                currentEnemy.getCoordinate().getY() >= bottomBoundTowerRange;
                        boolean fourthCondition =
                                currentEnemy.getCoordinate().getY() <= topBoundTowerRange;

                        /**
                        if (i == 0) {
                            System.out.println("First condition: " + firstCondition);
                            System.out.println("Second condition: " + secondCondition);
                            System.out.printf("y: %d, leftBound: %d\n",
                                currentEnemy.getCoordinate().getY(), bottomBoundTowerRange);
                            System.out.println("Third condition: " + thirdCondition);
                            System.out.println("Fourth condition: " + fourthCondition);
                            System.out.println();
                        }

                        System.out.printf("Tower x: %d, Tower y: %d, Tower Range: %d\n",
                            getCoordinate().getX(), getCoordinate().getY(), getAttackRange());
                        System.out.printf("Enemy x: %d, Enemy y: %d\n",
                            currentEnemy.getCoordinate().getX(),
                            currentEnemy.getCoordinate().getY());
                        System.out.println();
                        /**

                         System.out.println("Printing from tower " + this);
                        System.out.println("Coordinate object:" + coordinate);
                        System.out.printf("(%d, %d)\n", coordinate.getX(), coordinate.getY());

                        if (i == 0) {
                            System.out.println("Enemy " + i);
                            System.out.printf("(%d, %d)\n", currentEnemy.getCoordinate().getX(),
                                currentEnemy.getCoordinate().getY());
                            // System.out.println("First condition: " + firstCondition);
                            System.out.printf("Enemy coordinate x: %d, leftBoundTowerRange: %d\n",
                                currentEnemy.getCoordinate().getX(), leftBoundTowerRange);
                            System.out.printf("Enemy coordinate x: %d, rightBoundTowerRange: %d\n",
                                currentEnemy.getCoordinate().getX(), rightBoundTowerRange);
                            // System.out.println("Second condition: " + secondCondition);
                            // System.out.println("Third condition: " + thirdCondition);
                            // System.out.println("Fourth condition: " + fourthCondition);
                            System.out.printf("Enemy coordinate y: %d, bottomBoundTowerRange: %d\n",
                                currentEnemy.getCoordinate().getY(), bottomBoundTowerRange);
                            System.out.printf("Enemy coordinate y: %d, topBoundTowerRange: %d\n",
                                currentEnemy.getCoordinate().getY(), topBoundTowerRange);
                            System.out.println();
                        } **/

                        // check to see if the enemy enters tower's range
                        if (firstCondition && secondCondition
                                && thirdCondition && fourthCondition) {
                            // System.out.println("Projectile created at y: "
                            // + getCoordinate().getY());
                            Projectile projectile = new Projectile(coordinate.getX(),
                                    coordinate.getY(), getAttack(), currentEnemy, Color.BLUE);

                            levelInformation.getProjectiles().push(projectile);
                            // only shoot one enemy
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An error has occurred in the schedule. " + e);
                }


            }
        };
        int delay = (int) (1000 / attackFrequency);
        scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(runnable, 0,
                delay, TimeUnit.MILLISECONDS);
    }

    public Stack<Path> getEnemyPath() {
        return enemyPath;
    }

    public void upgradeTower() {
        if (level + 1 > 3) {
            throw new IllegalStateException("The tower cannot be upgraded more than 2 times.");
        }
        // change the image source
        String oldSequence = "." + Integer.toString(level);
        level++;
        String newSequence = "." + Integer.toString(level);
        imgSrc = imgSrc.replace(oldSequence, newSequence);

        // update the image
        imageRepresentation = new Image(imgSrc);

        // upgrade the tower's stat
        attack *= 1.2;
        attackRange += 10;
    }
}
