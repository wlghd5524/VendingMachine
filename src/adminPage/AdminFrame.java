package adminPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import buyPage.BuyPanel;

//관리자 페이지 프레임 생성 클래스
public class AdminFrame extends JFrame {
    static String password;
    public static JPanel adminMenuPanel;

    public AdminFrame() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //관리자 페이지를 닫아도 자판기 프로그램이 종료되지 않게 설정
        setSize(900, 600);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        //관리자 로그인 패널 생성
        JPanel loginPanel = new LoginPanel();
        add(loginPanel);


        //관리자 메뉴 패널 생성
        adminMenuPanel = new AdminMenuPanel(this);
        add(adminMenuPanel);


        // 관리자 페이지를 닫았을 때 이벤트
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isAdminThreadRunning.set(false);
                BuyPanel.pressLogoCount = 0;
                BuyPanel.updateDrinkInformation();      //음료 정보(사진,가격) 최신화
                BuyPanel.updateBuyButton();             //구매 가능한 음료 표시 최신화
            }
        });
        setVisible(true);
    }

    // 관리자 스레드 중복 실행 방지
    private static final AtomicBoolean isAdminThreadRunning = new AtomicBoolean(false); //lock 형식으로 현재 스레드가 실행중인지 표시
    //관리자 스레드 설정
    static Runnable adminMode = () -> {
        try {
            AdminFrame adminFrame = new AdminFrame();   //관리자 스레드가 실행되면 관리자 페이지 실행
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    //어드민 스레드 실행
    public static void startAdminThread() {
        if (isAdminThreadRunning.compareAndSet(false, true)) {
            Thread adminThread = new Thread(adminMode);
            adminThread.start();        //이미 실행 중인 스레드가 있는지 판별하고 없다면 어드민 스레드 실행
        }
    }
}
