import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdminFrame extends JFrame {
    static String password;
    Font textFont = new Font("Arial", Font.BOLD, 40);
    static JPanel adminPanel;

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
        setSize(900, 600);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        //관리자 로그인 패널
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(252, 255, 216));
        loginPanel.setSize(900, 600);
        add(loginPanel);
        loginPanel.setVisible(true);

        //관리자 패널
        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(2, 3, 50, 50));
        adminPanel.setBackground(new Color(252, 255, 216));
        adminPanel.setSize(900, 600);
        add(adminPanel);
        adminPanel.setVisible(false);

        //로그인 화면
        JPasswordField passwordField = new JPasswordField("비밀번호 입력");
        passwordField.setForeground(Color.GRAY);
        JButton adminCheckButton = new JButton("확인");
        passwordField.setBounds(250, 200, 400, 100);
        adminCheckButton.setBounds(250, 300, 400, 100);
        passwordField.setFont(textFont);
        adminCheckButton.setFont(textFont);
        JLabel incorrectPasswordLabel = new JLabel("비밀번호가 알맞지 않습니다.");
        incorrectPasswordLabel.setFont(textFont);
        incorrectPasswordLabel.setBounds(220, 100, 500, 100);
        incorrectPasswordLabel.setVisible(false);
        loginPanel.add(incorrectPasswordLabel);
        adminCheckButton.addActionListener(e -> {
            String insertedPassword = String.valueOf(passwordField.getPassword());
            if (insertedPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                insertedPassword = HangulToQwerty.convertHangulToQwerty(insertedPassword);
            }
            if (insertedPassword.equals(AdminFrame.password)) {
                loginPanel.setVisible(false);
                adminPanel.setVisible(true);
            } else {
                incorrectPasswordLabel.setVisible(true);
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
        loginPanel.add(adminCheckButton);
        loginPanel.add(passwordField);

        // 비밀번호 입력 칸에 아무 것도 입력하지 않았을 때 힌트 문자
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("비밀번호 입력")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('⦁');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText("비밀번호 입력");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        //비밀번호 변경 화면
        JPanel passwordChangePanel = new passwordChangePanel();
        add(passwordChangePanel);
        passwordChangePanel.setVisible(false);
        //비밀번호 변경 메뉴 버튼
        JButton passwordChangeMenuButton = new JButton("<html><div style='text-align: center;'>비밀번호<br>변경</div></html>");
        passwordChangeMenuButton.setFont(textFont);
        adminPanel.add(passwordChangeMenuButton);
        passwordChangeMenuButton.addActionListener(e -> {
            passwordChangePanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //재고 보충 화면
        JPanel addDrinkPanel = new AddDrinkPanel();
        add(addDrinkPanel);
        //재고 보충 메뉴 버튼
        JButton addDrinkButton = new JButton("재고 보충");
        addDrinkButton.setFont(textFont);
        adminPanel.add(addDrinkButton);
        addDrinkButton.addActionListener(e -> {
            addDrinkPanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //자판기 매출 산출 화면
        JPanel machineSalesReportPanel = new MachineSalesReportPanel();
        add(machineSalesReportPanel);
        //자판기 일별/월별 매출 산출 버튼
        JButton machineSalesReportButton = new JButton("<html><div style='text-align: center;'>자판기<br>일별/월별<br>매출 산출</div></html>");
        machineSalesReportButton.setFont(textFont);
        adminPanel.add(machineSalesReportButton);
        machineSalesReportButton.addActionListener(e -> {
            machineSalesReportPanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //음료 매출 산출 화면
        JPanel drinkSalesReportPanel = new DrinkSalesReportPanel();
        add(drinkSalesReportPanel);
        //음료 일별/월별 매출 산출
        JButton drinkSalesReportButton = new JButton("<html><div style='text-align: center;'>음료<br>일별/월별<br>매출 산출</div></html>");
        drinkSalesReportButton.setFont(textFont);
        adminPanel.add(drinkSalesReportButton);
        drinkSalesReportButton.addActionListener(e -> {
            drinkSalesReportPanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //수금 화면
        JPanel collectMoneyPanel = new CollectMoneyPanel();
        add(collectMoneyPanel);
        //수금 버튼
        JButton collectMoneyButton = new JButton("수금");
        collectMoneyButton.setFont(textFont);
        adminPanel.add(collectMoneyButton);
        collectMoneyButton.addActionListener(e -> {
            collectMoneyPanel.setVisible(true);
            adminPanel.setVisible(false);
        });

        //음료 정보 변경 화면
        JPanel modifyDrinkPanel = new ModifyDrinkPanel();
        add(modifyDrinkPanel);
        //음료 정보 변경 버튼
        JButton modifyDrinkButton = new JButton("<html><div style='text-align: center;'>음료<br>정보 변경</div></html>");
        modifyDrinkButton.setFont(textFont);
        adminPanel.add(modifyDrinkButton);
        modifyDrinkButton.addActionListener(e -> {
            modifyDrinkPanel.setVisible(true);
            adminPanel.setVisible(false);
        });

        // 관리자 스레드 중복 실행 방지
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isAdminThreadRunning.set(false);
                BuyFrame.pressLogoCount = 0;
            }
        });
        setVisible(true);
    }

    private static final AtomicBoolean isAdminThreadRunning = new AtomicBoolean(false);
    static Runnable adminMode = () -> {
        try {
            AdminFrame adminFrame = new AdminFrame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static void startAdminThread() {
        if (isAdminThreadRunning.compareAndSet(false, true)) {
            Thread adminThread = new Thread(adminMode);
            adminThread.start();
        }


    }
}
