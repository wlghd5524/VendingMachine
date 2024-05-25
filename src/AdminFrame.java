import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdminFrame extends JFrame {
    static String password;
    public AdminFrame() {
        //비밀번호 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Password.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                password = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 900);
        getContentPane().setBackground(Color.WHITE);
        Font buttonFont = new Font("Arial", Font.BOLD, 40);

        //관리자 로그인 패널
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(252, 255, 216));
        loginPanel.setSize(1100, 1100);
        add(loginPanel);
        loginPanel.setVisible(true);

        //관리자 패널
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setBackground(new Color(252, 255, 216));
        adminPanel.setSize(1100, 1100);
        add(adminPanel);
        adminPanel.setVisible(false);

        //로그인 화면
        JPasswordField passwordField = new JPasswordField();
        JButton adminCheckButton = new JButton("확인");
        passwordField.setBounds(350, 400, 400, 100);
        adminCheckButton.setBounds(350, 500, 400, 100);
        passwordField.setFont(buttonFont);
        adminCheckButton.setFont(buttonFont);
        JLabel incorrectPasswordLabel = new JLabel("비밀번호가 알맞지 않습니다.");
        incorrectPasswordLabel.setFont(buttonFont);
        incorrectPasswordLabel.setBounds(340, 300, 500, 100);
        incorrectPasswordLabel.setVisible(false);
        loginPanel.add(incorrectPasswordLabel);
        adminCheckButton.addActionListener(e -> {
            if (passwordField.getText().equals(AdminFrame.password)) {
                loginPanel.setVisible(false);
                adminPanel.setVisible(true);
            }
            else {
                incorrectPasswordLabel.setVisible(true);
            }
        });
        loginPanel.add(adminCheckButton);
        loginPanel.add(passwordField);
        setVisible(true);
    }
    static Runnable adminMode = new Runnable() {
        @Override
        public void run() {
            AdminFrame adminFrame = new AdminFrame();
        }
    };
    public static void startAdminThread() {
        Thread adminThread = new Thread(adminMode);
        adminThread.start();
    }
}
