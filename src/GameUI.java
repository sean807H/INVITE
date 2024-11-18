import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class GameUI {
    private Stage stage;
    private MediaPlayer backgroundMusicPlayer;
    private MediaPlayer effectPlayer;
    private Story story; // Story 클래스 참조 추가

    public GameUI(Stage stage) {
        this.stage = stage;
        this.story = new Story(); // Story 인스턴스 초기화
        initializeUI();
    }
    public void initializeUI() {
        ImageView mainImageView = new ImageView(ImageLoader.loadImage("main-image.png"));
        mainImageView.setFitWidth(1440);
        mainImageView.setFitHeight(1024);

        ImageView inviteImageView = new ImageView(ImageLoader.loadImage("invite-button.png"));
        inviteImageView.setFitWidth(409);
        inviteImageView.setFitHeight(132);

        Button inviteButton = new Button("", inviteImageView);
        inviteButton.setStyle("-fx-background-color: transparent;");
        inviteButton.setOnAction(event -> loadNextPage());

        StackPane root = new StackPane(mainImageView, inviteButton);
        StackPane.setAlignment(inviteButton, Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root, 1440, 1024);
        stage.setScene(scene);
        stage.setTitle("초대");

        Media initialMusic = new Media(getClass().getResource("/sounds/MP_시린 겨울 바람.mp3").toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(initialMusic);
        backgroundMusicPlayer.play();

        backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO));
    }

    private void loadNextPage() {
        backgroundMusicPlayer.stop();

        ImageView waterGifView = new ImageView(ImageLoader.loadGif("water.gif"));
        waterGifView.setFitWidth(1440);
        waterGifView.setFitHeight(1024);

        ImageView invitationImageView = new ImageView(ImageLoader.loadImage("invitation.png"));
        invitationImageView.setFitWidth(776);
        invitationImageView.setFitHeight(849);
        invitationImageView.setOpacity(0);

        ImageView fontBackground1 = new ImageView(ImageLoader.loadImage("font-background.png"));
        fontBackground1.setFitWidth(344);
        fontBackground1.setFitHeight(207);

        ImageView fontBackground2 = new ImageView(ImageLoader.loadImage("font-background.png"));
        fontBackground2.setFitWidth(344);
        fontBackground2.setFitHeight(207);

        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightBold.ttf"), 30);

        Label text1 = new Label("어느 날, 로판에\n빙의된 당신!");
        text1.setFont(customFont);
        text1.setStyle("-fx-text-fill: black;");

        Label text2 = new Label("마음에 드는 것을\n선택해 결혼식을\n올리세요.");
        text2.setFont(customFont);
        text2.setStyle("-fx-text-fill: black;");

        StackPane textPane1 = new StackPane(fontBackground1, text1);
        textPane1.setAlignment(Pos.CENTER);
        textPane1.setTranslateX(-270);
        textPane1.setTranslateY(-220);
        textPane1.setOpacity(0);

        StackPane textPane2 = new StackPane(fontBackground2, text2);
        textPane2.setAlignment(Pos.CENTER);
        textPane2.setTranslateX(270);
        textPane2.setTranslateY(220);
        textPane2.setOpacity(0);

        StackPane nextPage = new StackPane(waterGifView, invitationImageView, textPane1, textPane2);
        StackPane.setAlignment(invitationImageView, Pos.CENTER);

        Scene nextScene = new Scene(nextPage, 1440, 1024);
        stage.setScene(nextScene);

        Media splashSound = new Media(getClass().getResource("/sounds/MP_Splash Rock In Lake.mp3").toExternalForm());
        effectPlayer = new MediaPlayer(splashSound);
        effectPlayer.play();

        FadeTransition fadeInInvitation = new FadeTransition(Duration.seconds(2), invitationImageView);
        fadeInInvitation.setFromValue(0);
        fadeInInvitation.setToValue(0.8);
        fadeInInvitation.setDelay(Duration.seconds(1.0));

        FadeTransition fadeInTextPane1 = new FadeTransition(Duration.seconds(2), textPane1);
        fadeInTextPane1.setFromValue(0);
        fadeInTextPane1.setToValue(1);
        fadeInTextPane1.setDelay(Duration.seconds(1.0));

        FadeTransition fadeInTextPane2 = new FadeTransition(Duration.seconds(2), textPane2);
        fadeInTextPane2.setFromValue(0);
        fadeInTextPane2.setToValue(1);
        fadeInTextPane2.setDelay(Duration.seconds(2.0));

        fadeInInvitation.setOnFinished(event -> {
            playDingAndResumeBackgroundMusic();
            fadeInTextPane1.play();
            fadeInTextPane2.play();

            // 두 번째 페이지 전체 페이드 아웃 효과
            FadeTransition fadeOutAll = new FadeTransition(Duration.seconds(1), nextPage);
            fadeOutAll.setFromValue(1);
            fadeOutAll.setToValue(0);
            fadeOutAll.setDelay(Duration.seconds(7)); // 텍스트 페이드 인 후에 페이드 아웃 시작

            fadeOutAll.setOnFinished(e -> transitionToThirdPage()); // 페이드 아웃 후 새로운 페이지로 전환
            fadeOutAll.play();
        });

        fadeInInvitation.play();
    }

    private void playDingAndResumeBackgroundMusic() {
        Media dingSound = new Media(getClass().getResource("/sounds/Ding Sound Effect.mp3").toExternalForm());
        effectPlayer = new MediaPlayer(dingSound);
        effectPlayer.play();

        effectPlayer.setOnEndOfMedia(() -> {
            Media winterMusic = new Media(getClass().getResource("/sounds/MP_시린 겨울 바람.mp3").toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(winterMusic);
            backgroundMusicPlayer.play();
        });
    }

    private void transitionToThirdPage() {
        // Story 클래스의 메서드를 호출하여 세 번째 페이지와 깜빡이는 효과를 구현합니다.
        story.displayThirdPageWithBlinkEffect(stage);
    }

}
