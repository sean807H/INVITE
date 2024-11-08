import javafx.scene.image.Image;

public class ImageLoader {
    // 이미지를 로드하는 메소드
    public static Image loadImage(String filename) {
        return new Image(ImageLoader.class.getResourceAsStream("/images/" + filename));
    }

    // GIF 파일을 로드하는 메소드
    public static Image loadGif(String filename) {
        return new Image(ImageLoader.class.getResourceAsStream("/images/" + filename));
    }
}
