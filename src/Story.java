import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene; // Scene 클래스 import 추가
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class Story {
    private GameUI gameUI;
    private Label dialogLabel; // 대사 레이블
    private ImageView storyFrame; // 대사 배경 이미지
    private ImageView arrowImageView; // 화살표 이미지

    // 선택지 이미지 변수
    private ImageView choice1ImageView;
    private ImageView choice2ImageView;
    private ImageView choice3ImageView;
    private ImageView choice4ImageView;

    // 선택지 이미지를 감쌀 StackPane 변수
    private StackPane choice1Pane;
    private StackPane choice2Pane;
    private StackPane choice3Pane;
    private StackPane choice4Pane;

    private Font customFont; // 클래스 레벨 폰트 변수

    public Story(GameUI gameUI) {
        this.gameUI = gameUI;
        loadCustomFont();
    }

    // 커스텀 폰트 로드 메서드
    private void loadCustomFont() {
        try {
            customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);
            if (customFont == null) {
                throw new Exception("Custom font not found.");
            }
        } catch (Exception e) {
            System.err.println("Failed to load custom font. Using default font.");
            customFont = Font.font("Arial", 30); // 기본 폰트로 대체
        }
    }

    public void displayThirdPageWithBlinkEffect(Stage stage) {
        // 배경 이미지 설정
        ImageView backgroundImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/StoryPage.png")));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(1024);

        // 로고 이미지 설정 및 위치 조정
        ImageView logoImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/invite-logo.png")));
        logoImageView.setFitWidth(302);
        logoImageView.setFitHeight(175);
        logoImageView.setOpacity(0);

        // 로고에 마우스 이벤트 설정
        logoImageView.setOnMouseEntered(event -> logoImageView.setCursor(Cursor.HAND));
        logoImageView.setOnMouseExited(event -> logoImageView.setCursor(Cursor.DEFAULT));
        logoImageView.setOnMouseClicked(event -> gameUI.goToStartPage());

        // 로고 위치 설정
        StackPane.setAlignment(logoImageView, Pos.TOP_LEFT);
        StackPane.setMargin(logoImageView, new Insets(20, 0, 0, 20)); // 위쪽 20px, 왼쪽 20px 마진

        // 검은색 오버레이 설정
        StackPane blackOverlay = new StackPane();
        blackOverlay.setStyle("-fx-background-color: black;");
        blackOverlay.setOpacity(0.6);
        blackOverlay.setMouseTransparent(true); // 마우스 이벤트가 아래로 전달되도록 설정

        // 초기 스토리 레이블 설정
        Label storyLabel = new Label("데뷔탕트 당일, 당신은 치장을 마무리하고 있습니다");
        storyLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +  // 검정색 배경
                        "-fx-background-radius: 10;" +    // 둥근 모서리 10px
                        "-fx-padding: 10;"                // 텍스트 주변 여백
        );
        storyLabel.setFont(Font.font(customFont.getFamily(), 30));
        storyLabel.setOpacity(0);

        // 스토리 레이블을 중앙에 배치
        StackPane.setAlignment(storyLabel, Pos.CENTER);

        // 대사 프레임 설정
        storyFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/storyframe.png")));
        storyFrame.setFitWidth(1352); // 변경된 너비
        storyFrame.setFitHeight(392); // 변경된 높이
        storyFrame.setOpacity(0);
        StackPane.setAlignment(storyFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(storyFrame, new Insets(0, 0, 50, 0)); // 아래쪽 50px 마진

        // 대사 레이블 설정
        dialogLabel = new Label("(사용인1) 치장을 하였습니다. 눈을 떠주세요 공주님");
        dialogLabel.setStyle("-fx-text-fill: black;");
        dialogLabel.setFont(Font.font(customFont.getFamily(), 35));
        dialogLabel.setOpacity(0);

        // 대사 레이블을 storyFrame 위에 배치
        StackPane.setAlignment(dialogLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(dialogLabel, new Insets(0, 0, 200, 0)); // 아래쪽 200px 마진

        // 화살표 이미지 설정
        arrowImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        arrowImageView.setFitWidth(30); // 화살표 크기 조정
        arrowImageView.setFitHeight(25);
        arrowImageView.setOpacity(0); // 초기 상태는 보이지 않음

        // 화살표에 마우스 이벤트 설정
        arrowImageView.setOnMouseEntered(event -> arrowImageView.setCursor(Cursor.HAND));
        arrowImageView.setOnMouseExited(event -> arrowImageView.setCursor(Cursor.DEFAULT));

        // 화살표 클릭 시 NextStory 메서드 호출
        arrowImageView.setOnMouseClicked(event -> NextStory(stage));

        // 화살표를 화면 하단 중앙에 배치
        StackPane.setAlignment(arrowImageView, Pos.BOTTOM_CENTER);
        StackPane.setMargin(arrowImageView, new Insets(0, 0, 150, 0)); // 아래쪽 150px 마진

        // 모든 요소를 StackPane에 추가
        StackPane thirdPage = new StackPane(backgroundImageView, blackOverlay, logoImageView, storyLabel, storyFrame, dialogLabel, arrowImageView);
        Scene thirdScene = new Scene(thirdPage, 1440, 1024);

        // 애니메이션 설정
        FadeTransition fadeInLogo = new FadeTransition(Duration.seconds(2), logoImageView);
        fadeInLogo.setFromValue(0);
        fadeInLogo.setToValue(1);

        FadeTransition fadeInStoryLabel = new FadeTransition(Duration.seconds(2), storyLabel);
        fadeInStoryLabel.setFromValue(0);
        fadeInStoryLabel.setToValue(1);

        // 깜빡이는 효과 추가
        FadeTransition blinkEffect = new FadeTransition(Duration.seconds(1), blackOverlay);
        blinkEffect.setFromValue(0.6);
        blinkEffect.setToValue(0);

        blinkEffect.setOnFinished(event -> {
            fadeInLogo.play();
            fadeInStoryLabel.play();
        });

        // 3초 뒤에 storyLabel 페이드 아웃 효과 추가
        FadeTransition fadeOutStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeOutStoryLabel.setFromValue(1);
        fadeOutStoryLabel.setToValue(0);
        fadeOutStoryLabel.setDelay(Duration.seconds(3)); // 3초 뒤에 페이드 아웃 시작

        // dialogLabel과 storyFrame을 동시에 나타내는 페이드 인 효과 추가
        FadeTransition fadeInStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeInStoryFrame.setFromValue(0);
        fadeInStoryFrame.setToValue(1);

        FadeTransition fadeInDialogLabel = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeInDialogLabel.setFromValue(0);
        fadeInDialogLabel.setToValue(1);

        // 화살표 이미지 페이드 인 효과 추가
        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), arrowImageView);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);

        // storyLabel이 사라진 뒤에 storyFrame, dialogLabel, arrowImageView를 동시에 나타내도록 설정
        fadeOutStoryLabel.setOnFinished(event -> {
            fadeInStoryFrame.play();
            fadeInDialogLabel.play();
            fadeInArrow.play();
        });

        // 씬 설정 및 애니메이션 실행
        stage.setScene(thirdScene);
        blinkEffect.play();
        fadeInStoryLabel.play();
        fadeOutStoryLabel.play();
    }

    private void NextStory(Stage stage) {
        // 화살표를 비활성화하여 여러 번 클릭하지 못하도록 함
        arrowImageView.setDisable(true);

        // 다음 대사 표시
        displayNextDialog("(여긴 어디지.. 눈을 뜨라고?)", () -> {
            // 대사 변경 후 화살표 클릭 이벤트를 handleArrowClick으로 변경
            arrowImageView.setOnMouseClicked(event -> handleArrowClick(stage));
            // 이벤트 변경 후 화살표를 다시 활성화
            arrowImageView.setDisable(false);
        });
    }

    private void displayNextDialog(String newDialog, Runnable onComplete) {
        // dialogLabel을 페이드 아웃
        FadeTransition fadeOutDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeOutDialog.setFromValue(1);
        fadeOutDialog.setToValue(0);

        fadeOutDialog.setOnFinished(event -> {
            dialogLabel.setText(newDialog); // 대사 텍스트 변경
            FadeTransition fadeInDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
            fadeInDialog.setFromValue(0);
            fadeInDialog.setToValue(1);

            fadeInDialog.setOnFinished(event2 -> {
                if (onComplete != null) {
                    onComplete.run(); // 완료 후 추가 작업 실행
                }
            });

            fadeInDialog.play();
        });

        fadeOutDialog.play();
    }

    private void handleArrowClick(Stage stage) {
        // storyFrame, dialogLabel, arrowImageView을 페이드 아웃
        FadeTransition fadeOutStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeOutStoryFrame.setFromValue(1);
        fadeOutStoryFrame.setToValue(0);

        FadeTransition fadeOutDialogLabel = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeOutDialogLabel.setFromValue(1);
        fadeOutDialogLabel.setToValue(0);

        FadeTransition fadeOutArrow = new FadeTransition(Duration.seconds(1), arrowImageView);
        fadeOutArrow.setFromValue(1);
        fadeOutArrow.setToValue(0);

        fadeOutArrow.setOnFinished(event -> displayChoices(stage));

        fadeOutStoryFrame.play();
        fadeOutDialogLabel.play();
        fadeOutArrow.play();
    }

    private void displayChoices(Stage stage) {
        // 선택지 레이블 설정 및 스타일 적용
        Label choiceLabel = new Label("눈을 뜨고 거울로 본 당신의 모습은?");
        choiceLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +  // 검정색 배경
                        "-fx-background-radius: 10;" +    // 둥근 모서리 10px
                        "-fx-padding: 10;"                // 텍스트 주변 여백
        );
        choiceLabel.setFont(Font.font(customFont.getFamily(), 30));
        choiceLabel.setOpacity(0);

        // 선택지 이미지 설정
        choice1ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/세실리아 로젤린.png")));
        choice2ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/엘로이즈 리벨렌.png")));
        choice3ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/릴리안 에버린.png")));
        choice4ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/이벨린 베인.png")));

        // 선택지 이미지 크기 조정
        double choiceHeight = 413; // 원하는 높이로 조정
        double choiceWidth1 = 273; // 원하는 너비로 조정
        double choiceWidth2 = 297; // 원하는 너비로 조정
        double choiceWidth3 = 262; // 원하는 너비로 조정
        double choiceWidth4 = 323; // 원하는 너비로 조정

        choice1ImageView.setFitWidth(choiceWidth1);
        choice1ImageView.setFitHeight(choiceHeight);
        choice2ImageView.setFitWidth(choiceWidth2);
        choice2ImageView.setFitHeight(choiceHeight);
        choice3ImageView.setFitWidth(choiceWidth3);
        choice3ImageView.setFitHeight(choiceHeight);
        choice4ImageView.setFitWidth(choiceWidth4);
        choice4ImageView.setFitHeight(choiceHeight);

        // 선택지 이미지 초기 투명도 설정
        choice1ImageView.setOpacity(1);
        choice2ImageView.setOpacity(1);
        choice3ImageView.setOpacity(1);
        choice4ImageView.setOpacity(1);

        // 각 ImageView를 StackPane으로 감싸기
        choice1Pane = new StackPane(choice1ImageView);
        choice2Pane = new StackPane(choice2ImageView);
        choice3Pane = new StackPane(choice3ImageView);
        choice4Pane = new StackPane(choice4ImageView);

        // 선택지 이미지를 HBox에 배치 (가로로 배치)
        HBox choicesHBox = new HBox(20, choice1Pane, choice2Pane, choice3Pane, choice4Pane);
        choicesHBox.setAlignment(Pos.CENTER);
        choicesHBox.setOpacity(1); // 초기 투명도 설정

        // VBox에 선택지 레이블과 HBox 배치
        VBox choicesBox = new VBox(20, choiceLabel, choicesHBox);
        choicesBox.setAlignment(Pos.CENTER);
        choicesBox.setOpacity(1); // 초기 투명도 설정

        // StackPane에 선택지 레이블과 선택지 박스를 배치
        StackPane choicesPane = new StackPane(choiceLabel, choicesBox);
        StackPane.setAlignment(choiceLabel, Pos.TOP_CENTER);
        StackPane.setMargin(choiceLabel, new Insets(50, 0, 0, 0));
        StackPane.setAlignment(choicesBox, Pos.CENTER);

        // 현재 씬의 루트 노드 가져오기
        StackPane root = (StackPane) stage.getScene().getRoot();
        root.getChildren().add(choicesPane);

        // 선택지 레이블과 선택지 박스에 페이드 인 효과 적용
        FadeTransition fadeInChoiceLabel = new FadeTransition(Duration.seconds(1), choiceLabel);
        fadeInChoiceLabel.setFromValue(0);
        fadeInChoiceLabel.setToValue(1);

        FadeTransition fadeInChoicesBox = new FadeTransition(Duration.seconds(1), choicesBox);
        fadeInChoicesBox.setFromValue(0);
        fadeInChoicesBox.setToValue(1);

        fadeInChoiceLabel.play();
        fadeInChoicesBox.play();

        // loading.gif 추가
        addLoadingGif(root);
    }

    private void addLoadingGif(StackPane root) {
        // loading.gif 설정
        ImageView loadingImageView;
        try {
            loadingImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        } catch (Exception e) {
            System.err.println("Failed to load loading.gif.");
            e.printStackTrace();
            return;
        }
        loadingImageView.setFitWidth(500);
        loadingImageView.setFitHeight(300);
        loadingImageView.setOpacity(0);

        // loading.gif를 중앙에 배치하기 위해 StackPane 사용
        StackPane loadingPane = new StackPane(loadingImageView);
        loadingPane.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setMargin(loadingPane, new Insets(0, 0, 50, 0));

        // Fade in loading.gif
        FadeTransition fadeInLoading = new FadeTransition(Duration.seconds(0.5), loadingImageView);
        fadeInLoading.setFromValue(0);
        fadeInLoading.setToValue(1);

        // loadingPane을 root에 추가
        root.getChildren().add(loadingPane);

        fadeInLoading.play();

        // 5초 동안 대기
        PauseTransition pause = new PauseTransition(Duration.seconds(5.1));
        pause.setOnFinished(event -> {
            // Fade out loading.gif
            FadeTransition fadeOutLoading = new FadeTransition(Duration.seconds(0.5), loadingImageView);
            fadeOutLoading.setFromValue(1);
            fadeOutLoading.setToValue(0);
            fadeOutLoading.setOnFinished(ev -> root.getChildren().remove(loadingPane));

            fadeOutLoading.play();

            // 이미지 회색 처리 및 점수 텍스트 표시
            applyGrayScaleAndShowScores();
        });
        pause.play();
    }

    private void applyGrayScaleAndShowScores() {
        // 각 선택지에 회색 효과 적용
        applyGrayScale(choice1ImageView);
        applyGrayScale(choice2ImageView);
        applyGrayScale(choice3ImageView);
        applyGrayScale(choice4ImageView);

        // 각 선택지 위에 점수 텍스트 추가
        addScoreLabelToPane(choice1Pane, "+1");
        addScoreLabelToPane(choice2Pane, "+3");
        addScoreLabelToPane(choice3Pane, "+2");
        addScoreLabelToPane(choice4Pane, "+0");
    }

    private void applyGrayScale(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1); // 회색으로 만들기
        imageView.setEffect(colorAdjust);
    }

    private void addScoreLabelToPane(StackPane pane, String score) {
        Label scoreLabel = new Label(score);
        scoreLabel.setStyle(
                "-fx-text-fill: Blue;"
        );
        scoreLabel.setFont(Font.font(customFont.getFamily(), 50));
        scoreLabel.setOpacity(0);

        pane.getChildren().add(scoreLabel);

        // 점수 레이블에 페이드 인 효과 적용
        FadeTransition fadeInScore = new FadeTransition(Duration.seconds(1), scoreLabel);
        fadeInScore.setFromValue(0);
        fadeInScore.setToValue(1);
        fadeInScore.play();
    }

}
