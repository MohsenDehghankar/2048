package firstProblem;

import com.sun.javafx.css.StyleCacheEntry;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

import static firstProblem.Direction.UP;


public class Controller {
    private static Controller controller = new Controller();
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    private static final Color BGCOLOR = Color.BLACK;

    public static Controller getInstance() {
        return controller;
    }

    private Controller() {
    }


    public void mainMenu(Stage primary) {
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
        quit.setOnMouseClicked(mouseEvent -> System.exit(0));
        textField.setOnAction(mouseEvent -> {
            if (textField.getText().split(" ").length > 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid User Name Entered");
                alert.setHeaderText("ERROR");
                alert.setContentText("Please Enter a valid user name first ( no space )");
                alert.showAndWait();
            } else {
                Player player = new Player(textField.getText());
                enteringSquareSize(primary, player);
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

    private void enteringSquareSize(Stage primary, Player player) {
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
                inGameMenu(playGround, primary);
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

    public void inGameMenu(PlayGround playGround, Stage stage) {
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
                mainMenu(stage);
            } else if (key.getCode() == KeyCode.UP) {
                playGround.move(UP);
                inGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.DOWN) {
                playGround.move(Direction.DOWN);
                inGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.RIGHT) {
                playGround.move(Direction.RIGHT);
                inGameMenu(playGround, stage);
            } else if (key.getCode() == KeyCode.LEFT) {
                playGround.move(Direction.LEFT);
                inGameMenu(playGround, stage);
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
        stage.setScene(scene);
        stage.show();
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
                    //String s = (i) +" " +(j);
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
                mainMenu(stage);
        });
        Label label = new Label("ESCAPE : Main Menu");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(10));
        label.relocate(60,230);
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
