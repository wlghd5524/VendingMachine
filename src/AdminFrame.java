import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdminFrame extends JFrame {
    static String password;
    Font textFont = new Font("Arial", Font.BOLD, 40);
    static JPanel adminMenuPanel;

    public AdminFrame() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        //관리자 로그인 패널
        JPanel loginPanel = new LoginPanel();
        add(loginPanel);


        //관리자 메뉴 패널
        adminMenuPanel = new JPanel();
        adminMenuPanel.setLayout(new GridLayout(2, 3, 50, 50));
        adminMenuPanel.setBackground(new Color(252, 255, 216));
        adminMenuPanel.setSize(900, 600);
        add(adminMenuPanel);
        adminMenuPanel.setVisible(false);


        //비밀번호 변경 화면
        JPanel passwordChangePanel = new passwordChangePanel();
        add(passwordChangePanel);
        passwordChangePanel.setVisible(false);
        //비밀번호 변경 메뉴 버튼
        JButton passwordChangeMenuButton = new JButton("<html><div style='text-align: center;'>비밀번호<br>변경</div></html>");
        passwordChangeMenuButton.setFont(textFont);
        adminMenuPanel.add(passwordChangeMenuButton);
        passwordChangeMenuButton.addActionListener(e -> {
            passwordChangePanel.setVisible(true);
            adminMenuPanel.setVisible(false);
        });


        //재고 보충 화면
        JPanel addDrinkPanel = new AddDrinkPanel();
        add(addDrinkPanel);
        //재고 보충 메뉴 버튼
        JButton addDrinkButton = new JButton("재고 보충");
        addDrinkButton.setFont(textFont);
        adminMenuPanel.add(addDrinkButton);
        addDrinkButton.addActionListener(e -> {
            addDrinkPanel.setVisible(true);
            adminMenuPanel.setVisible(false);
        });


        //자판기 매출 산출 화면
        JPanel machineSalesReportPanel = new MachineSalesReportPanel();
        add(machineSalesReportPanel);
        //자판기 일별/월별 매출 산출 버튼
        JButton machineSalesReportButton = new JButton("<html><div style='text-align: center;'>자판기<br>일별/월별<br>매출 산출</div></html>");
        machineSalesReportButton.setFont(textFont);
        adminMenuPanel.add(machineSalesReportButton);
        machineSalesReportButton.addActionListener(e -> {
            machineSalesReportPanel.setVisible(true);
            adminMenuPanel.setVisible(false);
        });


        //음료 매출 산출 화면
        JPanel drinkSalesReportPanel = new DrinkSalesReportPanel();
        add(drinkSalesReportPanel);
        //음료 일별/월별 매출 산출
        JButton drinkSalesReportButton = new JButton("<html><div style='text-align: center;'>음료<br>일별/월별<br>매출 산출</div></html>");
        drinkSalesReportButton.setFont(textFont);
        adminMenuPanel.add(drinkSalesReportButton);
        drinkSalesReportButton.addActionListener(e -> {
            drinkSalesReportPanel.setVisible(true);
            adminMenuPanel.setVisible(false);
        });


        //수금 화면
        JPanel collectMoneyPanel = new CollectMoneyPanel();
        add(collectMoneyPanel);
        //수금 버튼
        JButton collectMoneyButton = new JButton("수금");
        collectMoneyButton.setFont(textFont);
        adminMenuPanel.add(collectMoneyButton);
        collectMoneyButton.addActionListener(e -> {
            collectMoneyPanel.setVisible(true);
            adminMenuPanel.setVisible(false);
        });

        //음료 정보 변경 화면
        JPanel modifyDrinkPanel = new ModifyDrinkPanel();
        add(modifyDrinkPanel);
        //음료 정보 변경 버튼
        JButton modifyDrinkButton = new JButton("<html><div style='text-align: center;'>음료<br>정보 변경</div></html>");
        modifyDrinkButton.setFont(textFont);
        adminMenuPanel.add(modifyDrinkButton);
        modifyDrinkButton.addActionListener(e -> {
            modifyDrinkPanel.setVisible(true);
            adminMenuPanel.setVisible(false);
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
