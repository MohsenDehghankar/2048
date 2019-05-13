package firstProblem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.format.TextStyle;


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

    public void showPlayGround(PlayGround playGround) {
        Number[][] numbers = playGround.getNumbers();
        int squareSize = playGround.getSquareSize();
        Group group = new Group();
        Scene scene = new Scene(group, 700, 600);

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
        quit.relocate(150, 200);
        group.getChildren().add(quit);
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
                inGameMenu(playGround, player, primary);
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

    public void inGameMenu(PlayGround playGround, Player player, Stage stage) {
        int squareSize = playGround.getSquareSize();
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.web("#bbada0"));
        Label score = new Label();
        Rectangle square = new Rectangle(100, 50, 500, 500);
        square.setFill(Color.web("#cdc0b4"));
        Rectangle[][] rectangles = new Rectangle[squareSize][squareSize];
        /*for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                rectangles[i][j] = new Rectangle(100 + i * 500 / squareSize + 1, 100 + j * 500 / squareSize + 1,
                        500 / squareSize, 500 / squareSize);
            }
        }*/
        rectangles[0][0] = new Rectangle(100,50,500/squareSize,500/squareSize);

        root.getChildren().add(square);
        /*for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                root.getChildren().add(rectangles[i][j]);
            }
        }*/
        root.getChildren().add(rectangles[0][0]);
        stage.setScene(scene);
        stage.show();
    }
}
