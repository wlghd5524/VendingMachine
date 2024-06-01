import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BuyFrame extends JFrame {
    private int currentMoney = 0;                                   //현재 입력된 돈
    private final JLabel currentMoneyLabel;                         //현재 입력된 돈을 알려주는 라벨
    static int pressLogoCount = 0;                                  //로고를 누른 횟수
    private final JButton[] canBuyButtons = new JButton[6];         //각 음료의 구매 가능 버튼
    private final JButton[] canNotBuyButtons = new JButton[6];      //각 음료의 구매 불가능 버튼
    private int[] insertMoneyCount = new int[5];                    //입력된 각 화폐 개수
    static JLabel[] drinkImageLabel = new JLabel[6];                //음료 이미지 라벨
    Font textFont = new Font("Arial", Font.BOLD, 40);    //텍스트 폰트

    public BuyFrame() throws IOException {
        setTitle("Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             //창을 닫으면 프로그램 종료 설정
        setResizable(false);                                        //창 크기 변경 불가
        setSize(1100, 1100);                           //창 크기 설정

        getContentPane().setBackground(new Color(252, 255, 216));   //백그라운드 색 변경
        setLayout(null);
        //음료 정보 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Drinks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");        //공백 문자를 기준으로 음료수 정보를 나눠서 temp에 저장. temp[0]:음료수 이름 temp[1]:가격 temp[2]:재고 temp[3]:이미지 경로
                DrinkList.drinks.add(new Drink(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]));  //생성자로 음료수 정보 초기화하고 Linked-list에 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //거스름돈 정보 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Money.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");      //공백 문자를 기준으로 거스름돈 정보를 나눠서 temp에 저장. temp[0]:이름 temp[1]:가격 temp[2]:재고
                MoneyList.moneyList.add(new Money(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));      //생성자로 돈 정보 초기화하고 Linked-list에 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //판매 페이지 패널
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(null);
        buyPanel.setSize(1100, 1100);
        buyPanel.setBackground(new Color(252, 255, 216));
        add(buyPanel);


        //입력된 금액 표시
        currentMoneyLabel = new JLabel("현재 금액 : " + currentMoney + "원");
        currentMoneyLabel.setFont(textFont);
        currentMoneyLabel.setBounds(410, 700, 400, 150);
        buyPanel.add(currentMoneyLabel);

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
        buyPanel.add(logoButton);


        //음료 이미지 불러오기
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkImageLabel[i] = new JLabel(new ImageIcon(new ImageIcon(DrinkList.drinks.get(i).getImagePath()).getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH)));
            if (i < 3) {
                drinkImageLabel[i].setBounds(135 + (i * 370), 40, 100, 200);
            } else {
                drinkImageLabel[i].setBounds(135 + ((i % 3) * 370), 370, 100, 200);
            }
            buyPanel.add(drinkImageLabel[i]);
        }


        //구매 가능 버튼 이미지 불러오기
        ImageIcon canBuyButtonIcon = new ImageIcon(new ImageIcon("image/can buy button.png").getImage().getScaledInstance(250, 50, Image.SCALE_SMOOTH));

        //구매 불가 버튼 이미지 불러오기
        ImageIcon canNotBuyButtonIcon = new ImageIcon(new ImageIcon("image/can not buy button.png").getImage().getScaledInstance(250, 50, Image.SCALE_SMOOTH));

        //가격 표시
        JLabel[] priceLabels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            priceLabels[i] = new JLabel(DrinkList.drinks.get(i).getPrice() + "원");
            if (i < 3) {   //첫번째 줄 음료 가격
                priceLabels[i].setBounds(140 + (i * 370), 240, 120, 50);
            } else {      //두번째 줄 음료 가격
                priceLabels[i].setBounds(140 + ((i % 3) * 370), 570, 120, 50);
            }
            priceLabels[i].setFont(textFont);
            buyPanel.add(priceLabels[i]);
        }

        //구매 가능 버튼 이미지 불러오기
        for (int i = 0; i < 6; i++) {
            canBuyButtons[i] = new JButton(canBuyButtonIcon);
            if (i < 3) {
                canBuyButtons[i].setBounds(60 + (i * 370), 290, 250, 50);
            } else {
                canBuyButtons[i].setBounds(60 + ((i % 3) * 370), 620, 250, 50);
            }
            canBuyButtons[i].setBorderPainted(false);
            canBuyButtons[i].setContentAreaFilled(false);
            canBuyButtons[i].setFocusPainted(false);
            buyPanel.add(canBuyButtons[i]);
        }

        //구매 가능 버튼을 눌렀을 때 이벤트
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            canBuyButtons[i].addActionListener(e -> {
                currentMoney -= DrinkList.drinks.get(finalI).getPrice();
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                DrinkList.drinks.get(finalI).setStock(DrinkList.drinks.get(finalI).getStock() - 1);
                updateBuyButton();
                //매출 추가
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("SalesReport.txt", true))) {
                    LocalDateTime today = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]");
                    String formattedDate = formatter.format(today);
                    writer.write(formattedDate + " " + DrinkList.drinks.get(finalI).getName() + " " + DrinkList.drinks.get(finalI).getPrice());
                    if (DrinkList.drinks.get(finalI).getStock() == 0) {
                        writer.write(" Sold Out");
                    }
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }

        //구매 불가 표시
        for (int i = 0; i < 6; i++) {
            canNotBuyButtons[i] = new JButton(canNotBuyButtonIcon);
            if (i < 3) {
                canNotBuyButtons[i].setBounds(60 + (i * 370), 290, 250, 50);
            } else {
                canNotBuyButtons[i].setBounds(60 + ((i % 3) * 370), 620, 250, 50);
            }
            canNotBuyButtons[i].setBorderPainted(false);
            canNotBuyButtons[i].setContentAreaFilled(false);
            canNotBuyButtons[i].setFocusPainted(false);
            buyPanel.add(canNotBuyButtons[i]);
        }
        updateBuyButton();


        //화폐 입력 버튼
        JButton[] moneyButton = new JButton[5];
        for (int i = 0; i < 5; i++) {
            moneyButton[i] = new JButton(MoneyList.moneyList.get(i).getName());
            moneyButton[i].setBounds(50 + (i * 210), 900, 150, 100);
            moneyButton[i].setFont(textFont);
            buyPanel.add(moneyButton[i]);
            int finalI = i;
            moneyButton[i].addActionListener(e -> {
                if (currentMoney + MoneyList.moneyList.get(finalI).getPrice() <= 7000) {
                    insertMoneyCount[finalI]++;
                    currentMoney += MoneyList.moneyList.get(finalI).getPrice();
                    currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                    MoneyList.moneyList.get(finalI).setStock(MoneyList.moneyList.get(finalI).getStock() + 1);
                    updateBuyButton();
                    updateBuyButton();
                }
            });
        }


        //반환 버튼
        JButton returnButton = new JButton(new ImageIcon(new ImageIcon("image/returnButton.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        returnButton.setBounds(900, 700, 150, 150);
        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        buyPanel.add(returnButton);
        //반환 버튼을 눌렀을 때
        returnButton.addActionListener(e -> {
            while (currentMoney > 0) {
                if (currentMoney >= 1000) {
                    MoneyList.moneyList.get(4).setStock(MoneyList.moneyList.get(4).getStock() - 1);
                    ;
                    currentMoney -= 1000;
                } else if (currentMoney >= 500) {
                    if (MoneyList.moneyList.get(3).getStock() == 0) {
                        MoneyList.moneyList.get(2).setStock(MoneyList.moneyList.get(2).getStock() - 1);
                        currentMoney -= 100;
                    } else {
                        MoneyList.moneyList.get(3).setStock(MoneyList.moneyList.get(3).getStock() - 1);
                        currentMoney -= 500;
                    }
                } else if (currentMoney >= 100) {
                    if (MoneyList.moneyList.get(2).getStock() == 0) {
                        MoneyList.moneyList.get(1).setStock(MoneyList.moneyList.get(1).getStock() - 1);
                        currentMoney -= 50;
                    } else {
                        MoneyList.moneyList.get(2).setStock(MoneyList.moneyList.get(2).getStock() - 1);
                        currentMoney -= 100;
                    }
                } else if (currentMoney >= 50) {
                    if (MoneyList.moneyList.get(1).getStock() == 0) {
                        MoneyList.moneyList.get(0).setStock(MoneyList.moneyList.get(0).getStock() - 1);
                        currentMoney -= 10;
                    } else {
                        MoneyList.moneyList.get(1).setStock(MoneyList.moneyList.get(1).getStock() - 1);
                        currentMoney -= 50;
                    }
                } else {
                    MoneyList.moneyList.get(0).setStock(MoneyList.moneyList.get(0).getStock() - 1);
                    currentMoney -= 10;
                }
            }
            currentMoney = 0;
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            updateBuyButton();
        });

        // 프로그램이 종료될 때
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //음료 정보 최신화
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Drinks.txt"))) {
                    for (int i = 0; i < DrinkList.drinks.size(); i++) {
                        writer.write(DrinkList.drinks.get(i).getName() + " ");
                        writer.write(DrinkList.drinks.get(i).getPrice() + " ");
                        writer.write(DrinkList.drinks.get(i).getStock() + " ");
                        if (i == DrinkList.drinks.size() - 1) {
                            writer.write(DrinkList.drinks.get(i).getImagePath());
                        } else {
                            writer.write(DrinkList.drinks.get(i).getImagePath() + "\n");
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //거스름돈 정보 최신화
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Money.txt"))) {
                    for (int i = 0; i < MoneyList.moneyList.size(); i++) {
                        writer.write(MoneyList.moneyList.get(i).getName() + " ");
                        writer.write(MoneyList.moneyList.get(i).getPrice() + " ");
                        if (i == MoneyList.moneyList.size() - 1) {
                            writer.write(MoneyList.moneyList.get(i).getStock() + "");
                        } else {
                            writer.write(MoneyList.moneyList.get(i).getStock() + "\n");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    //구매 불가능과 구매 가능 버튼 표시
    private void updateBuyButton() {
        for (int i = 0; i < 6; i++) {
            if (DrinkList.drinks.get(i).getPrice() <= currentMoney && DrinkList.drinks.get(i).getStock() > 0) {


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

}