import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class passwordChangePanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);
    passwordChangePanel() {
        //비밀번호 변경 화면
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(false);

        JPasswordField currentPasswordField = new JPasswordField("현재 비밀번호 입력");
        currentPasswordField.setFont(textFont);
        currentPasswordField.setForeground(Color.GRAY);
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setFont(textFont);
        newPasswordField.setForeground(Color.GRAY);
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(textFont);
        confirmPasswordField.setForeground(Color.GRAY);
        JButton passwordChangeButton = new JButton("비밀번호 변경");
        passwordChangeButton.setFont(textFont);
        passwordChangeButton.setBounds(250, 400, 400, 100);
        currentPasswordField.setBounds(250, 100, 400, 100);
        newPasswordField.setBounds(250, 200, 400, 100);
        confirmPasswordField.setBounds(250, 300, 400, 100);
        add(currentPasswordField);
        add(newPasswordField);
        add(confirmPasswordField);
        add(passwordChangeButton);

        JLabel passwordErrorLabel = new JLabel();
        passwordErrorLabel.setFont(textFont);
        passwordErrorLabel.setBounds(220,10,800,100);
        add(passwordErrorLabel);
        passwordErrorLabel.setVisible(false);

//        JLabel differentPasswordLabel = new JLabel("새 비밀번호가 다릅니다.");
//        differentPasswordLabel.setFont(textFont);
//        differentPasswordLabel.setBounds(220, 50, 500, 100);
//        add(differentPasswordLabel);
//        differentPasswordLabel.setVisible(false);
//
//        JLabel incorrectPasswordLabel = new JLabel("비밀번호가 알맞지 않습니다.");
//        incorrectPasswordLabel.setFont(textFont);
//        incorrectPasswordLabel.setBounds(230, 10, 500, 100);
//        incorrectPasswordLabel.setVisible(false);
//        add(incorrectPasswordLabel);

        // 비밀번호 입력 칸에 아무 것도 입력하지 않았을 때 힌트 문자
        currentPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(currentPasswordField.getPassword()).equals("현재 비밀번호 입력")) {
                    currentPasswordField.setText("");
                    currentPasswordField.setForeground(Color.BLACK);
                    currentPasswordField.setEchoChar('⦁');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (currentPasswordField.getPassword().length == 0) {
                    currentPasswordField.setText("현재 비밀번호 입력");
                    currentPasswordField.setForeground(Color.GRAY);
                    currentPasswordField.setEchoChar((char) 0);
                }
            }
        });
        newPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(newPasswordField.getPassword()).equals("새로운 비밀번호 입력")) {
                    newPasswordField.setText("");
                    newPasswordField.setForeground(Color.BLACK);
                    newPasswordField.setEchoChar('⦁');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (newPasswordField.getPassword().length == 0) {
                    newPasswordField.setText("새로운 비밀번호 입력");
                    newPasswordField.setForeground(Color.GRAY);
                    newPasswordField.setEchoChar((char) 0);
                }
            }
        });
        confirmPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).equals("비밀번호 확인")) {
                    confirmPasswordField.setText("");
                    confirmPasswordField.setForeground(Color.BLACK);
                    confirmPasswordField.setEchoChar('⦁');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (confirmPasswordField.getPassword().length == 0) {
                    confirmPasswordField.setText("비밀번호 확인");
                    confirmPasswordField.setForeground(Color.GRAY);
                    confirmPasswordField.setEchoChar((char) 0);
                }
            }
        });
        passwordChangeButton.addActionListener(e -> {
            String currentPassword = new String(currentPasswordField.getPassword());    //현재 비밀번호
            String newPassword = new String(newPasswordField.getPassword());        //새 비밀번호
            String confirmPassword = new String(confirmPasswordField.getPassword());    //새 비밀번호 확인

            if (currentPassword.equals(AdminFrame.password)) {                //입력한 비밀번호가 맞을 때
                if (newPassword.equals(confirmPassword)) {         //새 비밀번호와 비밀번호 확인에 입력된 값이 같을 때
                    if(newPassword.length() >= 8) {
                        // 새 비밀번호의 유효성 검사 (알파벳, 숫자, 특수문자 각각 한 번씩 포함)
                        if (newPassword.matches(".*[A-Za-z].*") && newPassword.matches(".*\\d.*") && newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                            AdminFrame.password = newPassword;
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Password.txt"))) {
                                writer.write(AdminFrame.password);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            setVisible(false);
                            AdminFrame.adminPanel.setVisible(true);
                        } else {
                            passwordErrorLabel.setBounds(20,10,900,100);
                            passwordErrorLabel.setText("비밀번호는 알파벳, 숫자, 특수문자를 포함해야 합니다.");
                            passwordErrorLabel.setVisible(true);
                        }

                    }
                    else {
                        passwordErrorLabel.setBounds(180,10,900,100);
                        passwordErrorLabel.setText("비밀번호는 8자리 이상이어야 합니다.");
                        passwordErrorLabel.setVisible(true);
                    }

                } else {                              //새 비밀번호와 비밀번호 확인에 입력된 값이 다를 때
                    passwordErrorLabel.setBounds(50,10,900,100);
                    passwordErrorLabel.setText("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                    passwordErrorLabel.setVisible(true);
                }
            } else {      //입력한 비밀번호가 틀릴 때
                passwordErrorLabel.setBounds(180,10,900,100);
                passwordErrorLabel.setText("현재 비밀번호가 일치하지 않습니다.");
                passwordErrorLabel.setVisible(true);
            }
        });
    }

}
