package sample;
import controller.MarketController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.IOException;
public class Main extends Application {
    private Stage mainStage;
    private Difficulty difficulty;
    private String playerName;
    private int money;
    private Monument monument;
    private Money balance;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainStage = primaryStage;
        this.mainStage.setTitle("Group 43");
        this.mainStage.setScene(createWelcomeScene());
        this.mainStage.setResizable(false);
        this.mainStage.sizeToScene();
        this.mainStage.show();
    }

    public Main(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public Main() {
    }

    public Scene createWelcomeScene() throws Exception {
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.PINK,
                CornerRadii.EMPTY, Insets.EMPTY)));
        //FileInputStream imageStream = new FileInputStream("Avatar.jpg");
        //Image image = new Image(imageStream);
        //grid.add(new ImageView(image), 0, 0);
        //ImageView imageView = new ImageView(new Image(new FileInputStream("Avatar.jpeg")));
        //imageView.setX(50);
        //imageView.setY(25);
        //imageView.setFitHeight(455);
        //imageView.setFitWidth(500);
        //imageView.setPreserveRatio(true);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setHgap(20);

        Text welcomeTxt = new Text("Welcome to the Game");
        welcomeTxt.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        grid.add(welcomeTxt, 0, 0);

        Button startButton = new Button("Start");
        startButton.setPrefSize(100, 100);
        startButton.setOnAction(e -> this.mainStage.setScene(createConfigScene()));
        grid.add(startButton, 0, 1, 2, 1);

        return new Scene(grid, 800, 800);
    }

    public Scene createConfigScene() {
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.PINK,
                CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(30);
        grid.setHgap(20);

        Label nameTxt = new Label("Name: ");
        nameTxt.setFont(Font.font("Arial", 20));
        grid.add(nameTxt, 0, 0);

        TextField playerName = new TextField();
        playerName.setPromptText("Enter your name here");
        playerName.setId("playerName"); // for testing purposes
        grid.add(playerName, 1, 0, 2, 1);

        Label difficultyTxt = new Label("Choose difficulty: ");
        difficultyTxt.setFont(Font.font("Arial", 20));
        grid.add(difficultyTxt, 0, 1);

        ComboBox<Difficulty> diffChoice = new ComboBox<>();
        diffChoice.getItems().setAll(Difficulty.values());
        diffChoice.setId("diffChoice"); // for testing purposes
        grid.add(diffChoice, 1, 1, 2, 1);

        Label errorTxt = new Label();
        errorTxt.setFont(Font.font("Arial", 13));
        errorTxt.setTextFill(Color.RED);
        errorTxt.setVisible(false);
        grid.add(errorTxt, 0, 3, 3, 1);

        Label errorTxt1 = new Label();
        errorTxt1.setFont(Font.font("Arial", 13));
        errorTxt1.setTextFill(Color.RED);
        errorTxt1.setVisible(false);
        grid.add(errorTxt1, 0, 4, 3, 1);

        Button startButton = new Button("Submit");
        startButton.setPrefSize(150, 150);
        startButton.setOnAction(event -> {
            errorTxt.setVisible(false);
            errorTxt.setText("");
            errorTxt.setId("errorTxt");
            errorTxt1.setVisible(false);
            errorTxt1.setText("");
            errorTxt.setId("errorTxt1");
            boolean validName = false;
            boolean validDiff = false;
            try {
                setValidName(playerName.getText());
                validName = true;
            } catch (IllegalArgumentException e) {
                errorTxt.setVisible(true);
                errorTxt.setText("Name cannot be null, empty, or whitespace-only name.");
            }
            try {
                setInitialValues(diffChoice.getValue());
                // this.monument.setHealthFromDifficulty(diffChoice.getValue());
                // this.balance.setBalanceFromDifficulty(diffChoice.getValue());
                this.difficulty = diffChoice.getValue();
                validDiff = true;
            } catch (IllegalArgumentException e) {
                if (errorTxt.getText().isEmpty()) {
                    errorTxt.setVisible(true);
                    errorTxt.setText("Difficulty cannot be null.");
                } else {
                    errorTxt1.setVisible(true);
                    errorTxt1.setText("Difficulty cannot be null.");
                }
            }
            if (validDiff && validName) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../fxml/market.fxml"));
                    loader.load();
                    MarketController marketController = loader.getController();
                    // transfer the balance to the market class
                    marketController.setValues(this.balance, this.difficulty, this.mainStage);
                    Parent root = loader.getRoot();
                    this.mainStage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // this.mainStage.setScene(createInitialGameScene());
            }
        });
        grid.add(startButton, 1, 2);
        return new Scene(grid, 800, 800);
    }

    public Scene createInitialGameScene() {
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.PINK,
                CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setVgap(30);
        grid.setHgap(20);

        Text welcomeTxt = new Text("Initial Game Scene");
        welcomeTxt.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        grid.add(welcomeTxt, 0, 0, 2, 1);

        Label startMoneyTxt = new Label("Money: ");
        startMoneyTxt.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        grid.add(startMoneyTxt, 0, 1);

        Label moneyTxt = new Label();
        moneyTxt.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        moneyTxt.setText(String.valueOf((int) this.balance.getBalance()));
        grid.add(moneyTxt, 1, 1);

        Label monumentTxt = new Label("Monument Health: ");
        monumentTxt.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        grid.add(monumentTxt, 0, 2);

        Label monumentHealthTxt = new Label();
        monumentHealthTxt.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        monumentHealthTxt.setText(String.valueOf(this.monument.getHealth()));
        grid.add(monumentHealthTxt, 1, 2);

        Button quitButton = new Button("Quit Game");
        quitButton.setPrefSize(100, 50);
        quitButton.setOnAction(e -> this.mainStage.setScene(createConfigScene()));
        grid.add(quitButton, 2, 0);

        grid.setBackground(new Background(
                new BackgroundImage(
                        new Image("https://static.wikia.nocookie.net/b__/images/8/85/Track2AA.PNG/revision/latest/scale-to-width-down/222?cb=20100926002912&path-prefix=bloons"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                                true, true, false, true)
                ),
                new BackgroundImage(
                        new Image("https://www.pinclipart.com/picdir/big/164-1647838_castles-clipart-free-castle-clip-art-pictures-clipartix.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.RIGHT, 140, false, Side.BOTTOM, 0, false),
                        new BackgroundSize(150, 150, false, false, false, false)
                ))
        );

        return new Scene(grid, 800, 800);
    }

    private void setValidName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Enter a valid name.");
        }
        this.playerName = name;
    }

    /**
     * Set amount of money and monument health based on difficulty level.
     * @param diff the difficulty level.
     */
    private void setInitialValues(Difficulty diff) {
        if (diff == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        this.monument = new Monument();
        this.balance = new Money();
        this.balance.setBalanceFromDifficulty(diff);
        this.monument.setHealthFromDifficulty(diff);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
