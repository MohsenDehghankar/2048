package firstProblem;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = Controller.getInstance();
        controller.showMainMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
