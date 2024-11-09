import javafx.animation.FadeTransition;
import javafx.geometry.Pos; // Pos 클래스 추가
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Story {

    public void displayThirdPageWithBlinkEffect(Stage stage) {
        // 세 번째 페이지의 배경 및 로고 설정
        ImageView backgroundImageView = new ImageView(ImageLoader.loadImage("first-image.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(1024);

        ImageView logoImageView = new ImageView(ImageLoader.loadImage("invite-logo.png"));
        logoImageView.setFitWidth(302);
        logoImageView.setFitHeight(175);
        logoImageView.setOpacity(0);
        StackPane.setAlignment(logoImageView, Pos.TOP_LEFT);
        logoImageView.setTranslateX(50);
        logoImageView.setTranslateY(20);

        StackPane blackOverlay = new StackPane();
        blackOverlay.setStyle("-fx-background-color: black;");
        blackOverlay.setOpacity(0.6);

        StackPane thirdPage = new StackPane(backgroundImageView, logoImageView, blackOverlay);
        Scene thirdScene = new Scene(thirdPage, 1440, 1024);

        FadeTransition fadeInLogo = new FadeTransition(Duration.seconds(2), logoImageView);
        fadeInLogo.setFromValue(0);
        fadeInLogo.setToValue(1);

        FadeTransition blinkEffect = new FadeTransition(Duration.seconds(1), blackOverlay);
        blinkEffect.setFromValue(0.6);
        blinkEffect.setToValue(0);

        blinkEffect.setOnFinished(event -> fadeInLogo.play());

        stage.setScene(thirdScene);
        blinkEffect.play();
    }
}
