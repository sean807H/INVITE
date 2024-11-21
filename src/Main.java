import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {     // start 메서드: JavaFX 애플리케이션의 시작 지점
        new GameUI(primaryStage);        // primaryStage는 애플리케이션의 기본 Stage (창) 객체
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);    // launch(args): JavaFX 애플리케이션을 실행하고 start 메서드를 호출
    }
}
