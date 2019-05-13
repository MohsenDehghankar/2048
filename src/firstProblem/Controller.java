package firstProblem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


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
        TextField textField = new TextField("Enter User Name...");
        Group group = new Group();
        Scene scene = new Scene(group, WIDTH / 2, HEIGHT / 2);
        scene.setFill(BGCOLOR);
        textField.relocate(scene.getWidth() / 2 - 100, scene.getHeight() / 2);
        textField.setFont(Font.font(15));
        Label mainMenu = new Label("MAIN MENU");
        mainMenu.setFont(Font.font(20));
        mainMenu.setTextFill(Color.WHITE);
        mainMenu.relocate(WIDTH / 4 - 70, 20);
        mainMenu.setStyle("-fx-font-weight: bold;");
        group.getChildren().add(textField);
        group.getChildren().add(mainMenu);
        textField.setOnAction(mouseEvent -> {
            if (textField.getText().equals("Enter User Name...") ||
                    textField.getText().split(" ").length > 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid User Name Entered");
                alert.setHeaderText("ERROR");
                alert.setContentText("Please Enter a valid user name first ( no space )");
                alert.showAndWait();
            } else {
                //TODO
            }
        });
        primary.setTitle("2048");
        primary.setScene(scene);
        primary.show();
    }
}
