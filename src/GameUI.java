// GameUI.java
import javax.swing.*;
import java.awt.*;

public class GameUI {
    private JFrame frame;

    public GameUI() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("초대 - 게임 시작");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 이미지 로드 및 추가
        loadImages();

        frame.setVisible(true);
    }

    // 이미지 파일 불러오기 설정
    private void loadImages() {
        // 이미지 파일 경로 (src/images 폴더에 있는 이미지 파일)
        ImageIcon icon = new ImageIcon("src/images/yourImage.png");  // 실제 파일명으로 수정 필요
        JLabel label = new JLabel(icon);

        // 레이아웃에 맞게 이미지 위치 설정
        frame.add(label, BorderLayout.CENTER);
    }
}
