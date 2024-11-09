import javafx.application.Application;
import javafx.stage.Stage;

public class MainForResultPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        ResultPage resultPage = new ResultPage(primaryStage);
        resultPage.displayResultPage();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
