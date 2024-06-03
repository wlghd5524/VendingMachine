import javax.swing.*;
import java.awt.*;

//뒤로 가기 버튼 생성 클래스
public class BackButtonGenerator {
    public static JButton createBackButton(JPanel currentPanel) {
        JButton backButton = new JButton(new ImageIcon(new ImageIcon("image/back.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        backButton.setBounds(0, 0, 50, 50);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            currentPanel.setVisible(false);
            AdminFrame.adminMenuPanel.setVisible(true);
        });
        return backButton;
    }
}
