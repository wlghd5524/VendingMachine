package adminPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import buyPage.BuyPanel;

public class AdminFrame extends JFrame {
    static String password;
    public static JPanel adminMenuPanel;
    JPanel passwordChangePanel;
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
        adminMenuPanel = new AdminMenuPanel(this);
        add(adminMenuPanel);


        // 관리자 스레드 중복 실행 방지
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isAdminThreadRunning.set(false);
                BuyPanel.pressLogoCount = 0;
                BuyPanel.updateBuyButton();
            }
        });
        setVisible(true);
    }

    // 관리자 스레드 중복 실행 방지
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
