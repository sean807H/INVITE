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
    private Stage stage;
    private GameUI gameUI;
    private StackPane root; // root를 클래스 레벨 변수로 선언

    private ImageView nextBackground; // 배경 이미지 클래스 레벨 변수로 선언

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
        this.stage = gameUI.getStage();
        loadCustomFont();
        setupBackground();
    }

    // 배경 이미지 설정
    private void setupBackground() {
        nextBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/StoryPage.png")));
        nextBackground.setFitWidth(1440);
        nextBackground.setFitHeight(1024);
    }

    // 사용자 정의 폰트를 로드
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
        dialogLabel = new Label("(사용인1) 치장을 하였습니다. 눈을 떠주십시오. 공주님");
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

        // root 초기화
        root = new StackPane(backgroundImageView, blackOverlay, storyLabel, storyFrame, dialogLabel, arrowImageView);
        Scene thirdScene = new Scene(root, 1440, 1024);
        stage.setScene(thirdScene);


        FadeTransition fadeInStoryLabel = new FadeTransition(Duration.seconds(2), storyLabel);
        fadeInStoryLabel.setFromValue(0);
        fadeInStoryLabel.setToValue(1);

        // 깜빡이는 효과 추가
        FadeTransition blinkEffect = new FadeTransition(Duration.seconds(1), blackOverlay);
        blinkEffect.setFromValue(0.6);
        blinkEffect.setToValue(0);

        blinkEffect.setOnFinished(event -> {
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

        addNextArrow(root);
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

    private void addNextArrow(StackPane root) {
        // 화살표 이미지 생성
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30); // 화살표 크기 설정
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);

        // 화살표 위치 및 이벤트 설정
        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 150, 0));
        nextArrow.setOnMouseEntered(event -> nextArrow.setCursor(Cursor.HAND));
        nextArrow.setOnMouseExited(event -> nextArrow.setCursor(Cursor.DEFAULT));
        nextArrow.setOnMouseClicked(event -> {
            // 현재 선택지 UI 제거 및 다음 이야기로 진행
            root.getChildren().clear(); // 현재 화면의 모든 요소 제거
            proceedToNextStory(root); // 다음 페이지로 넘어가기
        });

        // 화살표를 root에 추가
        root.getChildren().add(nextArrow);

        // 화살표 페이드 인 애니메이션
        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);
        fadeInArrow.play();
    }

    private void proceedToNextStory(StackPane root) {
        // root에 배경 이미지 추가
        root.getChildren().clear();
        root.getChildren().add(nextBackground);

        // 대사 프레임 설정
        storyFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/storyframe.png")));
        storyFrame.setFitWidth(1352); // 변경된 너비
        storyFrame.setFitHeight(392); // 변경된 높이
        storyFrame.setOpacity(0);
        StackPane.setAlignment(storyFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(storyFrame, new Insets(0, 0, 50, 0)); // 아래쪽 50px 마진

        // 대사 레이블 설정
        dialogLabel = new Label("(사용인1) 공주님 이제 나가보셔야 합니다. 얼른 골라주십시오.");
        dialogLabel.setStyle("-fx-text-fill: black;");
        dialogLabel.setFont(Font.font(customFont.getFamily(), 35));
        dialogLabel.setOpacity(0);

        // 대사 레이블을 storyFrame 위에 배치
        StackPane.setAlignment(dialogLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(dialogLabel, new Insets(0, 0, 200, 0)); // 아래쪽 200px 마진

        // 화살표 이미지 설정
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0); // 초기 숨김
        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 150, 0));

        // 화살표 이벤트 추가
        nextArrow.setOnMouseEntered(event -> nextArrow.setCursor(Cursor.HAND));
        nextArrow.setOnMouseExited(event -> nextArrow.setCursor(Cursor.DEFAULT));
        nextArrow.setOnMouseClicked(event -> {
            // 다음 선택지를 표시
            displayChoices(root);
        });

        // root에 새로운 요소 추가
        root.getChildren().clear();
        root.getChildren().addAll(nextBackground, storyFrame, dialogLabel, nextArrow);

        // 애니메이션 설정
        FadeTransition fadeInStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeInStoryFrame.setFromValue(0);
        fadeInStoryFrame.setToValue(1);

        FadeTransition fadeInDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeInDialog.setFromValue(0);
        fadeInDialog.setToValue(1);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);

        // 애니메이션 실행
        fadeInStoryFrame.play();
        fadeInDialog.play();
        fadeInArrow.play();
    }

    private void displayChoices(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/StoryPage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 배경 이미지를 가장 먼저 추가하여 모든 요소들 뒤에 위치하도록 설정
        root.getChildren().add(newBackground); // 인덱스 0으로 추가하여 가장 뒤에 배치

        // 선택지 레이블 설정 및 스타일 적용
        Label choiceLabel = new Label("당신이 데뷔탕트에 신고 갈 구두는?");
        choiceLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +  // 검정색 배경
                        "-fx-background-radius: 10;" +    // 둥근 모서리
                        "-fx-padding: 10;"                // 텍스트 주변 여백
        );
        choiceLabel.setFont(Font.font(customFont.getFamily(), 30));
        choiceLabel.setOpacity(1);

        // 선택지 이미지 설정
        choice1ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/redshoes.png")));
        choice2ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/greenshoes.png")));
        choice3ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/blueshoes.png")));
        choice4ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/yellowshoes.png")));

        // 선택지 이미지 크기 조정
        double choiceHeight = 329;
        double choiceWidth = 355;

        choice1ImageView.setFitWidth(choiceWidth);
        choice1ImageView.setFitHeight(choiceHeight);
        choice2ImageView.setFitWidth(choiceWidth);
        choice2ImageView.setFitHeight(choiceHeight);
        choice3ImageView.setFitWidth(choiceWidth);
        choice3ImageView.setFitHeight(choiceHeight);
        choice4ImageView.setFitWidth(choiceWidth);
        choice4ImageView.setFitHeight(choiceHeight);

        // 각 선택지 감싸기
        choice1Pane = new StackPane(choice1ImageView);
        choice2Pane = new StackPane(choice2ImageView);
        choice3Pane = new StackPane(choice3ImageView);
        choice4Pane = new StackPane(choice4ImageView);

        // 상단 HBox에 두 개의 선택지 배치
        HBox topChoicesHBox = new HBox(50, choice1Pane, choice2Pane);
        topChoicesHBox.setAlignment(Pos.CENTER);

        // 하단 HBox에 두 개의 선택지 배치
        HBox bottomChoicesHBox = new HBox(50, choice3Pane, choice4Pane);
        bottomChoicesHBox.setAlignment(Pos.CENTER);

        // 선택지 전체를 VBox로 배치
        VBox choicesBox = new VBox(50, choiceLabel, topChoicesHBox, bottomChoicesHBox);
        choicesBox.setAlignment(Pos.CENTER);

        // root에 선택지 박스를 추가
        root.getChildren().add(choicesBox);

        // 페이드 인 애니메이션
        FadeTransition fadeInChoicesBox = new FadeTransition(Duration.seconds(1), choicesBox);
        fadeInChoicesBox.setFromValue(0);
        fadeInChoicesBox.setToValue(1);

        fadeInChoicesBox.setOnFinished(event -> showLoadingGif(root));
        fadeInChoicesBox.play();
    }


    private void showLoadingGif(StackPane root) {
        ImageView loadingImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        loadingImageView.setFitWidth(500);
        loadingImageView.setFitHeight(300);
        loadingImageView.setOpacity(0);

        // loading.gif를 중앙에 배치하기 위해 StackPane 사용
        StackPane loadingPane = new StackPane(loadingImageView);
        loadingPane.setAlignment(Pos.BOTTOM_CENTER); // 하단 중앙 정렬
//        StackPane.setMargin(loadingPane, new Insets(0, 0, 30, 0)); // 기존 마진 설정

        // Y 축 위치를 추가로 조정하여 더 아래로 이동시키기
        loadingPane.setTranslateY(100); // 양수를 줄수록 아래로 내려감


        // loadingPane을 root에 추가
        root.getChildren().add(loadingPane);

        // 페이드 인
        FadeTransition fadeInLoading = new FadeTransition(Duration.seconds(0.5), loadingImageView);
        fadeInLoading.setFromValue(0);
        fadeInLoading.setToValue(1);

        fadeInLoading.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(4.8));
            pause.setOnFinished(e -> {
                // loadingPane을 제거해야 합니다.
                root.getChildren().remove(loadingPane);
                applyGrayScaleAndShowScores(root);
            });
            pause.play();
        });
        fadeInLoading.play();
    }



    private void applyGrayScaleAndShowScores(StackPane root) {
        applyGrayScale(choice1ImageView);
        applyGrayScale(choice2ImageView);
        applyGrayScale(choice3ImageView);
        applyGrayScale(choice4ImageView);

        addScoreLabelToPane(choice1Pane, "+1");
        addScoreLabelToPane(choice2Pane, "+0");
        addScoreLabelToPane(choice3Pane, "+3");
        addScoreLabelToPane(choice4Pane, "+2");

        addNavigationArrow(root);
    }

    private void addNavigationArrow(StackPane root) {
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);

        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 50, 0));

        nextArrow.setOnMouseEntered(event -> nextArrow.setCursor(Cursor.HAND));
        nextArrow.setOnMouseExited(event -> nextArrow.setCursor(Cursor.DEFAULT));
        nextArrow.setOnMouseClicked(event -> {
            root.getChildren().clear();
            prepareNextScene(root);
        });

        root.getChildren().add(nextArrow);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);
        fadeInArrow.play();
    }


    // 다음 씬을 준비하는 메서드
    private void prepareNextScene(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/SerPage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 스토리 레이블 설정
        Label storyLabel = new Label("당신의 데뷔탕트 파트너가 에스코트하러 와 있습니다");
        storyLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );
        storyLabel.setFont(Font.font(customFont.getFamily(), 30));
        storyLabel.setOpacity(0);

        // 대사 프레임 설정
        storyFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/storyframe.png")));
        storyFrame.setFitWidth(1352);
        storyFrame.setFitHeight(392);
        storyFrame.setOpacity(0);
        StackPane.setAlignment(storyFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(storyFrame, new Insets(0, 0, 50, 0)); // 아래쪽 50px 마진

        // 대사 레이블 설정
        Label dialogLabel = new Label("안녕하십니까. 공주님 뵙게되어 영광입니다.");
        dialogLabel.setStyle("-fx-text-fill: black;");
        dialogLabel.setFont(Font.font(customFont.getFamily(), 35));
        dialogLabel.setOpacity(0);

        // 대사 레이블을 storyFrame 위에 배치
        StackPane.setAlignment(dialogLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(dialogLabel, new Insets(0, 0, 200, 0)); // 아래쪽 200px 마진

        // 화살표 이미지 설정
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);
        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 150, 0));

        // 이벤트 추가
        nextArrow.setOnMouseEntered(event -> nextArrow.setCursor(Cursor.HAND));
        nextArrow.setOnMouseExited(event -> nextArrow.setCursor(Cursor.DEFAULT));
        nextArrow.setOnMouseClicked(event -> displayChoice(root));

        // 요소 추가 순서 확인
        root.getChildren().addAll(newBackground, storyLabel, storyFrame, dialogLabel, nextArrow);

        // 스토리 레이블 페이드 인 (1초)
        FadeTransition fadeInStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeInStoryLabel.setFromValue(0);
        fadeInStoryLabel.setToValue(1);

        // 스토리 레이블 유지 시간 (5초)
        PauseTransition pauseStoryLabel = new PauseTransition(Duration.seconds(3));

        // 스토리 레이블 페이드 아웃 (1초)
        FadeTransition fadeOutStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeOutStoryLabel.setFromValue(1);
        fadeOutStoryLabel.setToValue(0);
        // 애니메이션 설정 및 실행
        FadeTransition fadeInStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeInStoryFrame.setFromValue(0);
        fadeInStoryFrame.setToValue(1);

        FadeTransition fadeInDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeInDialog.setFromValue(0);
        fadeInDialog.setToValue(1);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);

        // 애니메이션 순서 제어
        fadeInStoryLabel.setOnFinished(event -> pauseStoryLabel.play());
        pauseStoryLabel.setOnFinished(event -> fadeOutStoryLabel.play());
        fadeOutStoryLabel.setOnFinished(event -> {
            fadeInStoryFrame.play();
            fadeInDialog.play();
            fadeInArrow.play();
        });

        // 스토리 레이블 애니메이션 실행
        fadeInStoryLabel.play();
    }


    private void displayChoice(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/SerPage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 배경 이미지를 가장 먼저 추가하여 모든 요소들 뒤에 위치하도록 설정
        root.getChildren().add(newBackground); // 인덱스 0으로 추가하여 가장 뒤에 배치

        // 선택지 레이블 설정 및 스타일 적용
        Label choiceLabel = new Label("파트너 (그)는 누구인가요?");
        choiceLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +  // 검정색 배경
                        "-fx-background-radius: 10;" +    // 둥근 모서리
                        "-fx-padding: 10;"                // 텍스트 주변 여백
        );
        choiceLabel.setFont(Font.font(customFont.getFamily(), 30));
        choiceLabel.setOpacity(1);

        // 선택지 이미지 설정
        choice1ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/세라핀 카르모어.png")));
        choice2ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/다리엘 블랙번.png")));
        choice3ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/아쉬엘 리베르.png")));
        choice4ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/엘리오르 라브란트.png")));

        // 선택지 이미지 크기 조정
        double choiceHeight = 485;
        double choiceWidth1 = 328;
        double choiceWidth2 = 203;
        double choiceWidth3 = 361;
        double choiceWidth4 = 355;

        choice1ImageView.setFitWidth(choiceWidth1);
        choice1ImageView.setFitHeight(choiceHeight);
        choice2ImageView.setFitWidth(choiceWidth2);
        choice2ImageView.setFitHeight(choiceHeight);
        choice3ImageView.setFitWidth(choiceWidth3);
        choice3ImageView.setFitHeight(choiceHeight);
        choice4ImageView.setFitWidth(choiceWidth4);
        choice4ImageView.setFitHeight(choiceHeight);

        // 각 선택지 감싸기
        choice1Pane = new StackPane(choice1ImageView);
        choice2Pane = new StackPane(choice2ImageView);
        choice3Pane = new StackPane(choice3ImageView);
        choice4Pane = new StackPane(choice4ImageView);

        // HBox에 네 개의 선택지 배치
        HBox choicesHBox = new HBox(20, choice1Pane, choice2Pane, choice3Pane, choice4Pane);
        choicesHBox.setAlignment(Pos.CENTER);

        // VBox로 choiceLabel과 선택지 HBox를 배치
        VBox layout = new VBox(50, choiceLabel, choicesHBox);
        layout.setAlignment(Pos.CENTER);

        // 선택지 VBox를 root에 추가
        root.getChildren().add(layout);

        // 페이드 인 애니메이션
        FadeTransition fadeInChoicesHBox = new FadeTransition(Duration.seconds(1), choicesHBox);
        fadeInChoicesHBox.setFromValue(0);
        fadeInChoicesHBox.setToValue(1);

        fadeInChoicesHBox.setOnFinished(event -> showsLoadingGif(root));
        fadeInChoicesHBox.play();
    }


    private void showsLoadingGif(StackPane root) {
        ImageView loadingImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        loadingImageView.setFitWidth(500);
        loadingImageView.setFitHeight(300);
        loadingImageView.setOpacity(0);

        // loading.gif를 중앙에 배치하기 위해 StackPane 사용
        StackPane loadingPane = new StackPane(loadingImageView);
        loadingPane.setAlignment(Pos.BOTTOM_CENTER); // 하단 중앙 정렬
//        StackPane.setMargin(loadingPane, new Insets(0, 0, 30, 0)); // 기존 마진 설정

        // Y 축 위치를 추가로 조정하여 더 아래로 이동시키기
        loadingPane.setTranslateY(50); // 양수를 줄수록 아래로 내려감


        // loadingPane을 root에 추가
        root.getChildren().add(loadingPane);

        // 페이드 인
        FadeTransition fadeInLoading = new FadeTransition(Duration.seconds(0.5), loadingImageView);
        fadeInLoading.setFromValue(0);
        fadeInLoading.setToValue(1);

        fadeInLoading.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(4.8));
            pause.setOnFinished(e -> {
                // loadingPane을 제거해야 합니다.
                root.getChildren().remove(loadingPane);
                applyGrayScaleAndShowScore(root);
            });
            pause.play();
        });
        fadeInLoading.play();
    }

    private void applyGrayScaleAndShowScore(StackPane root) {
        applyGrayScale(choice1ImageView);
        applyGrayScale(choice2ImageView);
        applyGrayScale(choice3ImageView);
        applyGrayScale(choice4ImageView);

        addScoreLabelToPane(choice1Pane, "+2");
        addScoreLabelToPane(choice2Pane, "+1");
        addScoreLabelToPane(choice3Pane, "+0");
        addScoreLabelToPane(choice4Pane, "+3");

        appNavigationArrow(root);
    }

    private void appNavigationArrow(StackPane root) {
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);

        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 50, 0));

        nextArrow.setOnMouseEntered(event -> nextArrow.setCursor(Cursor.HAND));
        nextArrow.setOnMouseExited(event -> nextArrow.setCursor(Cursor.DEFAULT));
        nextArrow.setOnMouseClicked(event -> {
            root.getChildren().clear();
            preNextScene(root);
        });

        root.getChildren().add(nextArrow);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);
        fadeInArrow.play();
    }

    private void preNextScene(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/SerPage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        root.getChildren().add(newBackground); // 인덱스 0으로 추가하여 가장 뒤에 배치

        // 대사 프레임 설정
        ImageView storyFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/storyframe.png")));
        storyFrame.setFitWidth(1352);
        storyFrame.setFitHeight(392);
        storyFrame.setOpacity(0);
        StackPane.setAlignment(storyFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(storyFrame, new Insets(0, 0, 50, 0));

        // 첫 번째 대사 레이블 설정
        Label dialogLabel = new Label("(주인공)저도 영광입니다. 오늘 하루 잘 부탁드려요.");
        dialogLabel.setStyle("-fx-text-fill: black;");
        dialogLabel.setFont(Font.font(customFont.getFamily(), 35));
        dialogLabel.setOpacity(0);
        StackPane.setAlignment(dialogLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(dialogLabel, new Insets(0, 0, 200, 0));

        // 화살표 이미지 설정
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);
        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 150, 0));

        // 화살표 클릭 이벤트 - preNextScene2로 이동
        nextArrow.setOnMouseClicked(event -> preNextScene2(root));

        // root에 요소 추가 (중요!)
        root.getChildren().addAll(storyFrame, dialogLabel, nextArrow);

        // 애니메이션 설정
        FadeTransition fadeInStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeInStoryFrame.setFromValue(0);
        fadeInStoryFrame.setToValue(1);

        FadeTransition fadeInDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeInDialog.setFromValue(0);
        fadeInDialog.setToValue(1);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);

        // 애니메이션 순서 제어
        fadeInStoryFrame.setOnFinished(event -> fadeInDialog.play());
        fadeInDialog.setOnFinished(event -> fadeInArrow.play());

        // 애니메이션 시작
        fadeInStoryFrame.play();
    }


    private void preNextScene2(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/DancePage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 첫 번째 스토리 레이블 설정 (대사 프레임 없이 표시)
        Label storyLabel = new Label("파트너와 함께 데뷔탕트에 온 당신. 당신에게 춤을 신청하는 사람이 있습니다");
        storyLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );
        storyLabel.setFont(Font.font(customFont.getFamily(), 30));
        storyLabel.setOpacity(0);
        StackPane.setAlignment(storyLabel, Pos.CENTER); // 화면 중앙에 배치

        // 대사 프레임 설정 (dialogLabel과 함께 표시됨)
        ImageView storyFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/storyframe.png")));
        storyFrame.setFitWidth(1352);
        storyFrame.setFitHeight(392);
        storyFrame.setOpacity(0);
        StackPane.setAlignment(storyFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(storyFrame, new Insets(0, 0, 50, 0));

        // 두 번째 대사 레이블 설정 (storyFrame과 함께 표시됨)
        Label dialogLabel = new Label("제게 공주님과 춤 출 수 있는 영광을 주시겠습니까?");
        dialogLabel.setStyle("-fx-text-fill: black;");
        dialogLabel.setFont(Font.font(customFont.getFamily(), 35));
        dialogLabel.setOpacity(0);
        StackPane.setAlignment(dialogLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(dialogLabel, new Insets(0, 0, 200, 0));

        // 화살표 설정
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);
        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 150, 0));

        // 화살표 클릭 이벤트 - 선택지 표시
        nextArrow.setOnMouseClicked(event -> displayChoose(root));

        // 요소 추가 순서 확인
        root.getChildren().addAll(newBackground, storyLabel, storyFrame, dialogLabel, nextArrow);

        // 첫 번째 스토리 레이블 애니메이션 설정
        FadeTransition fadeInStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeInStoryLabel.setFromValue(0);
        fadeInStoryLabel.setToValue(1);

        PauseTransition pauseStoryLabel = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOutStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeOutStoryLabel.setFromValue(1);
        fadeOutStoryLabel.setToValue(0);

        // 두 번째 대사와 대사 프레임 애니메이션 설정
        FadeTransition fadeInStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeInStoryFrame.setFromValue(0);
        fadeInStoryFrame.setToValue(1);

        FadeTransition fadeInDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeInDialog.setFromValue(0);
        fadeInDialog.setToValue(1);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);

        // 애니메이션 순서 제어
        fadeInStoryLabel.setOnFinished(event -> pauseStoryLabel.play());
        pauseStoryLabel.setOnFinished(event -> fadeOutStoryLabel.play());
        fadeOutStoryLabel.setOnFinished(event -> {
            fadeInStoryFrame.play();
            fadeInDialog.play();
            fadeInArrow.play();
        });

        // 첫 번째 스토리 레이블 애니메이션 시작
        fadeInStoryLabel.play();
    }

    private void displayChoose(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/DancePage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 배경 이미지를 가장 먼저 추가하여 모든 요소들 뒤에 위치하도록 설정
        root.getChildren().add(newBackground); // 인덱스 0으로 추가하여 가장 뒤에 배치

        // 선택지 레이블 설정 및 스타일 적용
        Label choiceLabel = new Label("당신과 처음으로 춤을 추는 사람은 누구?");
        choiceLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +  // 검정색 배경
                        "-fx-background-radius: 10;" +    // 둥근 모서리
                        "-fx-padding: 10;"                // 텍스트 주변 여백
        );
        choiceLabel.setFont(Font.font(customFont.getFamily(), 30));
        choiceLabel.setOpacity(1);

        // 선택지 이미지 설정
        choice1ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/카이엘 라나드.png")));
        choice2ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/에이든 로사르.png")));
        choice3ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/레온하르트 에셀린.png")));
        choice4ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/펠릭스 에라모어.png")));

        // 선택지 이미지 크기 조정
        double choiceHeight1 = 434;
        double choiceWidth1 = 389;
        double choiceHeight2 = 467;
        double choiceWidth2 = 329;
        double choiceHeight3 = 456;
        double choiceWidth3 = 309;
        double choiceHeight4 = 434;
        double choiceWidth4 = 309;

        choice1ImageView.setFitWidth(choiceWidth1);
        choice1ImageView.setFitHeight(choiceHeight1);
        choice1ImageView.setPreserveRatio(true);

        choice2ImageView.setFitWidth(choiceWidth2);
        choice2ImageView.setFitHeight(choiceHeight2);
        choice2ImageView.setPreserveRatio(true);

        choice3ImageView.setFitWidth(choiceWidth3);
        choice3ImageView.setFitHeight(choiceHeight3);
        choice3ImageView.setPreserveRatio(true);

        choice4ImageView.setFitWidth(choiceWidth4);
        choice4ImageView.setFitHeight(choiceHeight4);
        choice4ImageView.setPreserveRatio(true);

        // 각 선택지 감싸기
        choice1Pane = new StackPane(choice1ImageView);
        choice2Pane = new StackPane(choice2ImageView);
        choice3Pane = new StackPane(choice3ImageView);
        choice4Pane = new StackPane(choice4ImageView);

        // HBox에 네 개의 선택지 배치
        HBox choicesHBox = new HBox(20, choice1Pane, choice2Pane, choice3Pane, choice4Pane);
        choicesHBox.setAlignment(Pos.CENTER);

        // VBox로 choiceLabel과 선택지 HBox를 배치
        VBox layout = new VBox(50, choiceLabel, choicesHBox);
        layout.setAlignment(Pos.CENTER);

        // 선택지 VBox를 root에 추가
        root.getChildren().add(layout);

        // 페이드 인 애니메이션
        FadeTransition fadeInChoicesHBox = new FadeTransition(Duration.seconds(1), choicesHBox);
        fadeInChoicesHBox.setFromValue(0);
        fadeInChoicesHBox.setToValue(1);

        fadeInChoicesHBox.setOnFinished(event -> showsloadingGif(root));
        fadeInChoicesHBox.play();

    }

    private void showsloadingGif(StackPane root) {
        ImageView loadingImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        loadingImageView.setFitWidth(500);
        loadingImageView.setFitHeight(300);
        loadingImageView.setOpacity(0);

        // loading.gif를 중앙에 배치하기 위해 StackPane 사용
        StackPane loadingPane = new StackPane(loadingImageView);
        loadingPane.setAlignment(Pos.BOTTOM_CENTER); // 하단 중앙 정렬
//        StackPane.setMargin(loadingPane, new Insets(0, 0, 30, 0)); // 기존 마진 설정

        // Y 축 위치를 추가로 조정하여 더 아래로 이동시키기
        loadingPane.setTranslateY(50); // 양수를 줄수록 아래로 내려감


        // loadingPane을 root에 추가
        root.getChildren().add(loadingPane);

        // 페이드 인
        FadeTransition fadeInLoading = new FadeTransition(Duration.seconds(0.5), loadingImageView);
        fadeInLoading.setFromValue(0);
        fadeInLoading.setToValue(1);

        fadeInLoading.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(4.8));
            pause.setOnFinished(e -> {
                // loadingPane을 제거해야 합니다.
                root.getChildren().remove(loadingPane);
                applyGrayScaleAndShowscore(root);
            });
            pause.play();
        });
        fadeInLoading.play();
    }

    private void applyGrayScaleAndShowscore(StackPane root) {
        applyGrayScale(choice1ImageView);
        applyGrayScale(choice2ImageView);
        applyGrayScale(choice3ImageView);
        applyGrayScale(choice4ImageView);

        addScoreLabelToPane(choice1Pane, "+4");
        addScoreLabelToPane(choice2Pane, "+2");
        addScoreLabelToPane(choice3Pane, "+1");
        addScoreLabelToPane(choice4Pane, "+3");

        appNavigationarrow(root);
    }

    private void appNavigationarrow(StackPane root) {
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);

        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 50, 0));

        nextArrow.setOnMouseEntered(event -> nextArrow.setCursor(Cursor.HAND));
        nextArrow.setOnMouseExited(event -> nextArrow.setCursor(Cursor.DEFAULT));
        nextArrow.setOnMouseClicked(event -> {
            root.getChildren().clear();
            prenextScene(root);
        });

        root.getChildren().add(nextArrow);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);
        fadeInArrow.play();
    }

    private void prenextScene(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/endPage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 첫 번째 스토리 레이블 설정 (대사 프레임 없이 표시)
        Label storyLabel = new Label("춤을 춘 남주와 함께 바람쐐러 정원으로 나온 당신");
        storyLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );
        storyLabel.setFont(Font.font(customFont.getFamily(), 30));
        storyLabel.setOpacity(0);
        StackPane.setAlignment(storyLabel, Pos.CENTER); // 화면 중앙에 배치

        // 두 번째 스토리 레이블 설정 (대사 프레임 없이 표시)
        Label storyLabel2 = new Label("남주가 당신에게 무언가를 건넵니다");
        storyLabel2.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );
        storyLabel2.setFont(Font.font(customFont.getFamily(), 30));
        storyLabel2.setOpacity(0);
        StackPane.setAlignment(storyLabel2, Pos.CENTER); // 화면 중앙에 배치

        // 대사 프레임 설정 (dialogLabel과 함께 표시됨)
        ImageView storyFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/storyframe.png")));
        storyFrame.setFitWidth(1352);
        storyFrame.setFitHeight(392);
        storyFrame.setOpacity(0);
        StackPane.setAlignment(storyFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(storyFrame, new Insets(0, 0, 50, 0));

        // 두 번째 대사 레이블 설정 (storyFrame과 함께 표시됨)
        Label dialogLabel = new Label("공주님께 드리는 제 마음입니다.");
        dialogLabel.setStyle("-fx-text-fill: black;");
        dialogLabel.setFont(Font.font(customFont.getFamily(), 35));
        dialogLabel.setOpacity(0);
        StackPane.setAlignment(dialogLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(dialogLabel, new Insets(0, 0, 200, 0));

        // 화살표 설정
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);
        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 150, 0));

        // 화살표 클릭 이벤트 - 선택지 표시
        nextArrow.setOnMouseClicked(event -> displayChooses(root));

        // 요소 추가 순서 확인
        root.getChildren().addAll(newBackground, storyLabel, storyLabel2, storyFrame, dialogLabel, nextArrow);

        // 첫 번째 스토리 레이블 애니메이션 설정
        FadeTransition fadeInStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeInStoryLabel.setFromValue(0);
        fadeInStoryLabel.setToValue(1);

        PauseTransition pauseStoryLabel = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOutStoryLabel = new FadeTransition(Duration.seconds(1), storyLabel);
        fadeOutStoryLabel.setFromValue(1);
        fadeOutStoryLabel.setToValue(0);

        // 두 번째 스토리 레이블 애니메이션 설정
        FadeTransition fadeInStoryLabel2 = new FadeTransition(Duration.seconds(1), storyLabel2);
        fadeInStoryLabel2.setFromValue(0);
        fadeInStoryLabel2.setToValue(1);

        PauseTransition pauseStoryLabel2 = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOutStoryLabel2 = new FadeTransition(Duration.seconds(1), storyLabel2);
        fadeOutStoryLabel2.setFromValue(1);
        fadeOutStoryLabel2.setToValue(0);

        // 두 번째 대사와 대사 프레임 애니메이션 설정
        FadeTransition fadeInStoryFrame = new FadeTransition(Duration.seconds(1), storyFrame);
        fadeInStoryFrame.setFromValue(0);
        fadeInStoryFrame.setToValue(1);

        FadeTransition fadeInDialog = new FadeTransition(Duration.seconds(1), dialogLabel);
        fadeInDialog.setFromValue(0);
        fadeInDialog.setToValue(1);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);

        // 애니메이션 순서 제어
        fadeInStoryLabel.setOnFinished(event -> {
            pauseStoryLabel.play();
        });
        pauseStoryLabel.setOnFinished(event -> {
            fadeOutStoryLabel.play();
        });
        fadeOutStoryLabel.setOnFinished(event -> {
            fadeInStoryLabel2.play();
        });
        fadeInStoryLabel2.setOnFinished(event -> {
            pauseStoryLabel2.play();
        });
        pauseStoryLabel2.setOnFinished(event -> {
            fadeOutStoryLabel2.play();
        });
        fadeOutStoryLabel2.setOnFinished(event -> {
            fadeInStoryFrame.play();
            fadeInDialog.play();
            fadeInArrow.play();
        });

        // 첫 번째 스토리 레이블 애니메이션 시작
        fadeInStoryLabel.play();
    }

    private void displayChooses(StackPane root) {
        // root 초기화 및 새로운 배경 이미지 추가
        root.getChildren().clear();

        // 새로운 배경 이미지 설정
        ImageView newBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/endPage.png")));
        newBackground.setFitWidth(1440);
        newBackground.setFitHeight(1024);

        // 배경 이미지를 가장 먼저 추가하여 모든 요소들 뒤에 위치하도록 설정
        root.getChildren().add(newBackground); // 인덱스 0으로 추가하여 가장 뒤에 배치

        // 선택지 레이블 설정 및 스타일 적용
        Label choiceLabel = new Label("남주에게 선물로 받고싶은 꽃을 선택하세요");
        choiceLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.8);" +  // 검정색 배경
                        "-fx-background-radius: 10;" +    // 둥근 모서리
                        "-fx-padding: 10;"                // 텍스트 주변 여백
        );
        choiceLabel.setFont(Font.font(customFont.getFamily(), 30));
        choiceLabel.setOpacity(1);

        // 선택지 이미지 설정
        choice1ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/flower1.png")));
        choice2ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/flower2.png")));
        choice3ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/flower3.png")));
        choice4ImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/flower4.png")));

        // 선택지 이미지 크기 조정
        double choiceHeight1 = 335;
        double choiceWidth1 = 322;
        double choiceHeight2 = 328;
        double choiceWidth2 = 295;
        double choiceHeight3 = 320;
        double choiceWidth3 = 346;
        double choiceHeight4 = 337;
        double choiceWidth4 = 327;

        choice1ImageView.setFitWidth(choiceWidth1);
        choice1ImageView.setFitHeight(choiceHeight1);
        choice1ImageView.setPreserveRatio(true);

        choice2ImageView.setFitWidth(choiceWidth2);
        choice2ImageView.setFitHeight(choiceHeight2);
        choice2ImageView.setPreserveRatio(true);

        choice3ImageView.setFitWidth(choiceWidth3);
        choice3ImageView.setFitHeight(choiceHeight3);
        choice3ImageView.setPreserveRatio(true);

        choice4ImageView.setFitWidth(choiceWidth4);
        choice4ImageView.setFitHeight(choiceHeight4);
        choice4ImageView.setPreserveRatio(true);

        // 각 선택지 감싸기
        choice1Pane = new StackPane(choice1ImageView);
        choice2Pane = new StackPane(choice2ImageView);
        choice3Pane = new StackPane(choice3ImageView);
        choice4Pane = new StackPane(choice4ImageView);

        // 상단 HBox에 두 개의 선택지 배치
        HBox topChoicesHBox = new HBox(50, choice1Pane, choice2Pane);
        topChoicesHBox.setAlignment(Pos.CENTER);

        // 하단 HBox에 두 개의 선택지 배치
        HBox bottomChoicesHBox = new HBox(50, choice3Pane, choice4Pane);
        bottomChoicesHBox.setAlignment(Pos.CENTER);

        // 선택지 전체를 VBox로 배치
        VBox choicesBox = new VBox(50, choiceLabel, topChoicesHBox, bottomChoicesHBox);
        choicesBox.setAlignment(Pos.CENTER);

        // root에 선택지 박스를 추가
        root.getChildren().add(choicesBox);

        // 페이드 인 애니메이션
        FadeTransition fadeInChoicesBox = new FadeTransition(Duration.seconds(1), choicesBox);
        fadeInChoicesBox.setFromValue(0);
        fadeInChoicesBox.setToValue(1);

        fadeInChoicesBox.setOnFinished(event -> showloadingGif(root));
        fadeInChoicesBox.play();

    }

    private void showloadingGif(StackPane root) {
        ImageView loadingImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        loadingImageView.setFitWidth(500);
        loadingImageView.setFitHeight(300);
        loadingImageView.setOpacity(0);

        // loading.gif를 중앙에 배치하기 위해 StackPane 사용
        StackPane loadingPane = new StackPane(loadingImageView);
        loadingPane.setAlignment(Pos.BOTTOM_CENTER); // 하단 중앙 정렬
//        StackPane.setMargin(loadingPane, new Insets(0, 0, 30, 0)); // 기존 마진 설정

        // Y 축 위치를 추가로 조정하여 더 아래로 이동시키기
        loadingPane.setTranslateY(100); // 양수를 줄수록 아래로 내려감


        // loadingPane을 root에 추가
        root.getChildren().add(loadingPane);

        // 페이드 인
        FadeTransition fadeInLoading = new FadeTransition(Duration.seconds(0.5), loadingImageView);
        fadeInLoading.setFromValue(0);
        fadeInLoading.setToValue(1);

        fadeInLoading.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(4.8));
            pause.setOnFinished(e -> {
                // loadingPane을 제거해야 합니다.
                root.getChildren().remove(loadingPane);
                applyGrayScaleAndshowscore(root);
            });
            pause.play();
        });
        fadeInLoading.play();
    }

    private void applyGrayScaleAndshowscore(StackPane root) {
        applyGrayScale(choice1ImageView);
        applyGrayScale(choice2ImageView);
        applyGrayScale(choice3ImageView);
        applyGrayScale(choice4ImageView);

        addScoreLabelToPane(choice1Pane, "+2");
        addScoreLabelToPane(choice2Pane, "+0");
        addScoreLabelToPane(choice3Pane, "+3");
        addScoreLabelToPane(choice4Pane, "+1");

        appnavigationarrow(root); // root만 전달
    }

    private void appnavigationarrow(StackPane root) {
        ImageView nextArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/Vector.png")));
        nextArrow.setFitWidth(30);
        nextArrow.setFitHeight(25);
        nextArrow.setOpacity(0);

        StackPane.setAlignment(nextArrow, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextArrow, new Insets(0, 0, 50, 0));

        nextArrow.setOnMouseClicked(event -> {
            gameUI.goToResultPage(); // GameUI를 통해 ResultPage로 이동
            event.consume();
        });

        root.getChildren().add(nextArrow);

        FadeTransition fadeInArrow = new FadeTransition(Duration.seconds(1), nextArrow);
        fadeInArrow.setFromValue(0);
        fadeInArrow.setToValue(1);
        fadeInArrow.play();
    }



}
