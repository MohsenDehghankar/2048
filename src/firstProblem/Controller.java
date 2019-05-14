package firstProblem;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

import static firstProblem.Direction.*;


public class Controller {
    private static Controller controller = new Controller();
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    private static final Color BGCOLOR = Color.BLACK;
    private static Stage primaryStage;

    public static Controller getInstance() {
        return controller;
    }

    private Controller() {
    }


    public void showMainMenu(Stage primary) {
        primaryStage = primary;
        TextField textField = new TextField("Enter User Name ( then press ENTER )");
        Group group = new Group();
        Scene scene = new Scene(group, WIDTH / 2, HEIGHT / 2);
        scene.setFill(BGCOLOR);
        textField.relocate(scene.getWidth() / 2 - 130, scene.getHeight() / 2);
        textField.setFont(Font.font(15));
        textField.setPrefWidth(270);
        Label mainMenu = new Label("MAIN MENU");
        mainMenu.setFont(Font.font(20));
        mainMenu.setTextFill(Color.WHITE);
        mainMenu.relocate(WIDTH / 4 - 70, 20);
        mainMenu.setStyle("-fx-font-weight: bold;");
        group.getChildren().add(textField);
        group.getChildren().add(mainMenu);
        Button quit = new Button("Quit");
        quit.relocate(200, 200);
        group.getChildren().add(quit);
        group.getChildren().add(getLeaderBoardButton(primary));
        group.getChildren().add(getLoginButton(primary));
        quit.setOnMouseClicked(mouseEvent -> System.exit(0));
        textField.setOnAction(mouseEvent -> {
            if (textField.getText().split(" ").length > 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid User Name Entered ( don't use space )");
                alert.showAndWait();
            } else if (Player.searchPlayer(textField.getText()) != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("This User Name Is token");
                alert.showAndWait();
            } else {
                Player player = new Player(textField.getText());
                showSquareSizeEnteringMenu(primary, player);
            }
        });
        primary.setTitle("2048");
        primary.setScene(scene);
        primary.show();
    }

    private Button getLeaderBoardButton(Stage stage) {
        Button button = new Button("Leader Board");
        button.relocate(90, 200);
        button.setOnMouseClicked(mouseEvent -> {
            showLeaderBoards(stage);
        });
        return button;
    }

    private Button getLoginButton(Stage stage) {
        Button button = new Button("Login ( Already Have An Acount )");
        button.relocate(80, 20);
        button.setOnMouseClicked(mouseEvent -> {
            showLoginMenu(stage);
        });
        return button;
    }

    private void showLoginMenu(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 200);
        TextField userName = new TextField("Enter User Name");
        userName.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ESCAPE)
                showMainMenu(stage);
        });
        userName.setOnAction(actionEvent -> {
            if (userName.getText().split(" ").length > 1
                    || Player.searchPlayer(userName.getText()) == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid User Name Entered");
                alert.showAndWait();
            } else {
                Player player = Player.searchPlayer(userName.getText());
                PlayGround playGround = player.getPlayGround();
                controller.showInGameMenu(playGround, stage);
            }
        });
        root.getChildren().add(userName);
        stage.setScene(scene);
        stage.show();
    }

    private void showSquareSizeEnteringMenu(Stage primary, Player player) {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 100);
        scene.setFill(Color.BLACK);
        TextField field = new TextField("Enter Size Of Square");
        field.relocate(40, 30);
        field.setPrefWidth(125);
        field.setOnAction(actionEvent -> {
            int square;
            try {
                square = Integer.parseInt(field.getText());
                PlayGround playGround = new PlayGround(square, player);
                showInGameMenu(playGround, primary);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("invalid input");
                alert.setHeaderText("ERROR");
                alert.setContentText("Please Enter a number");
                alert.showAndWait();
            }
        });
        root.getChildren().add(field);
        primary.setScene(scene);
        primary.show();
    }

    private Label getEnterLeaderBoardLabel(){
        Label label = new Label("Press 'L' to enter Leader Board");
        label.relocate(450,20);
        label.setFont(Font.font(15));
        return label;
    }

    public void showInGameMenu(PlayGround playGround, Stage stage) {
        Player player = playGround.getPlayer();
        int squareSize = playGround.getSquareSize();
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.web("#bbada0"));
        Rectangle square = new Rectangle(100, 50, 500, 500);
        square.setFill(Color.web("#cdc0b4"));
        root.getChildren().add(square);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                showMainMenu(stage);
            } else if (key.getCode() == KeyCode.UP) {
                if (!playGround.move(UP))
                    root.getChildren().add(endGame());
                else
                    showInGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.DOWN) {
                if (!playGround.move(DOWN))
                    root.getChildren().add(endGame());
                else
                    showInGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.RIGHT) {
                if (!playGround.move(RIGHT))
                    root.getChildren().add(endGame());
                else
                    showInGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.LEFT) {
                if (!playGround.move(LEFT))
                    root.getChildren().add(endGame());
                else
                    showInGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.L) {
                showLeaderBoards(stage);
            }
        });
        Rectangle[][] rectangles = showPlayGroundCells(playGround);
        Label[][] labels = showPlayGroundLabels(playGround);
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                root.getChildren().add(rectangles[i][j]);
                if (labels[i][j] != null)
                    root.getChildren().add(labels[i][j]);
            }
        }
        root.getChildren().add(getPlayerScore(player));
        root.getChildren().add(getPlayerName(player));
        root.getChildren().add(getEnterLeaderBoardLabel());
        stage.setScene(scene);
        stage.show();
    }

    private Label endGame() {
        Label gameOver = new Label("Game Over ( ESCAPE to Menu )");
        gameOver.setFont(Font.font(20));
        gameOver.setTextFill(Color.BLUE);
        gameOver.relocate(150, 200);
        return gameOver;
    }

    private Rectangle[][] showPlayGroundCells(PlayGround playGround) {
        int squareSize = playGround.getSquareSize();
        Number[][] numbers = playGround.getNumbers();
        Rectangle[][] rectangles = new Rectangle[squareSize][squareSize];
        ArrayList<Node> result = new ArrayList<>();
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                rectangles[i][j] = new Rectangle(100 + j * 500 / squareSize + j, 50 + i * 500 / squareSize + i,
                        500 / squareSize, 500 / squareSize);
                rectangles[i][j].setFill(numbers[i][j].getColor());
                result.add(rectangles[i][j]);
            }
        }
        return rectangles;
    }

    private Label[][] showPlayGroundLabels(PlayGround playGround) {
        int squareSize = playGround.getSquareSize();
        Number[][] numbers = playGround.getNumbers();
        Label[][] labels = new Label[squareSize][squareSize];
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if (numbers[i][j].getNumber() != 0) {
                    labels[i][j] = new Label(String.valueOf(numbers[i][j].getNumber()));
                    labels[i][j].setFont(Font.font(20));
                    labels[i][j].relocate(100 + j * 500 / squareSize + j, 50 + i * 500 / squareSize + i);
                }
            }
        }
        return labels;
    }

    public void showLeaderBoards(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 200, 300);
        scene.setFill(Color.BLACK);
        ListView<String> leaderBoard = new ListView<>();
        for (Player player : Player.getPlayers()) {
            leaderBoard.getItems().add(player.getName() + " : " + player.getPoint());
        }
        leaderBoard.setPrefSize(200, 200);
        leaderBoard.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ESCAPE)
                showMainMenu(stage);
        });
        Label label = new Label("ESCAPE : Main Menu");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(10));
        label.relocate(60, 230);
        root.getChildren().add(label);
        root.getChildren().add(leaderBoard);
        stage.setScene(scene);
        stage.show();
    }

    private Label getPlayerScore(Player player) {
        String scoreBoard = "Your Score : " + (player.getPoint());
        Label score = new Label(scoreBoard);
        score.setFont(Font.font(20));
        score.setTextFill(Color.BLACK);
        score.relocate(WIDTH / 2 - 80, 10);
        return score;
    }

    private Label getPlayerName(Player player) {
        Label userName = new Label("User Name : " + player.getName());
        userName.relocate(50, 10);
        userName.setFont(Font.font(20));
        return userName;
    }
}
