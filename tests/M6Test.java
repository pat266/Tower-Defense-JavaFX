import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.concurrent.TimeUnit;
import sample.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class M6Test extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * Test if the tower can be upgraded
     * Tested in EASY mode
     */
    @Test
    public void testUpgradeTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        // place tower
        clickOn("#tower0");
        clickOn("#towerPlacement0");
        // upgrade the tower
        clickOn("#upgradeButton");
        clickOn("#towerPlacement0");
        Label label = lookup("#healthValue").query();
        Assert.assertEquals(label.getText(), "500");
    }

    /**
     * Test whether gold decreases after upgrading tower
     * Tested in EASY mode
     */
    @Test
    public void testMoneyReduceUpgrade() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");

        // place tower
        clickOn("#tower0");
        clickOn("#towerPlacement0");
        // test initial gold
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "290");
        // upgrade the tower (expect error)
        clickOn("#upgradeButton");
        clickOn("#towerPlacement0");
        // test gold after upgrading
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "195");
    }

    /**
     * Test whether the four statistics show up on the end game screen
     * Tested in HARD mode
     */
    @Test
    public void testEndScreenComponents() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        TimeUnit.SECONDS.sleep(70);
        // verify the 4 statistics are there
        verifyThat("Total Time (s)", NodeMatchers.isVisible());
        verifyThat("Gold Earned", NodeMatchers.isVisible());
        verifyThat("Gold Spent", NodeMatchers.isVisible());
        verifyThat("Enemies Killed", NodeMatchers.isVisible());
    }

    /**
     * Test whether the four statistics show up on the end game screen
     * Tested in EASY mode
     */
    @Test
    public void testWinScreen() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        // place tower
        clickOn("#tower0");
        clickOn("#towerPlacement1");
        clickOn("#tower0");
        clickOn("#towerPlacement2");
        // wait
        TimeUnit.SECONDS.sleep(86);
        // verify the winning message is there
        verifyThat("Congrats. You Won!", NodeMatchers.isVisible());
    }

    /**
     * Test whether the four statistics show up on the end game screen
     * Tested in HARD mode
     */
    @Test
    public void testLoseScreen() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        TimeUnit.SECONDS.sleep(70);
        // verify the losing message is there
        verifyThat("You Lost!", NodeMatchers.isVisible());
    }

    /**
     * Test whether the exit button works properly
     * Tested in HARD mode
     */
    @Test
    public void testExit() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        TimeUnit.SECONDS.sleep(70);
        clickOn("#egame");
    }

    /**
     * Test whether the restart button works properly
     * Tested in HARD mode
     */
    @Test
    public void testRestart() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        TimeUnit.SECONDS.sleep(70);
        clickOn("#rhome");
        // verify the losing message is there
        verifyThat("Welcome to the Game", NodeMatchers.isVisible());
    }

    /**
     * Test whether the stats are correct when the user does nothing
     * Tested in HARD mode
     */
    @Test
    public void testStatsDoingNothing() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        TimeUnit.SECONDS.sleep(70);
        // verify the amount of time
        Label totalTime = lookup("#totalTime").query();
        Assert.assertEquals(totalTime.getText(), "70");
        // verify the amount of gold earned
        Label goldEarned = lookup("#goldEarned").query();
        Assert.assertEquals(goldEarned.getText(), "140");
        // verify the amount of time
        Label goldSpent = lookup("#goldSpent").query();
        Assert.assertEquals(goldSpent.getText(), "0");
        // verify the amount of gold earned
        Label enemiesKilled = lookup("#enemiesKilled").query();
        Assert.assertEquals(enemiesKilled.getText(), "0");
    }

    /**
     * Test whether the stats are updated correctly when placing one tower
     * All of the values are compared to the value to testStatsDoingNothing()
     * Tested in HARD mode
     */
    @Test
    public void testStatsBuyingOneTowers() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        // initial gold
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "304");
        // place tower
        clickOn("#tower0");
        clickOn("#towerPlacement1");
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "44");
    }

    /**
     * Test the error message when there isn't enough money
     * Tested in HARD mode
     */
    @Test
    public void testUpgradeTowerFail() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");

        // place tower
        clickOn("#tower0");
        clickOn("#towerPlacement0");
        // upgrade the tower (expect error)
        clickOn("#upgradeButton");
        clickOn("#towerPlacement0");
        // check error message
        verifyThat("Available fund < upgrade cost", NodeMatchers.isVisible());
    }
}
