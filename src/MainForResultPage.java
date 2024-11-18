import javafx.application.Application;
import javafx.stage.Stage;

public class MainForResultPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameUI gameUI = new GameUI(primaryStage); // GameUI 인스턴스 생성
        ResultPage resultPage = new ResultPage(primaryStage, gameUI); // GameUI 인스턴스를 전달
        resultPage.displayResultPage();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
