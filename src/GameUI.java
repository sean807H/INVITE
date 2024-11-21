// GameUI 클래스: JavaFX 기반 게임 UI 관리 클래스
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
    private Stage stage; // JavaFX Stage 객체
    private MediaPlayer backgroundMusicPlayer; // 배경 음악 플레이어
    private MediaPlayer effectPlayer; // 효과음 플레이어
    private Story story; // Story 클래스 참조

    // GameUI 생성자: Stage와 Story 초기화
    public GameUI(Stage stage) {
        this.stage = stage;
        this.story = new Story(this); // Story 인스턴스 생성 및 GameUI 참조 전달
        initializeUI(); // 초기 UI 설정
    }

    // Stage 객체 반환
    public Stage getStage() {
        return stage;
    }

    // 씬(Scene) 전환 메서드
    public void changeScene(Scene newScene) {
        stage.setScene(newScene);
        stage.show();
    }

    // 결과 페이지로 이동
    public void goToResultPage() {
        ResultPage resultPage = new ResultPage(stage, this);
        resultPage.displayResultPage();
    }

    // 초기 화면으로 돌아가는 메서드
    public void goToStartPage() {
        initializeUI();
    }

    // 초기 UI 설정
    public void initializeUI() {
        // 메인 이미지 설정
        ImageView mainImageView = new ImageView(ImageLoader.loadImage("main-image.png"));
        mainImageView.setFitWidth(1440);
        mainImageView.setFitHeight(1024);

        // 초대 버튼 이미지와 설정
        ImageView inviteImageView = new ImageView(ImageLoader.loadImage("invite-button.png"));
        inviteImageView.setFitWidth(409);
        inviteImageView.setFitHeight(132);

        Button inviteButton = new Button("", inviteImageView); // 이미지로 버튼 생성
        inviteButton.setStyle("-fx-background-color: transparent;"); // 버튼 배경 투명화
        inviteButton.setOnAction(event -> loadNextPage()); // 클릭 시 다음 페이지 로드

        // UI 레이아웃 설정
        StackPane root = new StackPane(mainImageView, inviteButton);
        StackPane.setAlignment(inviteButton, Pos.BOTTOM_CENTER); // 버튼 위치 설정

        // Scene 설정
        Scene scene = new Scene(root, 1440, 1024);
        stage.setScene(scene);
        stage.setTitle("초대");

        // 배경 음악 설정 및 재생
        Media initialMusic = new Media(getClass().getResource("/sounds/MP_시린 겨울 바람.mp3").toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(initialMusic);
        backgroundMusicPlayer.play();

        // 배경 음악 무한 반복 설정
        backgroundMusicPlayer.setOnEndOfMedia(() -> backgroundMusicPlayer.seek(Duration.ZERO));
    }

    // 다음 페이지 로드
    private void loadNextPage() {
        backgroundMusicPlayer.stop(); // 배경 음악 정지

        // 물 효과 GIF와 초대 이미지 설정
        ImageView waterGifView = new ImageView(ImageLoader.loadGif("water.gif"));
        waterGifView.setFitWidth(1440);
        waterGifView.setFitHeight(1024);

        ImageView invitationImageView = new ImageView(ImageLoader.loadImage("invitation.png"));
        invitationImageView.setFitWidth(776);
        invitationImageView.setFitHeight(849);
        invitationImageView.setOpacity(0); // 초대 이미지 투명화 초기화

        ImageView fontBackground1 = new ImageView(ImageLoader.loadImage("font-background.png"));
        fontBackground1.setFitWidth(344);
        fontBackground1.setFitHeight(207);

        ImageView fontBackground2 = new ImageView(ImageLoader.loadImage("font-background.png"));
        fontBackground2.setFitWidth(344);
        fontBackground2.setFitHeight(207);

        // 텍스트와 배경 설정
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);

        Label text1 = new Label("어느 날, 로판에\n빙의된 당신!");
        text1.setFont(customFont);
        text1.setStyle("-fx-text-fill: black;");

        Label text2 = new Label("마음에 드는 것을\n선택하세요.\n(점수를 더해주세요!)");
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

        // 다음 페이지 레이아웃
        StackPane nextPage = new StackPane(waterGifView, invitationImageView, textPane1, textPane2);
        StackPane.setAlignment(invitationImageView, Pos.CENTER);

        // 새로운 Scene 생성 및 전환
        Scene nextScene = new Scene(nextPage, 1440, 1024);
        stage.setScene(nextScene);

        // 효과음 재생
        Media splashSound = new Media(getClass().getResource("/sounds/MP_Splash Rock In Lake.mp3").toExternalForm());
        effectPlayer = new MediaPlayer(splashSound);
        effectPlayer.play();

        // 초대 이미지 페이드 인 애니메이션
        FadeTransition fadeInInvitation = new FadeTransition(Duration.seconds(2), invitationImageView);
        fadeInInvitation.setFromValue(0);
        fadeInInvitation.setToValue(0.8);
        fadeInInvitation.setDelay(Duration.seconds(1.0));

        // 텍스트 애니메이션 설정
        FadeTransition fadeInTextPane1 = new FadeTransition(Duration.seconds(2), textPane1);
        fadeInTextPane1.setFromValue(0);
        fadeInTextPane1.setToValue(1);
        fadeInTextPane1.setDelay(Duration.seconds(1.0));

        FadeTransition fadeInTextPane2 = new FadeTransition(Duration.seconds(2), textPane2);
        fadeInTextPane2.setFromValue(0);
        fadeInTextPane2.setToValue(1);
        fadeInTextPane2.setDelay(Duration.seconds(2.0));

        // 페이드 인 후 페이지 전체 페이드 아웃
        fadeInInvitation.setOnFinished(event -> {
            playDingAndResumeBackgroundMusic();
            fadeInTextPane1.play();
            fadeInTextPane2.play();

            FadeTransition fadeOutAll = new FadeTransition(Duration.seconds(1), nextPage);
            fadeOutAll.setFromValue(1);
            fadeOutAll.setToValue(0);
            fadeOutAll.setDelay(Duration.seconds(7));

            fadeOutAll.setOnFinished(e -> transitionToThirdPage());
            fadeOutAll.play();
        });

        fadeInInvitation.play(); // 초대 이미지 페이드 인 시작
    }

    // 효과음 재생 후 배경음악 다시 재생
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

    // 세 번째 페이지로 전환
    private void transitionToThirdPage() {
        story.displayThirdPageWithBlinkEffect(stage);
    }
}