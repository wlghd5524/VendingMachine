import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class adminFrame extends JFrame {
    static String password;
    public adminFrame() {
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
        setSize(1000, 1000);
        getContentPane().setBackground(Color.WHITE);
        //관리자 패널
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setBackground(new Color(252, 255, 216));
        adminPanel.setSize(1100, 1100);
        add(adminPanel);


        setVisible(true);


    }
}
