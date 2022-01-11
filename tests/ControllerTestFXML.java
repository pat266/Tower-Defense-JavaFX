import controller.MarketController;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

//import org.junit.After;
import org.junit.Test;

//import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
//import static org.testfx.assertions.api.Assertions.assertThat;


import sample.*;

public class ControllerTestFXML extends ApplicationTest {
    @Test
    public void testMonument() {
        Monument monument1 = new Monument();
        monument1.setHealthFromDifficulty(Difficulty.EASY);
        assertEquals(monument1.getHealth(), 500);
        Monument monument2 = new Monument();
        monument2.setHealthFromDifficulty(Difficulty.MEDIUM);
        assertEquals(monument2.getHealth(), 400);
        Monument monument3 = new Monument();
        monument3.setHealthFromDifficulty(Difficulty.HARD);
        assertEquals(monument3.getHealth(), 300);
    }

    @Test
    public void testTower() {
        Tower tower1 = new Tower();
        tower1.setName("Water Elemental");
        tower1.setPrice(250);
        assertEquals(tower1.getName(), "Water Elemental");
        assertEquals(250, tower1.getPrice(), 0);
        Tower tower2 = new Tower();
        tower2.setName("Earth Elemental");
        tower2.setPrice(300);
        assertEquals(tower2.getName(), "Earth Elemental");
        assertEquals(300, tower2.getPrice(), 0);
        Tower tower3 = new Tower();
        tower3.setName("Fire Elemental");
        tower3.setPrice(200);
        assertEquals(tower3.getName(), "Fire Elemental");
        assertEquals(200, tower3.getPrice(), 0);
        Tower tower4 = new Tower();
        tower4.setName("Air Elemental");
        tower4.setPrice(400);
        assertEquals(tower4.getName(), "Air Elemental");
        assertEquals(400, tower4.getPrice(), 0);
    }

    @Test
    public void moneyGoesDown() {
        Money m = new Money();
        MarketController mc = new MarketController();
        Tower t = new Tower();
        t.setPrice(200);
        m.set(400);
        double moneyBefore = m.getBalance();
        // removed this method
        // mc.setClickedOn1(true);
        m.subtractBalance(t.getPrice());
        assertTrue(moneyBefore > m.getBalance());

    }

    @Test
    public void testHealthDown() {
        Monument mu = new Monument();
        mu.setHealthFromDifficulty(Difficulty.MEDIUM);
        mu.subtractHealth(100);
        assertEquals(mu.getHealth(), 300);

        Monument mu2 = new Monument();
        mu2.setHealthFromDifficulty(Difficulty.EASY);
        mu2.subtractHealth(100);
        assertEquals(mu2.getHealth(), 400);

        Monument mu3 = new Monument();
        mu3.setHealthFromDifficulty(Difficulty.HARD);
        mu3.subtractHealth(100);
        assertEquals(mu3.getHealth(), 200);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);
    }

    @Test
    public void testGameScreenMoney() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        verifyThat("Amount", NodeMatchers.isNotNull());
        verifyThat("300", NodeMatchers.isNotNull());
    }

    @Test
    public void testMarket() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        verifyThat("Tower Market", NodeMatchers.isNotNull());
    }

    @Test
    public void testGameScreenMarket() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        verifyThat("Tower Market", NodeMatchers.isNotNull());
    }

    @Test
    public void testName() {
        MarketController mc = new MarketController();
        for (int i = 0; i <= 4; i++) {
            assertEquals(mc.getTowerName(i), ("Sample" + i));
        }
    }

    @Test
    public void testDescription() {
        MarketController mc = new MarketController();
        for (int i = 0; i <= 4; i++) {
            assertEquals(mc.getTowerDescription(i), ("Sample" + i + " throws things"));
        }
    }

    @Test
    public void testBalanceDifficulty() {
        Money money = new Money();
        money.setBalanceFromDifficulty(Difficulty.EASY);
        assertEquals(500, money.getBalance(), 0);

        Money money1 = new Money();
        money1.setBalanceFromDifficulty(Difficulty.MEDIUM);
        assertEquals(400, money1.getBalance(), 0);

        Money money2 = new Money();
        money2.setBalanceFromDifficulty(Difficulty.HARD);
        assertEquals(300, money2.getBalance(), 0);
    }
    @Test
    public void testBalanceException() {
        Money money = new Money();
        money.setBalanceFromDifficulty(Difficulty.EASY);
        try {
            // current balance: 500
            money.addBalance(-600);
            money.set(-600);
            money.addBalance(-600);
            money.subtractBalance(600);
            fail("Should not reach this");
        } catch (IllegalArgumentException e) {
            assertFalse(money.isValidTransaction(600));
            assertTrue(money.isValidTransaction(500));
        }
    }
}