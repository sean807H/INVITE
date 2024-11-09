import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ResultPage {

    private Stage stage;

    public ResultPage(Stage stage) {
        this.stage = stage;
    }

    public void displayResultPage() {
        // 배경 이미지 설정 및 페이드 인 효과
        ImageView backgroundImageView = new ImageView(ImageLoader.loadImage("end-background.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(1024);

        FadeTransition fadeInBackground = new FadeTransition(Duration.seconds(2), backgroundImageView);
        fadeInBackground.setFromValue(0);
        fadeInBackground.setToValue(1);

        Font customFont = Font.loadFont(getClass().getResourceAsStream("/HS봄바람체2.0.otf"), 30);
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
        backToStart.setOnMouseClicked(event -> goToStartPage());

        // 선택지와의 간격 설정
        HBox optionsBox = new HBox(100, wrapInStackPane(option1), wrapInStackPane(option2)); // 가로 간격을 20으로 설정
        optionsBox.setAlignment(Pos.CENTER); // 중앙 정렬

        // 레이아웃을 설정하고 resultText와 optionsBox 사이에 간격을 추가
        VBox layout = new VBox(40, resultText, optionsBox, backToStart);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(optionsBox, new javafx.geometry.Insets(70, 0, 0, 0)); // 위쪽 여백 70

        StackPane root = new StackPane(backgroundImageView, layout);
        StackPane.setAlignment(layout, Pos.CENTER);

        Scene scene = new Scene(root, 1440, 1024);
        stage.setScene(scene);
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
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/HS봄바람체2.0.otf"), 30);

        Label label = new Label(text);
        label.setFont(customFont);
        label.setStyle("-fx-text-fill: black;");
        label.setAlignment(Pos.CENTER); // 라벨 안의 텍스트 정렬
        label.setOnMouseEntered(e -> label.setStyle("-fx-text-fill: blue;"));
        label.setOnMouseExited(e -> label.setStyle("-fx-text-fill: black;"));
        label.setOnMouseClicked(e -> System.out.println(text + " 클릭됨"));
        return label;
    }

    private void goToWebtoonPage() {
        // 웹툰 결과 페이지로 이동하는 로직 추가
        System.out.println("웹툰 결과 페이지로 이동");
    }

    private void goToCharacterIntroPage() {
        // 인물 소개 페이지로 이동하는 로직 추가
        System.out.println("인물 소개 페이지로 이동");
    }

    private void goToStartPage() {
        // 처음으로 돌아가는 페이지로 이동하는 로직 추가
        System.out.println("처음 페이지로 돌아가기");
    }
}
