package adminPage;

import adminPage.util.BackButtonGenerator;
import adminPage.util.HangulToQwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//비밀번호 변경 화면 패널 생성 클래스
public class PasswordChangePanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);

    PasswordChangePanel() {
        //비밀번호 변경 화면
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼
        JButton backButton = BackButtonGenerator.createBackButton(this);
        add(backButton);

        //제목 라벨
        JLabel titleLabel = new JLabel("비밀번호 변경");
        titleLabel.setFont(textFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 900, 100);
        add(titleLabel);
        titleLabel.setVisible(true);


        //비밀번호 입력 칸 설정
        JPasswordField currentPasswordField = new JPasswordField("현재 비밀번호 입력");
        currentPasswordField.setFont(textFont);
        currentPasswordField.setForeground(Color.GRAY);
        currentPasswordField.setBounds(250, 100, 400, 100);

        //새 비밀번호 입력 칸 설정
        JPasswordField newPasswordField = new JPasswordField("새로운 비밀번호 입력");
        newPasswordField.setFont(textFont);
        newPasswordField.setForeground(Color.GRAY);
        newPasswordField.setBounds(250, 200, 400, 100);

        //새 비밀번호 확인 입력 칸 설정
        JPasswordField confirmPasswordField = new JPasswordField("비밀번호 확인");
        confirmPasswordField.setFont(textFont);
        confirmPasswordField.setForeground(Color.GRAY);
        confirmPasswordField.setBounds(250, 300, 400, 100);

        //비밀번호 변경 버튼 설정
        JButton passwordChangeButton = new JButton("비밀번호 변경");
        passwordChangeButton.setFont(textFont);
        passwordChangeButton.setBounds(250, 400, 400, 100);


        add(currentPasswordField);
        add(newPasswordField);
        add(confirmPasswordField);
        add(passwordChangeButton);


        //입력 칸에 아무것도 입력되지 않았을 때 힌트 문자 설정
        LoginPanel.addFocusListenerToPasswordField(currentPasswordField, "현재 비밀번호 입력");
        LoginPanel.addFocusListenerToPasswordField(newPasswordField, "새로운 비밀번호 입력");
        LoginPanel.addFocusListenerToPasswordField(confirmPasswordField, "비밀번호 확인");


        // 엔터키를 눌렀을 때 비밀번호 변경 버튼이 눌리도록 설정
        confirmPasswordField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        confirmPasswordField.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordChangeButton.doClick();
            }
        });

        // 비밀번호 변경 버튼을 눌렀을 때 이벤트 설정
        passwordChangeButton.addActionListener(e -> {
            String currentPassword = new String(currentPasswordField.getPassword());    //현재 비밀번호
            String newPassword = new String(newPasswordField.getPassword());        //새 비밀번호
            String confirmPassword = new String(confirmPasswordField.getPassword());    //새 비밀번호 확인
            //입력된 문자가 한글이면 자동으로 쿼티 자판 영어로 변환
            if (currentPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                currentPassword = HangulToQwerty.convertHangulToQwerty(currentPassword);
            }
            if (newPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                newPassword = HangulToQwerty.convertHangulToQwerty(newPassword);
            }
            if (confirmPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                confirmPassword = HangulToQwerty.convertHangulToQwerty(confirmPassword);
            }
            if (currentPassword.equals(AdminFrame.password)) {                //입력한 비밀번호가 맞을 때
                if (newPassword.equals(confirmPassword)) {         //새 비밀번호와 비밀번호 확인에 입력된 값이 같을 때
                    if (newPassword.length() >= 8) {
                        // 새 비밀번호의 유효성 검사 (알파벳, 숫자, 특수문자 각각 한 번씩 포함)
                        if (newPassword.matches(".*[A-Za-z].*") && newPassword.matches(".*\\d.*") && newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                            AdminFrame.password = newPassword;
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Password.txt"))) {
                                writer.write(AdminFrame.password);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.");
                            setVisible(false);
                            AdminFrame.adminMenuPanel.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "비밀번호는 알파벳, 숫자, 특수문자를 포함해야 합니다.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "비밀번호는 8자리 이상이어야 합니다.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");     //새 비밀번호와 비밀번호 확인에 입력된 값이 다를 때
                }
            } else {
                JOptionPane.showMessageDialog(null, "현재 비밀번호가 일치하지 않습니다.");     //입력한 비밀번호가 틀릴 때
            }
        });
    }


}
