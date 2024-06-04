package buyPage;

import adminPage.AdminFrame;
import drink.DrinkList;
import money.MoneyList;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

//판매 페이지 패널
public class BuyPanel extends JPanel {
    private static int currentMoney = 0;             //현재 입력된 돈
    private final JLabel currentMoneyLabel;          //현재 입력된 돈을 알려주는 라벨
    public static int pressLogoCount = 0;            //로고를 누른 횟수
    private static JButton[] canBuyButtons;          //각 음료의 구매 가능 버튼
    private static JButton[] canNotBuyButtons;       //각 음료의 구매 불가능 버튼
    private int[] insertMoneyCount;                  //입력된 각 화폐 개수
    static JLabel[] drinkImageLabel;                 //음료 이미지 라벨
    static JLabel[] priceLabels;                     //음료 가격 라벨
    Font textFont = new Font("Arial", Font.BOLD, 40);           //텍스트 폰트

    public BuyPanel() {

        setLayout(null);
        setSize(1100, 1100);
        setBackground(new Color(252, 255, 216));


        //입력된 금액 표시
        currentMoneyLabel = new JLabel("현재 금액 : " + currentMoney + "원");
        currentMoneyLabel.setFont(textFont);
        currentMoneyLabel.setBounds(410, 700, 400, 150);
        add(currentMoneyLabel);

        //로고 이미지 불러오기
        JButton logoButton = new JButton(new ImageIcon(new ImageIcon("image/logo.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        logoButton.setBounds(50, 700, 150, 150);
        logoButton.setBorderPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setFocusPainted(false);
        logoButton.addActionListener(e -> { //로고를 5번 누르면 관리자 페이지 열기
            pressLogoCount++;
            if (pressLogoCount == 5) {
                AdminFrame.startAdminThread();
            }
        });
        add(logoButton);


        //음료 이미지 불러오기
        drinkImageLabel = new JLabel[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkImageLabel[i] = new JLabel(new ImageIcon(new ImageIcon(DrinkList.drinks.get(i).getImagePath()).getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH)));
            if (i < 3) {
                drinkImageLabel[i].setBounds(135 + (i * 370), 40, 100, 200);
            } else {
                drinkImageLabel[i].setBounds(135 + ((i % 3) * 370), 370, 100, 200);
            }
            add(drinkImageLabel[i]);
        }


        //구매 가능 버튼 이미지 불러오기
        ImageIcon canBuyButtonIcon = new ImageIcon(new ImageIcon("image/can buy button.png").getImage().getScaledInstance(250, 50, Image.SCALE_SMOOTH));

        //구매 불가 버튼 이미지 불러오기
        ImageIcon canNotBuyButtonIcon = new ImageIcon(new ImageIcon("image/can not buy button.png").getImage().getScaledInstance(250, 50, Image.SCALE_SMOOTH));

        //가격 표시 라벨 설정
        priceLabels = new JLabel[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            priceLabels[i] = new JLabel(DrinkList.drinks.get(i).getPrice() + "원");
            if (i < 3) {
                priceLabels[i].setBounds(140 + (i * 370), 240, 120, 50);    //첫번째 줄 음료 가격 표시 위치 및 크기
            } else {
                priceLabels[i].setBounds(140 + ((i % 3) * 370), 570, 120, 50);  //두번째 줄 음료 가격 표시 위치 및 크기
            }
            priceLabels[i].setFont(textFont);
            add(priceLabels[i]);
        }

        //구매 가능 버튼 설정
        canBuyButtons = new JButton[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            canBuyButtons[i] = new JButton(canBuyButtonIcon);
            if (i < 3) {
                canBuyButtons[i].setBounds(60 + (i * 370), 290, 250, 50);
            } else {
                canBuyButtons[i].setBounds(60 + ((i % 3) * 370), 620, 250, 50);
            }
            canBuyButtons[i].setBorderPainted(false);
            canBuyButtons[i].setContentAreaFilled(false);
            canBuyButtons[i].setFocusPainted(false);
            add(canBuyButtons[i]);
        }

        //구매 버튼을 눌렀을 때 이벤트
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            int finalI = i;
            canBuyButtons[i].addActionListener(e -> {
                currentMoney -= DrinkList.drinks.get(finalI).getPrice();
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                DrinkList.drinks.get(finalI).setStock(DrinkList.drinks.get(finalI).getStock() - 1);  //재고 줄이기
                updateBuyButton();
                addSalesLog(finalI);  //파일에 매출 로그 추가(salesReport/2024년/06월/03일.txt 형식으로 연도별, 월별, 일별 매출 분리하여 저장)
            });
        }

        //구매 불가 표시
        canNotBuyButtons = new JButton[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            canNotBuyButtons[i] = new JButton(canNotBuyButtonIcon);
            if (i < 3) {
                canNotBuyButtons[i].setBounds(60 + (i * 370), 290, 250, 50);
            } else {
                canNotBuyButtons[i].setBounds(60 + ((i % 3) * 370), 620, 250, 50);
            }
            canNotBuyButtons[i].setBorderPainted(false);
            canNotBuyButtons[i].setContentAreaFilled(false);
            canNotBuyButtons[i].setFocusPainted(false);
            add(canNotBuyButtons[i]);
        }
        updateBuyButton();

        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            int finalI = i;
            //구매 불가 버튼을 눌렀을 때 이유 메시지 알림
            canNotBuyButtons[i].addActionListener(e -> {
                if (DrinkList.drinks.get(finalI).getStock() == 0) {
                    JOptionPane.showMessageDialog(null, "품절입니다.");
                } else if (DrinkList.drinks.get(finalI).getPrice() > currentMoney) {
                    JOptionPane.showMessageDialog(null, "잔액이 부족합니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "자판기에 거스름돈이 부족합니다. 관리자에게 문의하세요.");
                }
            });
        }


        //화폐 입력 버튼
        JButton[] moneyButton = new JButton[MoneyList.moneyList.size()];
        insertMoneyCount = new int[MoneyList.moneyList.size()];
        for (int i = 0; i < MoneyList.moneyList.size(); i++) {
            moneyButton[i] = new JButton(MoneyList.moneyList.get(i).getName());
            moneyButton[i].setBounds(50 + (i * 210), 900, 150, 100);
            moneyButton[i].setFont(textFont);
            add(moneyButton[i]);
            int finalI = i;
            if (finalI < 4) {
                //화폐 입력 버튼을 눌렀을 때 이벤트 설정
                moneyButton[i].addActionListener(e -> {
                    if (currentMoney + MoneyList.moneyList.get(finalI).getPrice() <= 7000) {
                        insertMoneyCount[finalI]++;
                        currentMoney += MoneyList.moneyList.get(finalI).getPrice();
                        currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                        MoneyList.moneyList.get(finalI).setStock(MoneyList.moneyList.get(finalI).getStock() + 1);
                    } else {
                        JOptionPane.showMessageDialog(null, "돈은 총 7000원까지만 넣을 수 있습니다.");
                    }
                    updateBuyButton();
                });
            } else {
                //1000원 입력 버튼을 눌렀을 때 이벤트 설정
                moneyButton[4].addActionListener(e -> {
                    if (currentMoney + MoneyList.moneyList.get(4).getPrice() <= 5000) {
                        insertMoneyCount[4]++;
                        currentMoney += MoneyList.moneyList.get(4).getPrice();
                        currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                        MoneyList.moneyList.get(4).setStock(MoneyList.moneyList.get(4).getStock() + 1);
                        updateBuyButton();
                    } else {
                        JOptionPane.showMessageDialog(null, "지폐는 총 5000원까지만 넣을 수 있습니다.");
                    }
                    updateBuyButton();
                });
            }
        }


        //반환 버튼 설정
        JButton returnButton = new JButton(new ImageIcon(new ImageIcon("image/returnButton.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        returnButton.setBounds(900, 700, 150, 150);
        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        add(returnButton);
        //반환 버튼을 눌렀을 때 이벤트 설정
        returnButton.addActionListener(e -> {
            while (currentMoney > 0) {
                while (currentMoney >= 1000 && MoneyList.moneyList.get(4).getStock() > 0) {
                    MoneyList.moneyList.get(4).setStock(MoneyList.moneyList.get(4).getStock() - 1);
                    currentMoney -= 1000;
                }
                while (currentMoney >= 500 && MoneyList.moneyList.get(3).getStock() > 0) {
                    MoneyList.moneyList.get(3).setStock(MoneyList.moneyList.get(3).getStock() - 1);
                    currentMoney -= 500;
                }
                while (currentMoney >= 100 && MoneyList.moneyList.get(2).getStock() > 0) {
                    MoneyList.moneyList.get(2).setStock(MoneyList.moneyList.get(2).getStock() - 1);
                    currentMoney -= 100;
                }
                while (currentMoney >= 50 && MoneyList.moneyList.get(1).getStock() > 0) {
                    MoneyList.moneyList.get(1).setStock(MoneyList.moneyList.get(1).getStock() - 1);
                    currentMoney -= 50;
                }
                while (currentMoney >= 10 && MoneyList.moneyList.get(0).getStock() > 0) {
                    MoneyList.moneyList.get(0).setStock(MoneyList.moneyList.get(0).getStock() - 1);
                    currentMoney -= 10;
                }
            }
            currentMoney = 0;
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            Arrays.fill(insertMoneyCount, 0);
            updateBuyButton();
        });
    }

    //음료수 정보 최신화
    public static void updateDrinkInformation() {
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkImageLabel[i].setIcon(new ImageIcon(new ImageIcon(DrinkList.drinks.get(i).getImagePath()).getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH))); //이미지 다시 불러오기
            priceLabels[i].setText(DrinkList.drinks.get(i).getPrice() + "원");  //가격 정보 다시 불러오기
        }
    }

    //구매 불가능과 구매 가능 버튼 표시 최신화
    public static void updateBuyButton() {
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            if (canBuyDrink(i)) {
                canBuyButtons[i].setVisible(true);
                canBuyButtons[i].setEnabled(true);
                canNotBuyButtons[i].setVisible(false);
                canNotBuyButtons[i].setEnabled(false);
            } else {
                canBuyButtons[i].setVisible(false);
                canBuyButtons[i].setEnabled(false);
                canNotBuyButtons[i].setEnabled(true);
                canNotBuyButtons[i].setVisible(true);
            }
        }
    }


    //음료 구매 가능 여부 판별
    public static boolean canBuyDrink(int index) {
        int remainingMoney = currentMoney - DrinkList.drinks.get(index).getPrice();
        if (remainingMoney < 0 || DrinkList.drinks.get(index).getStock() <= 0) {
            return false;   //금액이 부족하거나 재고가 없을 경우 구매 불가
        }
        //거스름돈이 있는지 판별
        int[][] tempMoneyList = new int[MoneyList.moneyList.size()][2];
        for (int i = 0; i < MoneyList.moneyList.size(); i++) {
            tempMoneyList[i][0] = MoneyList.moneyList.get(i).getPrice();
            tempMoneyList[i][1] = MoneyList.moneyList.get(i).getStock();
        }
        for (int i = MoneyList.moneyList.size() - 1; i >= 0; i--) {
            while (remainingMoney - tempMoneyList[i][0] >= 0 && tempMoneyList[i][1] > 0) {
                tempMoneyList[i][1]--;
                remainingMoney -= tempMoneyList[i][0];
            }
        }

        if (remainingMoney == 0) {
            return true;    //거스름돈을 거슬러줄 수 있으면 구매 가능
        } else {
            return false;
        }
    }

    //파일에 매출 로그 추가(salesReport/2024년/06월/03일.txt 형식으로 연도별, 월별, 일별 매출 분리하여 저장)
    public static void addSalesLog(int index) {
        try {
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String folderPath = "salesReport/" + today.format(formatter) + "년";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // 연도별 폴더 생성
            }
            formatter = DateTimeFormatter.ofPattern("MM");
            folderPath += "/" + today.format(formatter) + "월";
            folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // 월별 폴더 생성
            }
            formatter = DateTimeFormatter.ofPattern("dd");
            String fileName = folderPath + "/" + today.format(formatter) + "일.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]");
                String formattedDate = formatter.format(today);
                writer.write(formattedDate + " " + DrinkList.drinks.get(index).getName() + " " + DrinkList.drinks.get(index).getPrice());  //파일을 생성하고 판매 로그 추가
                if (DrinkList.drinks.get(index).getStock() == 0) {
                    writer.write(" Sold Out");      //품절 로그 추가
                }
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
