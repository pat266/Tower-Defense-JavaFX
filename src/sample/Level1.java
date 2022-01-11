package sample;

import javafx.scene.shape.*;

import java.util.ArrayList;

public class Level1 {
    private static final int NUMTOWERS = 5;
    public static Path createEnemyPath() {
        Path path = new Path();
        path.getElements().add(new MoveTo(8, 384));
        path.getElements().add(new HLineTo(300));
        path.getElements().add(new VLineTo(180));
        path.getElements().add(new HLineTo(190));
        path.getElements().add(new VLineTo(60));
        path.getElements().add(new HLineTo(690));
        path.getElements().add(new VLineTo(202));
        path.getElements().add(new HLineTo(500));
        path.getElements().add(new VLineTo(350));
        path.getElements().add(new HLineTo(710));
        path.getElements().add(new VLineTo(500));
        path.getElements().add(new HLineTo(190));
        path.getElements().add(new VLineTo(725));
        path.getElements().add(new HLineTo(395));
        path.getElements().add(new VLineTo(580));
        path.getElements().add(new HLineTo(590));
        path.getElements().add(new VLineTo(680));
        return path;
    }
    public static int getNumTower() {
        return NUMTOWERS;
    }

    public static ArrayList<ArrayList<Enemy>> createEnemyWaves(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        // first wave
        Enemy enemy1;
        // second wave
        Enemy enemy2;
        Enemy enemy3;
        // third wave
        Enemy enemy4;
        Enemy enemy5;
        Enemy enemy6;

        // final wave
        Enemy finalBoss = new Enemy(difficulty);
        finalBoss.setBoss(true);
        finalBoss.setBossValues(200, 200, 10, difficulty);
        finalBoss.changeImgSource("/img/boss.png");
        finalBoss.setDimension(100, 100);

        switch (difficulty) {
        case EASY:
            // first wave
            enemy1 = new Enemy(Difficulty.EASY);
            // second wave
            enemy2 = new Enemy(Difficulty.EASY);
            enemy3 = new Enemy(Difficulty.MEDIUM);
            // third wave
            enemy4 = new Enemy(Difficulty.EASY);
            enemy5 = new Enemy(Difficulty.MEDIUM);
            enemy6 = new Enemy(Difficulty.HARD);
            break;
        case MEDIUM:
            // first wave
            enemy1 = new Enemy(Difficulty.MEDIUM);
            // second wave
            enemy2 = new Enemy(Difficulty.HARD);
            enemy3 = new Enemy(Difficulty.MEDIUM);
            // third wave
            enemy4 = new Enemy(Difficulty.MEDIUM);
            enemy5 = new Enemy(Difficulty.MEDIUM);
            enemy6 = new Enemy(Difficulty.HARD);
            break;
        default:
            // HARD
            // first wave
            enemy1 = new Enemy(Difficulty.MEDIUM);
            // second wave
            enemy2 = new Enemy(Difficulty.EASY);
            enemy3 = new Enemy(Difficulty.HARD);
            // third wave
            enemy4 = new Enemy(Difficulty.HARD);
            enemy5 = new Enemy(Difficulty.HARD);
            enemy6 = new Enemy(Difficulty.HARD);
            break;
        }
        ArrayList<ArrayList<Enemy>> waves = new ArrayList<>();
        // first wave
        ArrayList<Enemy> firstWave = new ArrayList<>();
        firstWave.add(enemy1);

        // second wave
        ArrayList<Enemy> secondWave = new ArrayList<>();
        secondWave.add(enemy2);
        secondWave.add(enemy3);

        // third wave
        ArrayList<Enemy> thirdWave = new ArrayList<>();
        thirdWave.add(enemy4);
        thirdWave.add(enemy5);
        thirdWave.add(enemy6);

        // third wave
        ArrayList<Enemy> finalWave = new ArrayList<>();
        finalWave.add(finalBoss);

        // add all waves to the ArrayList
        waves.add(firstWave);
        waves.add(secondWave);
        waves.add(thirdWave);
        waves.add(finalWave
        );
        return waves;
    }

    public static ArrayList<Tower> generateTowers(Difficulty difficulty) {
        ArrayList<Tower> towers = new ArrayList<>();
        // set a Tower for long range attack (range of 250 instead of 150)
        Tower regularTower = new Tower();
        regularTower.setName("Regular Tower");
        regularTower.setPrice(240);
        regularTower.setPriceDifficulty(difficulty);
        regularTower.setAttackRange(150);
        regularTower.setImgSrc("/img/level1/towers/1.1.png");
        towers.add(regularTower);

        // set a Tower for long range attack (range of 250 instead of 150)
        Tower longTower = new Tower();
        longTower.setName("Long Tower");
        longTower.setPrice(240);
        longTower.setPriceDifficulty(difficulty);
        longTower.setAttackRange(250);
        longTower.setAttackFrequency(0.8);
        longTower.setImgSrc("/img/level1/towers/2.1.png");
        towers.add(longTower);

        // set a tower for rapid attack
        // (shoot 2.5 shot/sec instead of 1 shot/sec and reduce the attack from 10 to 5)
        Tower rapidTower = new Tower();
        rapidTower.setName("Rapid Tower");
        rapidTower.setPrice(250);
        rapidTower.setPriceDifficulty(difficulty);
        rapidTower.setAttackFrequency(2.5);
        rapidTower.setAttack(5);
        rapidTower.setAttackRange(140);
        rapidTower.setImgSrc("/img/level1/towers/3.1.png");
        towers.add(rapidTower);


        // set a tower for big attack, long range, and slow fire rate
        Tower cannonTower = new Tower();
        cannonTower.setName("Canon Tower");
        cannonTower.setPrice(280);
        cannonTower.setPriceDifficulty(difficulty);
        cannonTower.setAttackFrequency(0.4);
        cannonTower.setAttack(25);
        cannonTower.setAttackRange(300);
        cannonTower.setImgSrc("/img/level1/towers/4.1.png");
        towers.add(cannonTower);

        return towers;
    }
}
