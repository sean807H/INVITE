import javafx.application.Application;
import javafx.stage.Stage;

public class MainStory extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Story Scene Example");

        // GameUI 인스턴스 생성
        GameUI gameUI = new GameUI(primaryStage);

        // Story 인스턴스 생성 및 GameUI 전달
        Story story = new Story(gameUI);

        // Story의 화면 표시
        story.displayThirdPageWithBlinkEffect(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
