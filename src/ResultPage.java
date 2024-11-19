import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;  // 추가
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.web.WebView;  // 추가
import javafx.scene.web.WebEngine;  // 추가


public class ResultPage {
    private Stage stage;
    private GameUI gameUI; // GameUI 클래스 참조 추가

    public ResultPage(Stage stage, GameUI gameUI) {
        this.stage = stage;
        this.gameUI = gameUI;
    }

    public void displayResultPage() {
        // 배경 이미지 설정 및 페이드 인 효과
        ImageView backgroundImageView = new ImageView(ImageLoader.loadImage("end-background.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(1024);

        FadeTransition fadeInBackground = new FadeTransition(Duration.seconds(2), backgroundImageView);
        fadeInBackground.setFromValue(0);
        fadeInBackground.setToValue(1);

        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);

        // 결과 텍스트 설정
        Label resultText = new Label("행복한 결말을 맞이하셨습니다. 당신의 결과를 확인해주세요");
        resultText.setFont(Font.font(customFont.getFamily(), 40));
        resultText.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.5);" +  // 검정색 배경, 선명도 50%
                        "-fx-background-radius: 10;" +                  // Border radius 10
                        "-fx-padding: 10;"                              // 텍스트 주변 여백
        );

        // 선택지 버튼 설정
        Label option1 = createOptionLabel("웹툰 결과 보기");
        option1.setOnMouseClicked(event -> goToWebtoonPage());

        Label option2 = createOptionLabel("인물 소개");
        option2.setOnMouseClicked(event -> goToCharacterIntroPage());

        // '처음으로 돌아가기' 설정
        Label backToStart = new Label("처음으로 돌아가기");
        backToStart.setFont(customFont);
        backToStart.setStyle("-fx-text-fill: white;");
        backToStart.setOnMouseEntered(e -> backToStart.setStyle("-fx-text-fill: blue;"));
        backToStart.setOnMouseExited(e -> backToStart.setStyle("-fx-text-fill: white;"));
        backToStart.setOnMouseClicked(event -> gameUI.initializeUI());

        // 선택지와의 간격 설정
        HBox optionsBox = new HBox(100, wrapInStackPane(option1), wrapInStackPane(option2));
        optionsBox.setAlignment(Pos.CENTER); // 중앙 정렬

        // 레이아웃을 설정하고 resultText와 optionsBox 사이에 간격을 추가
        VBox layout = new VBox(40, resultText, optionsBox, backToStart);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(optionsBox, new javafx.geometry.Insets(70, 0, 0, 0)); // 위쪽 여백 70

        StackPane root = new StackPane(backgroundImageView, layout);
        StackPane.setAlignment(layout, Pos.CENTER);

