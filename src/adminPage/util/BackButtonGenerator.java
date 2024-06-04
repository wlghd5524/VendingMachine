package adminPage.util;

import adminPage.AdminFrame;

import javax.swing.*;
import java.awt.*;

//뒤로 가기 버튼 생성 클래스
public class BackButtonGenerator {
    public static JButton createBackButton(JPanel currentPanel) {
        JButton backButton = new JButton(new ImageIcon(new ImageIcon("image/back.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));  //뒤로가기 버튼 이미지 불러오기
        backButton.setBounds(0, 0, 50, 50);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {         //뒤로 가기 버튼을 눌렀을 때 관리자 메뉴 화면으로 전환
            currentPanel.setVisible(false);
            AdminFrame.adminMenuPanel.setVisible(true);
        });
        return backButton;
    }
}
