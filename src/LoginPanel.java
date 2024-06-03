import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);

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
            JOptionPane.showMessageDialog(null,"비밀번호 파일을 불러올 수 없습니다. 관리자에게 문의해주세요.");
        }

        //로그인 화면
        JPasswordField passwordField = new JPasswordField("비밀번호 입력");
        passwordField.setForeground(Color.GRAY);
        JButton adminCheckButton = new JButton("확인");
        passwordField.setBounds(250, 200, 400, 100);
        adminCheckButton.setBounds(250, 300, 400, 100);
        passwordField.setFont(textFont);
        adminCheckButton.setFont(textFont);
        JLabel titleLabel = new JLabel("관리자 로그인");
        titleLabel.setFont(textFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 100, 900, 100);
        titleLabel.setVisible(true);
        add(titleLabel);
        adminCheckButton.addActionListener(e -> {
            String insertedPassword = String.valueOf(passwordField.getPassword());
            if (insertedPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                insertedPassword = HangulToQwerty.convertHangulToQwerty(insertedPassword);
            }
            if (insertedPassword.equals(AdminFrame.password)) {
                setVisible(false);
                AdminFrame.adminMenuPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "비밀번호가 올바르지 않습니다.");
            }
        });
        // 엔터키를 눌렀을 때 버튼이 눌리도록 설정
        passwordField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        passwordField.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminCheckButton.doClick();
            }
        });
        add(adminCheckButton);
        add(passwordField);

        addFocusListenerToPasswordField(passwordField,"비밀번호 입력");
    }
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
