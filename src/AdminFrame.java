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
        getContentPane().setBackground(Color.WHITE);
        Font buttonFont = new Font("Arial", Font.BOLD, 40);

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
            if(insertedPassword.matches(".*[ㄱ-ㅎㅏ-ㅣ].*")) {
                insertedPassword = HangulToQwerty.convertHangulToQwerty(insertedPassword);
            }
            if (insertedPassword.equals(AdminFrame.password)) {
                loginPanel.setVisible(false);
                adminPanel.setVisible(true);
            } else {
                incorrectPasswordLabel.setVisible(true);
            }
        });
        loginPanel.add(adminCheckButton);
        loginPanel.add(passwordField);



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
        JPanel addDrinkPanel = new JPanel();
        addDrinkPanel.setBackground(new Color(252, 255, 216));
        addDrinkPanel.setSize(900, 600);
        addDrinkPanel.setLayout(null);
        add(addDrinkPanel);
        addDrinkPanel.setVisible(false);

        //음료 재고 버튼
        JButton addDrinkButton = new JButton("재고 보충");
        addDrinkButton.setFont(textFont);
        adminPanel.add(addDrinkButton);
        addDrinkButton.addActionListener(e -> {
            addDrinkPanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //자판기 매출 산출 화면
        JPanel machineSalesReportPanel = new JPanel();
        machineSalesReportPanel.setBackground(new Color(252, 255, 216));
        machineSalesReportPanel.setSize(900, 600);
        machineSalesReportPanel.setLayout(null);
        add(machineSalesReportPanel);
        machineSalesReportPanel.setVisible(false);

        //자판기 일별/월별 매출 산출 버튼
        JButton machineSalesReportButton = new JButton("<html><div style='text-align: center;'>자판기<br>일별/월별<br>매출 산출</div></html>");
        machineSalesReportButton.setFont(textFont);
        adminPanel.add(machineSalesReportButton);
        machineSalesReportButton.addActionListener(e -> {
            machineSalesReportPanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //음료 매출 산출 화면
        JPanel drinkSalesReportPanel = new JPanel();
        drinkSalesReportPanel.setBackground(new Color(252, 255, 216));
        drinkSalesReportPanel.setSize(900, 600);
        drinkSalesReportPanel.setLayout(null);
        add(drinkSalesReportPanel);
        drinkSalesReportPanel.setVisible(false);

        //음료 일별/월별 매출 산출
        JButton drinkSalesReportButton = new JButton("<html><div style='text-align: center;'>음료<br>일별/월별<br>매출 산출</div></html>");
        drinkSalesReportButton.setFont(textFont);
        adminPanel.add(drinkSalesReportButton);
        drinkSalesReportButton.addActionListener(e -> {
            drinkSalesReportPanel.setVisible(true);
            adminPanel.setVisible(false);
        });


        //수금 화면
        JPanel collectMoneyPanel = new JPanel();
        collectMoneyPanel.setBackground(new Color(252, 255, 216));
        collectMoneyPanel.setSize(900, 600);
        collectMoneyPanel.setLayout(null);
        add(collectMoneyPanel);
        collectMoneyPanel.setVisible(false);

        //수금 버튼
        JButton collectMoneyButton = new JButton("수금");
        collectMoneyButton.setFont(textFont);
        adminPanel.add(collectMoneyButton);
        collectMoneyButton.addActionListener(e -> {
            collectMoneyPanel.setVisible(true);
            adminPanel.setVisible(false);
        });

        //음료 정보 변경 화면
        JPanel modifyDrinkPanel = new JPanel();
        modifyDrinkPanel.setBackground(new Color(252, 255, 216));
        modifyDrinkPanel.setSize(900, 600);
        modifyDrinkPanel.setLayout(null);
        add(modifyDrinkPanel);
        modifyDrinkPanel.setVisible(false);

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
