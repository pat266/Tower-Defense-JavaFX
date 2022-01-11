import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.junit.Assert;
import org.junit.Test;

import org.testfx.framework.junit.ApplicationTest;

import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.concurrent.TimeUnit;
import sample.*;

public class M5Test extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);
    }

    /**
     * Test money correctly increment over time on easy mode
     */
    @Test
    public void testMoneyGainEasy() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "505");
        TimeUnit.SECONDS.sleep(1);
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "510");
        TimeUnit.SECONDS.sleep(1);
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "515");

    }

    /**
     * Test money correctly increment over time on medium mode
     */
    @Test
    public void testMoneyGainMedium() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("MEDIUM");
        clickOn("Submit");
        clickOn("#startGame");

        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "404");
        TimeUnit.SECONDS.sleep(1);
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "408");
        TimeUnit.SECONDS.sleep(1);
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "412");
    }

    /**
     * Test money correctly increment over time on hard mode
     */
    @Test
    public void testMoneyGainHard() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");

        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "302");
        TimeUnit.SECONDS.sleep(1);
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "304");
        TimeUnit.SECONDS.sleep(1);
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "306");
    }

    /**
     * Test place tower on map
     */
    @Test
    public void testPlaceTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "505");
        clickOn("#tower0");
        clickOn("#towerPlacement0");
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "290");
    }

    /**
     * Test losing money when place tower
     */
    @Test
    public void testLoseMoneyPlaceRegularTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "505");
        clickOn("#tower0");
        clickOn("#towerPlacement2");
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "290");
    }

    /**
     * Test losing money when place tower (different price than regular tower)
     */
    @Test
    public void testLoseMoneyPlaceRapidTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "505");
        clickOn("#tower2");
        clickOn("#towerPlacement2");
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "280");
    }

    /**
     * Test killing enemy from long range tower (test range)
     */
    @Test
    public void testKillFromRangeTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        clickOn("#tower1");
        clickOn("#towerPlacement2");
        TimeUnit.SECONDS.sleep(7);
        Label label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "325");
        TimeUnit.SECONDS.sleep(2);
        // kill 1 second before the standard tower due to difference in range
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "345");
    }

    /**
     * Test killing enemy from long range tower (test range)
     */
    @Test
    public void testKillFromRapidTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        clickOn("#tower2");
        clickOn("#towerPlacement2");
        TimeUnit.SECONDS.sleep(7);
        Label label = lookup("#goldValue").query();
        // different initial gold value due to difference in tower price
        Assert.assertEquals(label.getText(), "315");
        TimeUnit.SECONDS.sleep(3);
        // kill 1 second after the long tower due to difference in range
        label = lookup("#goldValue").query();
        Assert.assertEquals(label.getText(), "340");
    }

    /**
     * Test error messages
     */
    @Test
    public void testPlaceTowerNoMoney() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        clickOn("#startGame");
        clickOn("#tower0");
        clickOn("#towerPlacement2");

        clickOn("#tower0");
        clickOn("#towerPlacement3");

        TimeUnit.SECONDS.sleep(1);
        verifyThat("Cannot go into debt. Tower Price > Available Fund", NodeMatchers.isVisible());
    }

    /**
     * Test to see if the monument health still reduces without tower
     */
    @Test
    public void testHealthWithoutTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        // initial health
        Label label = lookup("#healthValue").query();
        Assert.assertEquals(label.getText(), "500");
        TimeUnit.SECONDS.sleep(35);
        // health after 30 seconds
        label = lookup("#healthValue").query();
        Assert.assertEquals(label.getText(), "490");
    }

    /**
     * Test to see if the monument health still reduces with tower
     * Tower killed all enemies in easy mode
     */
    @Test
    public void testHealthWithTower() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        clickOn("#startGame");
        clickOn("#tower0");
        clickOn("#towerPlacement2");
        clickOn("#tower0");
        clickOn("#towerPlacement1");
        // initial health
        Label label = lookup("#healthValue").query();
        Assert.assertEquals(label.getText(), "500");
        TimeUnit.SECONDS.sleep(35);
        // health after 30 seconds (should decrease)
        label = lookup("#healthValue").query();
        Assert.assertEquals(label.getText(), "500");
    }
}
