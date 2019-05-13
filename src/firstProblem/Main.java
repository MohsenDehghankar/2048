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
        /*Group root = new Group();
        primaryStage.setTitle("2048");
        Scene scene = new Scene(root,700,600);
        scene.setFill(Color.rgb(187,173,160));
        primaryStage.setScene(scene);
        primaryStage.show();*/
        Controller controller = Controller.getInstance();
        controller.mainMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
