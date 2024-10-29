// GameUI.java
import javax.swing.*;

public class GameUI {
    public void showCharacterName(Character character) {
        JFrame frame = new JFrame("Game");
        JLabel label = new JLabel("캐릭터 이름: " + character.getName());
        frame.add(label);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}