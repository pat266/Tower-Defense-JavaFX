import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.junit.Assert;
import org.junit.Test;

import org.testfx.framework.junit.ApplicationTest;

import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.concurrent.TimeUnit;

import org.testfx.service.query.EmptyNodeQueryException;
import sample.*;

public class M4Test extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);

    }

    /*
        /**
         * Test if the ImageView disappears when it reaches the monument
         * while some are still there
         * /
        @Test
        public void testEnemyDisappear() throws InterruptedException {
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("EASY");
            clickOn("Submit");
            clickOn("#startGame");
            Thread.sleep(14000);
            verifyThat("#enemy1", Node::isVisible);
            Thread.sleep(21000);
            // test if the node is removed after it has reached the monument
            try {
                lookup("#enemy1").query();
                Assert.assertEquals("The enemy image view is still there", "wrong");
            } catch (EmptyNodeQueryException ex) {
                Assert.assertTrue(true);
            }
            // test enemy #10 to see if it has disappeared
            try {
                lookup("#enemy10").query();
                Assert.assertTrue(true);
            } catch (EmptyNodeQueryException ex) {
                Assert.assertEquals("The enemy image view is still there", "wrong");
            }
        }

        @Test
        public void testRestartGameScreen() throws InterruptedException {
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("HARD");
            clickOn("Submit");
            clickOn("#startGame");
            TimeUnit.SECONDS.sleep(35);
            verifyThat("You Lost :(", NodeMatchers.isNotNull());
            clickOn("Return to Home Screen");
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("HARD");
            clickOn("Submit");
            clickOn("#startGame");
            TimeUnit.SECONDS.sleep(35);
            verifyThat("You Lost :(", NodeMatchers.isNotNull());
            clickOn("Exit Game");
        }

        @Test
        public void testReachEndScreen() throws InterruptedException {
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("HARD");
            clickOn("Submit");
            clickOn("#startGame");
            TimeUnit.SECONDS.sleep(35);
            verifyThat("You Lost :(", NodeMatchers.isNotNull());
        }

        @Test
        public void testExitGame() throws InterruptedException {
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("HARD");
            clickOn("Submit");
            clickOn("#startGame");
            TimeUnit.SECONDS.sleep(35);
            clickOn("Exit Game");
        }

        /**
         * Test to see if the Game Labels are there
         * /
        @Test
        public void testGameLabels() {
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("HARD");
            clickOn("Submit");
            clickOn("#startGame");
            verifyThat("Gold: ", NodeMatchers.isNotNull());
            verifyThat("Monument Health:", NodeMatchers.isNotNull());
            verifyThat("Wave: ", NodeMatchers.isNotNull());
            verifyThat("Score: ", NodeMatchers.isNotNull());
        }

        /**
         * Test to see if the wave value increases
         * /
        @Test
        public void testWaveIncrement() throws InterruptedException {
            clickOn("Start");
            clickOn("#playerName").write("Player Name");
            clickOn("#diffChoice").clickOn("MEDIUM");
            clickOn("Submit");
            clickOn("#startGame");
            verifyThat("Wave: ", NodeMatchers.isNotNull());
            verifyThat("0", NodeMatchers.isNotNull());
            TimeUnit.SECONDS.sleep(20);
            verifyThat("1", NodeMatchers.isNotNull());
        }
    */

    /**
     * Test to see if the health decreases when the enemy touches monument
     * Test if the health remains the same mid-way (16 seconds)
     */
    @Test
    public void testHealthDecrement() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("MEDIUM");
        clickOn("Submit");
        clickOn("#startGame");
        verifyThat("Monument Health:", NodeMatchers.isNotNull());
        verifyThat("400", NodeMatchers.isNotNull());
        TimeUnit.SECONDS.sleep(16);
        verifyThat("400", NodeMatchers.isNotNull());
        TimeUnit.SECONDS.sleep(16);
        Label label = lookup("#healthValue").query();
        Assert.assertEquals(label.getText(), "380");
    }

    /**
     * Test to see if the enemy appears after the first wave starts spawning
     */
    @Test
    public void testEnemySpawn() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name"); // the TextField with ID of 'playerName'
        clickOn("#diffChoice").clickOn("MEDIUM"); // the ComboBox with ID of 'diffChoice'
        clickOn("Submit"); // a button with value 'Submit'
        clickOn("#startGame");
        Thread.sleep(14000);
        verifyThat("#enemy1", Node::isVisible);
    }

    /**
     * Test the first 10 enemies to see if they are not visible
     */
    @Test
    public void testEnemyNotSpawn() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name"); // the TextField with ID of 'playerName'
        clickOn("#diffChoice").clickOn("MEDIUM"); // the ComboBox with ID of 'diffChoice'
        clickOn("Submit"); // a button with value 'Submit'
        clickOn("#startGame");
        for (int i = 0; i < 10; i++) {
            Assert.assertFalse(lookup("#enemy" + i).query().isVisible());
        }
    }
    /**
     * Test to ensure the Start Combat button appears
     */
    @Test
    public void testStartCombatButton() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("MEDIUM");
        clickOn("Submit");
        verifyThat("#startGame", NodeMatchers.isNotNull());
    }
    /**
     * Never Start Game
     */
    @Test
    public void testNotStartGame() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("MEDIUM");
        clickOn("Submit");
        Thread.sleep(14000);
        try {
            lookup("#enemy1").query();
            Assert.assertEquals("The enemy image view is still there", "wrong");
        } catch (EmptyNodeQueryException ex) {
            Assert.assertTrue(true);
        }
    }
    /**
     * Test Start Game
     */
    @Test
    public void testStartGame() throws InterruptedException {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("MEDIUM");
        clickOn("Submit");
        clickOn("#startGame");
        Thread.sleep(14000);
        // test enemy #10 to see if it has disappeared
        try {
            lookup("#enemy10").query();
            Assert.assertTrue(true);
        } catch (EmptyNodeQueryException ex) {
            Assert.assertEquals("The enemy image view is still there", "wrong");
        }
    }
}
