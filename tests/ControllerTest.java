import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Test;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;

import sample.Main;

public class ControllerTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);
    }

    /**
     * Testing the 2nd screen: Configuration Game Screen
     * Test the error message when submitting an invalid name or difficulty
     */
    @Test
    public void testConfigScreen() {
        clickOn("Start");
        // case 1: both name and difficulty are invalid
        clickOn("Submit");
        verifyThat("Name cannot be null, empty, or whitespace-only name.",
                NodeMatchers.isNotNull());
        verifyThat("Difficulty cannot be null.", NodeMatchers.isNotNull());
        // case 2: name is valid and difficulty is not
        clickOn("#playerName").write("Player Name"); // the TextField with ID of 'playerName'
        clickOn("Submit"); // a button with value 'Submit'
        verifyThat("Difficulty cannot be null.",
                NodeMatchers.isNotNull());
        assertThat(lookup("errorTxt1").tryQuery()).isEmpty();
        // case 3: name is invalid and difficulty is valid
        doubleClickOn("#playerName").clickOn("#playerName").eraseText(2);
        clickOn("#diffChoice").clickOn("HARD"); // the ComboBox with ID of 'diffChoice'
        clickOn("Submit");
        verifyThat("Name cannot be null, empty, or whitespace-only name.",
                NodeMatchers.isNotNull());
        assertThat(lookup("errorTxt").tryQuery()).isEmpty();
        // case 4: name is invalid (empty space) and difficulty is valid
        clickOn("#playerName").write("   ");
        clickOn("Submit");
        verifyThat("Name cannot be null, empty, or whitespace-only name.",
                NodeMatchers.isNotNull());
        assertThat(lookup("errorTxt").tryQuery()).isEmpty();
        // case 5: name is valid (empty space) and difficulty is valid
        doubleClickOn("#playerName").clickOn("#playerName").eraseText(2);
        clickOn("#playerName").write("Player Name");
        clickOn("Submit");
        // verifyThat("Initial Game Scene", NodeMatchers.isNotNull());
    }

    @Test
    public void testInitialGameScreen() {
        clickOn("Start");
        clickOn("#playerName").write("Player Name");
        clickOn("#diffChoice").clickOn("HARD");
        clickOn("Submit");
        verifyThat("Initial Game Scene", NodeMatchers.isNotNull());
        verifyThat("Money: ", NodeMatchers.isNotNull());
        verifyThat("Monument Health: ", NodeMatchers.isNotNull());
        verifyThat("300", NodeMatchers.isNotNull());
        verifyThat("Quit Game", NodeMatchers.isNotNull());
        clickOn("Quit Game");
        verifyThat("Name: ", NodeMatchers.isNotNull());
    }

    @Test
    public void testWelcomeScreen() {
        verifyThat("Welcome to the Game", NodeMatchers.isNotNull());
        verifyThat("Start", NodeMatchers.isNotNull());
    }
    @Test
    public void testTransitions() {
        verifyThat("Welcome to the Game", NodeMatchers.isNotNull());
        verifyThat("Start", NodeMatchers.isNotNull());
        clickOn("Start");
        //transition to config
        verifyThat("Name: ", NodeMatchers.isNotNull());
        verifyThat("Choose difficulty: ", NodeMatchers.isNotNull());
        verifyThat("Submit", NodeMatchers.isNotNull());
        clickOn("#playerName").write("Sample Name");
        clickOn("#diffChoice").clickOn("EASY");
        clickOn("Submit");
        // transition to game scene
        // verifyThat("Initial Game Scene", NodeMatchers.isNotNull());
        verifyThat("Money: ", NodeMatchers.isNotNull());
        verifyThat("Monument Health: ", NodeMatchers.isNotNull());
        verifyThat("500", NodeMatchers.isNotNull());
        verifyThat("Quit Game", NodeMatchers.isNotNull());
        clickOn("Quit Game");
        //transition to config screen after quitting
        verifyThat("Name: ", NodeMatchers.isNotNull());
        verifyThat("Choose difficulty: ", NodeMatchers.isNotNull());
        verifyThat("Submit", NodeMatchers.isNotNull());
        //verifies it goes back to config screen
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testNameDiffSub() {
        clickOn("Start");
        verifyThat("Name: ", NodeMatchers.isNotNull());
        verifyThat("Choose difficulty: ", NodeMatchers.isNotNull());
        verifyThat("Submit", NodeMatchers.isNotNull());
    }
}
