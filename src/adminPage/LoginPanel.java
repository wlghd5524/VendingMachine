package adminPage;

import adminPage.util.HangulToQwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//관리자 로그인 화면 패널 생성 클래스
public class LoginPanel extends JPanel {
    Font textFont = new Font("SansSerif", Font.BOLD, 40);

    public LoginPanel() {
        setLayout(null);
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setVisible(true);

        //비밀번호 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Password.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                AdminFrame.password = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "비밀번호 파일을 불러올 수 없습니다. 관리자에게 문의해주세요.");
        }


        //제목 라벨
        JLabel titleLabel = new JLabel("관리자 로그인");
        titleLabel.setFont(textFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 100, 900, 100);
        titleLabel.setVisible(true);
        add(titleLabel);


        //패스워드 입력 칸
        JPasswordField passwordField = new JPasswordField("비밀번호 입력");
        passwordField.setForeground(Color.GRAY);
        passwordField.setBounds(250, 200, 400, 100);
        passwordField.setFont(textFont);

        //로그인 버튼
        JButton adminCheckButton = new JButton("확인");
        adminCheckButton.setBounds(250, 300, 400, 100);
        adminCheckButton.setFont(textFont);
        adminCheckButton.setBackground(Color.WHITE);
        //로그인 버튼을 눌렀을 때 이벤트 설정
        adminCheckButton.addActionListener(e -> {
            String insertedPassword = String.valueOf(passwordField.getPassword());
            if (insertedPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                insertedPassword = HangulToQwerty.convertHangulToQwerty(insertedPassword);  //한글이 입력되면 자동으로 쿼티 자판 영어로 변환
            }
            if (insertedPassword.equals(AdminFrame.password)) {
                setVisible(false);
                AdminFrame.adminMenuPanel.setVisible(true);                 //비밀번호가 올바르면 관리자 메뉴 띄우기
            } else {
                JOptionPane.showMessageDialog(null, "비밀번호가 올바르지 않습니다.");   //비밀번호가 올바르지 않으면 메세지 알림
            }
        });


        // 엔터키를 눌렀을 때 로그인 버튼이 눌리도록 설정
        passwordField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        passwordField.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminCheckButton.doClick();
            }
        });
        add(adminCheckButton);
        add(passwordField);

        addFocusListenerToPasswordField(passwordField, "비밀번호 입력");  //힌트 문자 설정
    }

    //비밀번호 입력 칸에 아무 것도 입력되어있지 않을 때 힌트 문자 설정
    public static void addFocusListenerToPasswordField(JPasswordField passwordField, String hintText) {
        passwordField.setText(hintText);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(hintText)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('⦁');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(hintText);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }
}
