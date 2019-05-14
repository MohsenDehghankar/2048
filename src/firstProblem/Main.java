package firstProblem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = Controller.getInstance();
        controller.mainMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
