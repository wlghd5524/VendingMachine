package adminPage;

import javax.swing.*;
import java.awt.*;

//관리자 메뉴 패널 생성 클래스
public class AdminMenuPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);
    public AdminMenuPanel(AdminFrame adminFrame) {
        //관리자 메뉴 패널 설정
        setLayout(new GridLayout(2, 3, 50, 50));
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setVisible(false);


        //비밀번호 변경 메뉴 버튼
        JButton passwordChangeMenuButton = new JButton("<html><div style='text-align: center;'>비밀번호<br>변경</div></html>");
        passwordChangeMenuButton.setFont(textFont);
        add(passwordChangeMenuButton);
        passwordChangeMenuButton.addActionListener(e -> {
            //비밀번호 변경 화면으로 전환
            JPanel passwordChangePanel = new PasswordChangePanel();
            adminFrame.add(passwordChangePanel);
            setVisible(false);
        });


        //재고 보충 메뉴 버튼
        JButton addDrinkButton = new JButton("재고 보충");
        addDrinkButton.setFont(textFont);
        add(addDrinkButton);
        addDrinkButton.addActionListener(e -> {
            //재고 보충 화면으로 전환
            JPanel addDrinkPanel = new AddDrinkPanel();
            adminFrame.add(addDrinkPanel);
            setVisible(false);
        });


        //자판기 일별/월별 매출 산출 버튼
        JButton machineSalesReportButton = new JButton("<html><div style='text-align: center;'>자판기<br>일별/월별<br>매출 산출</div></html>");
        machineSalesReportButton.setFont(textFont);
        add(machineSalesReportButton);
        machineSalesReportButton.addActionListener(e -> {
            //자판기 매출 산출 화면으로 전환
            JPanel machineSalesReportPanel = new MachineSalesReportPanel();
            adminFrame.add(machineSalesReportPanel);
            setVisible(false);
        });


        //음료 일별/월별 매출 산출
        JButton drinkSalesReportButton = new JButton("<html><div style='text-align: center;'>음료<br>일별/월별<br>매출 산출</div></html>");
        drinkSalesReportButton.setFont(textFont);
        add(drinkSalesReportButton);
        drinkSalesReportButton.addActionListener(e -> {
            //음료 매출 산출 화면으로 전환
            JPanel drinkSalesReportPanel = new DrinkSalesReportPanel();
            adminFrame.add(drinkSalesReportPanel);
            setVisible(false);
        });


        //수금 버튼
        JButton collectMoneyButton = new JButton("수금");
        collectMoneyButton.setFont(textFont);
        add(collectMoneyButton);
        collectMoneyButton.addActionListener(e -> {
            //수금 화면으로 전환
            JPanel collectMoneyPanel = new CollectMoneyPanel();
            adminFrame.add(collectMoneyPanel);
            setVisible(false);
        });


        //음료 정보 변경 버튼
        JButton modifyDrinkButton = new JButton("<html><div style='text-align: center;'>음료<br>정보 변경</div></html>");
        modifyDrinkButton.setFont(textFont);
        add(modifyDrinkButton);
        modifyDrinkButton.addActionListener(e -> {
            //음료 정보 변경 화면으로 전환
            JPanel modifyDrinkPanel = new ModifyDrinkPanel();
            adminFrame.add(modifyDrinkPanel);
            setVisible(false);
        });
    }
}