        Scene scene = new Scene(root, 1440, 1024);
        stage.setScene(scene); // Stage에 새로운 Scene 설정
        fadeInBackground.play();
    }

    private StackPane wrapInStackPane(Label label) {
        ImageView background = new ImageView(ImageLoader.loadImage("font-background.png"));
        background.setFitWidth(344);
        background.setFitHeight(207);

        StackPane stackPane = new StackPane(background, label);
        stackPane.setAlignment(Pos.CENTER); // 중앙 정렬
        return stackPane;
    }

    private Label createOptionLabel(String text) {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);

        Label label = new Label(text);
        label.setFont(customFont);
        label.setStyle("-fx-text-fill: black;");
        label.setAlignment(Pos.CENTER); // 라벨 안의 텍스트 정렬
        label.setOnMouseEntered(e -> label.setStyle("-fx-text-fill: blue;"));
        label.setOnMouseExited(e -> label.setStyle("-fx-text-fill: black;"));
        label.setOnMouseClicked(e -> System.out.println(text + " 클릭됨"));
        return label;
    }

    private int currentPageIndex = 0; // 현재 페이지 인덱스

    private void goToWebtoonPage() {
        // 페이지에 따른 이미지 배열 설정
        String[][] webtoonImages = {
                {"/images/재혼황후.png", "/images/사실은 내가 진짜였다.png"},
                {"/images/백작가의 망나니가 되었다.png", "/images/샬롯에게는 다섯명의 제자가있다.png"},
                {"/images/베이비 폭군.png", "/images/이번 생은 가주가 되겠습니다.png"},
                {"/images/시한부인 줄 알았어요.png", "/images/아빠 나 이 결혼 안 할래요.png"}
        };

        // 절대 경로로 웹 링크 설정
        String[][] webtoonLinks = {
                {"https://comic.naver.com/webtoon/list?titleId=735661", "https://page.kakao.com/content/55721630"},
                {"https://page.kakao.com/content/55553244", "https://page.kakao.com/content/53666782"},
                {"https://comic.naver.com/webtoon/list?titleId=807019", "https://page.kakao.com/content/56566288"},
                {"https://comic.naver.com/webtoon/list?titleId=801711", "https://page.kakao.com/content/56556599"}
        };


        // 각 페이지별 점수 텍스트 설정
        String[] scoreTexts = {
                "1~4점 사이(복수물)",
                "5~8점 사이(모험물)",
                "9~12점 사이(육아물)",
                "13~16점 사이(로맨스물)"
        };

        // 각 설명 텍스트 설정
        String[] genreDescriptions = {
                "복수물: 당신은 복수를 꿈꾸는 정열가의 스토리와 잘 어울립니다.\n" +
                        "초반은 다소 슬픈 이야기로 시작하지만 이야기를 진행할수록\n" +
                        "악한자들을 정의실현을 하는 아주 시원한 전개의 이야기 입니다.\n\n" +
                        "냉미남 남주: 말수가 적고 첫인상이 차가워 보이는 냉미남.\n" +
                        "하지만 시간이 지날수록 당신에게 마냥 차갑게만 느껴지지 않는데..\n" +
                        "#여주에게만 따뜻",
                "모험물: 당신은 목표를 갖고 세상을 탐험하는 스토리와 잘 어울립니다.\n" +
                        "여행을 떠나며 새로운 만남과 사건을 조우하는 흥미로운 이야기입니다.\n\n" +
                        "장난끼가 많은 남주: 당신을 볼 때마다 엉뚱한 장난치고\n" +
                        "당신의 반응을 귀여워하는 남주. 가벼워 보이는 행동이 알고 보니 여주에게만?\n" +
                        "#일편단심",
                "육아물: 당신은 어린아이부터 시작하는 통칭 ‘육아물’ 스토리와 잘 어울립니다.\n" +
                        "가족과 주변 사람들에게 사랑을 받으며 성장하는 과정에 생기는 사건들을\n" +
                        "다룬 이야기로 아주 힐링되는 이야기입니다.\n\n" +
                        "따뜻하고 친절한 남주: 바라만 보기만 해도 마음이 따뜻해지는 햇살남주!\n" +
                        "하지만 마냥 착하기만 한 성격은 아니었는데..\n" +
                        "#계략남주",
                "로맨스물: 당신은 운명적인 상대와 만나는 로맨스 스토리와 잘 어울립니다.\n" +
                        "여러 사건사고가 당신을 힘들게 하지만 당신의 든든한 아군이 운명적 상대와\n" +
                        "함께 역경을 이겨나가는 로맨스 성장 이야기입니다.\n\n" +
                        "매혹적인 매력을 가진 남주: 알쏭달쏭 미스테리하고 위한한 매력미가 있는 남주.\n" +
                        "하지만 여주에게는 솔직하게 애정을 표현하는데..\n" +
                        "#나쁜남자의 매력"
        };

        // 새로운 웹툰 결과 페이지를 설정 (배경 포함)
        ImageView backgroundImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/WebtoonPage.png")));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(1024);

        // 웹툰 이미지 설정 (현재 페이지 인덱스에 따라 이미지를 변경)
        ImageView webtoonImage1 = new ImageView(new Image(getClass().getResourceAsStream(webtoonImages[currentPageIndex][0])));
        webtoonImage1.setFitWidth(600);
        webtoonImage1.setFitHeight(600);
        webtoonImage1.setOnMouseEntered(e -> webtoonImage1.setStyle("-fx-cursor: hand;"));
        webtoonImage1.setOnMouseExited(e -> webtoonImage1.setStyle("-fx-cursor: default;"));
        webtoonImage1.setOnMouseClicked(e -> openWebPage(webtoonLinks[currentPageIndex][0]));

        ImageView webtoonImage2 = new ImageView(new Image(getClass().getResourceAsStream(webtoonImages[currentPageIndex][1])));
        webtoonImage2.setFitWidth(600);
        webtoonImage2.setFitHeight(600);
        webtoonImage2.setOnMouseEntered(e -> webtoonImage2.setStyle("-fx-cursor: hand;"));
        webtoonImage2.setOnMouseExited(e -> webtoonImage2.setStyle("-fx-cursor: default;"));
        webtoonImage2.setOnMouseClicked(e -> openWebPage(webtoonLinks[currentPageIndex][1]));

        // 왼쪽 화살표 설정
        ImageView leftArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/left.png")));
        leftArrow.setFitWidth(30);
        leftArrow.setFitHeight(45);
        StackPane.setAlignment(leftArrow, Pos.CENTER_LEFT);
        StackPane.setMargin(leftArrow, new javafx.geometry.Insets(0, 0, 0, 70)); // 왼쪽에서 70px 떨어짐
        leftArrow.setOnMouseClicked(eventLeft -> {
            currentPageIndex = (currentPageIndex - 1 + webtoonImages.length) % webtoonImages.length;
            goToWebtoonPage();
        });

        // 오른쪽 화살표 설정
        ImageView rightArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/right.png")));
        rightArrow.setFitWidth(30);
        rightArrow.setFitHeight(45);
        StackPane.setAlignment(rightArrow, Pos.CENTER_RIGHT);
        StackPane.setMargin(rightArrow, new javafx.geometry.Insets(0, 70, 0, 0)); // 오른쪽에서 70px 떨어짐
        rightArrow.setOnMouseClicked(eventRight -> {
            currentPageIndex = (currentPageIndex + 1) % webtoonImages.length;
            goToWebtoonPage();
        });

        // 결과 텍스트 설정
        Label scoreText = new Label(scoreTexts[currentPageIndex]);
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);
        scoreText.setFont(Font.font(customFont.getFamily(), 40));
        scoreText.setStyle(
                "-fx-text-fill: white;" +  // 기본 텍스트는 흰색
                        "-fx-background-color: rgba(0, 0, 0, 0.5);" +  // 검정색 배경, 투명도 50%
                        "-fx-background-radius: 10;" +                  // 둥근 모서리 10px
                        "-fx-padding: 10;"                              // 텍스트 주변 여백
        );
        StackPane.setAlignment(scoreText, Pos.TOP_CENTER);
        StackPane.setMargin(scoreText, new javafx.geometry.Insets(100, 0, 0, 0)); // 위쪽에서 100px 떨어짐

        // 설명 박스 설정 (기본적으로 숨김)
        Label genreDescriptionLabel = new Label(genreDescriptions[currentPageIndex]);
        genreDescriptionLabel.setFont(Font.font(customFont.getFamily(), 20));
        genreDescriptionLabel.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: rgba(255, 255, 255, 0.8);" +  // 흰색 배경, 투명도 80%
                        "-fx-background-radius: 10;" +                      // 둥근 모서리 10px
                        "-fx-padding: 10;"                                  // 텍스트 주변 여백
        );
        genreDescriptionLabel.setPrefWidth(680);
        genreDescriptionLabel.setPrefHeight(280);
        genreDescriptionLabel.setWrapText(true);
        genreDescriptionLabel.setVisible(false);  // 기본적으로 숨김 상태
        StackPane.setAlignment(genreDescriptionLabel, Pos.CENTER);
        StackPane.setMargin(genreDescriptionLabel, new javafx.geometry.Insets(0, 0, 0, 0)); // 중앙 위치

        // 결과 텍스트 hover 이벤트 설정
        scoreText.setOnMouseEntered(e -> genreDescriptionLabel.setVisible(true));
        scoreText.setOnMouseExited(e -> genreDescriptionLabel.setVisible(false));

        // 두 이미지 간의 간격을 유지하고 중앙 정렬하기 위한 HBox 사용
        HBox webtoonBox = new HBox(40, webtoonImage1, webtoonImage2);
        webtoonBox.setAlignment(Pos.CENTER);

        // '이전으로 돌아가기' 텍스트 설정
        Label backToResultPage = new Label("이전으로 돌아가기");
        backToResultPage.setFont(customFont);
        backToResultPage.setStyle("-fx-text-fill: white;");
        backToResultPage.setOnMouseEntered(e -> backToResultPage.setStyle("-fx-text-fill: blue;"));
        backToResultPage.setOnMouseExited(e -> backToResultPage.setStyle("-fx-text-fill: white;"));
        backToResultPage.setOnMouseClicked(event -> displayResultPage());

        // 웹툰 이미지와 '이전으로 돌아가기' 텍스트를 포함하는 VBox
        VBox webtoonContent = new VBox(20, webtoonBox, backToResultPage);
        webtoonContent.setAlignment(Pos.CENTER);
        StackPane.setMargin(webtoonContent, new javafx.geometry.Insets(50, 0, 0, 0)); // 수직 가운데에서 조금 내려오도록 설정

        StackPane webtoonRoot = new StackPane(backgroundImageView, webtoonContent, leftArrow, rightArrow, scoreText, genreDescriptionLabel);
        webtoonRoot.setAlignment(Pos.CENTER);

        Scene webtoonScene = new Scene(webtoonRoot, 1440, 1024);
        stage.setScene(webtoonScene);
    }



    // WebView를 사용하여 링크 열기
    private void openWebPage(String url) {
        try {
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load(url);

            // '이전으로 돌아가기' 버튼 설정
            Label backButton = new Label("이전으로 돌아가기");
            Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);
            backButton.setFont(customFont);
            backButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
            backButton.setMinHeight(70);  // 버튼의 높이를 40px로 설정
            backButton.setMaxHeight(70);
            backButton.setMaxWidth(1440);
            backButton.setAlignment(Pos.CENTER);

            // 버튼 hover 효과
            backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: blue;"));
            backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;"));
            backButton.setOnMouseClicked(event -> goToWebtoonPage());

            // 전체 레이아웃 설정 (WebView와 버튼)
            BorderPane webViewLayout = new BorderPane();
            webViewLayout.setCenter(webView);       // WebView를 중앙에 배치하여 화면을 꽉 채움
            webViewLayout.setBottom(backButton);    // 버튼을 아래에 배치

            Scene webViewScene = new Scene(webViewLayout, 1440, 1024);
            stage.setScene(webViewScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int currentCharacterIndex = 0; // 현재 인물 페이지 인덱스

    private void goToCharacterIntroPage() {
        // 인물 소개 페이지에 따른 이미지 배열 설정
        String[] characterImages = {
                "/images/카이엘 라나드.png",
                "/images/에이든 로사르.png",
                "/images/레온하르트 에셀린.png",
                "/images/펠릭스 에라모어.png",
                "/images/세실리아 로젤린.png",
                "/images/엘로이즈 리벨렌.png",
                "/images/릴리안 에버린.png",
                "/images/이벨린 베인.png"
        };

        // 각 인물 이미지의 너비 설정
        double[] characterWidths = {
                900, 723, 703, 725, 662, 723, 649, 800
        };

        // 각 인물 이미지의 높이 설정
        double[] characterHeights = {
                1024, 1028, 1041, 1018, 1000, 1004, 1020, 1000
        };

        // 인물 이름 설정
        String[] characterNames = {
                "카이엘 라나드",
                "에이든 로사르",
                "레온하르트 에셀린",
                "펠릭스 에라모어",
                "세실리아 로젤린",
                "엘로이즈 리벨렌",
                "릴리안 에버린",
                "이벨린 베인"
        };

        // 각 인물 설명 설정
        String[] characterDescriptions = {
                "카이엘은 하늘(카이)의 뜻을 담고 있으며, 라나드는 대지의 수호자를 의미합니다. 고귀한 혈통을 상징하며, 하늘과 대지를 아우르는 강인함을 나타내는 캐릭터",
                "에이든은 용맹한 기사로서, 로사르는 장미를 뜻합니다. 그의 이름은 장미처럼 아름다우면서도 가시에 찔릴 듯한 강렬함을 상징합니다.",
                "레온하르트는 사자의 마음을 가진 자라는 의미를 가지고 있습니다. 에셀린은 순수한 영혼을 의미하며, 정의감과 용기를 동시에 지닌 인물입니다.",
                "펠릭스는 행운을 뜻하는 이름으로, 에라모어는 영원한 불꽃을 의미합니다. 항상 긍정적이고 밝은 성격을 지닌 캐릭터입니다.",
                "세실리아는 하늘의 축복을 받은 자라는 의미를 지니며, 로젤린은 장미꽃처럼 아름답고 매혹적인 인물을 상징합니다.",
                "엘로이즈는 지혜를 상징하는 이름으로, 리벨렌은 자유로운 영혼을 뜻합니다. 세상에 얽매이지 않는 강한 의지를 가진 캐릭터입니다.",
                "릴리안은 순수함을 뜻하며, 에버린은 영원히 이어지는 사랑을 의미합니다. 사랑과 우정을 소중히 여기는 따뜻한 인물입니다.",
                "이벨린은 고귀한 빛을 의미하며, 베인은 어둠을 밝히는 빛을 뜻합니다. 어둠 속에서도 희망을 잃지 않는 용기 있는 캐릭터입니다."
        };

        // 새로운 인물 소개 페이지 설정 (배경 포함)
        ImageView backgroundImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/CharacterIntroPage.png")));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(1024);

        // '이전으로 돌아가기' 텍스트 설정
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/HeirofLightRegular.ttf"), 30);
        Label backToResultPage = new Label("이전으로 돌아가기");
        backToResultPage.setFont(customFont);
        backToResultPage.setStyle("-fx-text-fill: white;");
        backToResultPage.setOnMouseEntered(e -> backToResultPage.setStyle("-fx-text-fill: blue;"));
        backToResultPage.setOnMouseExited(e -> backToResultPage.setStyle("-fx-text-fill: white;"));
        backToResultPage.setOnMouseClicked(event -> displayResultPage());

        // '이전으로 돌아가기' 버튼을 왼쪽 상단에 배치
        StackPane.setAlignment(backToResultPage, Pos.TOP_LEFT);
        StackPane.setMargin(backToResultPage, new javafx.geometry.Insets(200, 0, 0, 50)); // 위쪽에서 200px, 왼쪽에서 50px 떨어짐

        // 인물 이미지 설정 (현재 인물 인덱스에 따라 이미지를 변경)
        ImageView characterImage = new ImageView(new Image(getClass().getResourceAsStream(characterImages[currentCharacterIndex])));
        characterImage.setFitWidth(characterWidths[currentCharacterIndex]);
        characterImage.setFitHeight(characterHeights[currentCharacterIndex]);
        StackPane.setAlignment(characterImage, Pos.CENTER);  // 인물 이미지를 중앙에 배치

        // 텍스트 배경 이미지 설정
        ImageView textBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/textbackground.png")));
        textBackground.setFitWidth(1002);
        textBackground.setFitHeight(232);
        StackPane.setAlignment(textBackground, Pos.BOTTOM_CENTER);
        StackPane.setMargin(textBackground, new javafx.geometry.Insets(0, 0, 85, 0)); // 아래쪽에서 85px 떨어짐

        // 텍스트 프레임 이미지 설정
        ImageView textFrame = new ImageView(new Image(getClass().getResourceAsStream("/images/textframe.png")));
        textFrame.setFitWidth(1164);
        textFrame.setFitHeight(361);
        StackPane.setAlignment(textFrame, Pos.BOTTOM_CENTER);
        StackPane.setMargin(textFrame, new javafx.geometry.Insets(0, 0, 25, 0)); // 아래쪽에서 25px 떨어짐

        // 오른쪽 끝에 폰트 배경 이미지 설정
        ImageView fontBackground = new ImageView(new Image(getClass().getResourceAsStream("/images/font-background.png")));
        fontBackground.setFitWidth(344);
        fontBackground.setFitHeight(207);
        StackPane.setAlignment(fontBackground, Pos.CENTER_RIGHT);
        StackPane.setMargin(fontBackground, new javafx.geometry.Insets(0, 70, 500, 0)); // 오른쪽에서 70px 떨어짐

        // 인물 이름 설정 (폰트 배경 위에 위치)
        Label characterNameLabel = new Label(characterNames[currentCharacterIndex]);
        characterNameLabel.setFont(Font.font(customFont.getFamily(), 30));
        characterNameLabel.setTextFill(Color.BLACK);
        StackPane.setAlignment(characterNameLabel, Pos.CENTER_RIGHT);
        StackPane.setMargin(characterNameLabel, new javafx.geometry.Insets(0, 140, 500, 0)); // 오른쪽에서 70px 떨어짐

        // 인물 설명 설정 (텍스트 배경 위에 위치)
        Label characterDescriptionLabel = new Label(characterDescriptions[currentCharacterIndex]);
        characterDescriptionLabel.setFont(Font.font(customFont.getFamily(), 27));
        characterDescriptionLabel.setTextFill(Color.BLACK);
        characterDescriptionLabel.setWrapText(true);
        characterDescriptionLabel.setMaxWidth(980);  // 텍스트 배경의 너비에 맞춤
        StackPane.setAlignment(characterDescriptionLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(characterDescriptionLabel, new javafx.geometry.Insets(0, 0, 170, 40)); // 텍스트 배경 바로 위에 위치하도록 설정

        // 왼쪽 화살표 설정
        ImageView leftArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/left.png")));
        leftArrow.setFitWidth(30);
        leftArrow.setFitHeight(45);
        StackPane.setAlignment(leftArrow, Pos.CENTER_LEFT);
        StackPane.setMargin(leftArrow, new javafx.geometry.Insets(0, 0, 0, 50)); // 왼쪽에서 50px 떨어짐
        leftArrow.setOnMouseClicked(eventLeft -> {
            currentCharacterIndex = (currentCharacterIndex - 1 + characterImages.length) % characterImages.length;
            goToCharacterIntroPage();
        });

        // 오른쪽 화살표 설정
        ImageView rightArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/right.png")));
        rightArrow.setFitWidth(30);
        rightArrow.setFitHeight(45);
        StackPane.setAlignment(rightArrow, Pos.CENTER_RIGHT);
        StackPane.setMargin(rightArrow, new javafx.geometry.Insets(0, 50, 0, 0)); // 오른쪽에서 50px 떨어짐
        rightArrow.setOnMouseClicked(eventRight -> {
            currentCharacterIndex = (currentCharacterIndex + 1) % characterImages.length;
            goToCharacterIntroPage();
        });

        // StackPane 레이아웃 설정
        StackPane characterIntroRoot = new StackPane(
                backgroundImageView,
                characterImage,
                textBackground,
                textFrame,
                fontBackground,
                characterNameLabel,
                characterDescriptionLabel,
                backToResultPage,
                leftArrow,
                rightArrow
        );
        characterIntroRoot.setAlignment(Pos.CENTER);

        // 새로운 장면 설정
        Scene characterIntroScene = new Scene(characterIntroRoot, 1440, 1024);
        stage.setScene(characterIntroScene);
    }
}
