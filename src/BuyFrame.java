import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class BuyFrame extends JFrame {
    private int currentMoney = 0;  //현재 입력된 돈
    private final JLabel currentMoneyLabel;
    private int pressLogoCount = 0; //로고를 누른 횟수
    private final JButton[] canBuyButtons = new JButton[6];
    private final JButton[] canNotBuyButtons = new JButton[6];
    private int[] insertMoneyCount = new int[5];

    public BuyFrame() throws IOException {
        setTitle("Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 1100);

        getContentPane().setBackground(new Color(252, 255, 216));
        setLayout(null);

        //음료 정보 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Drinks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");
                DrinkList.drinks.add(new Drink(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //거스름돈 정보 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Money.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");
                MoneyList.moneyList.add(new Money(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //판매 패널
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(null);
        buyPanel.setSize(1100, 1100);
        buyPanel.setBackground(new Color(252, 255, 216));
        add(buyPanel);


        Font buttonFont = new Font("Arial", Font.BOLD, 40);

        //입력된 금액 표시
        currentMoneyLabel = new JLabel("현재 금액 : " + currentMoney + "원");
        currentMoneyLabel.setFont(buttonFont);
        currentMoneyLabel.setBounds(410, 700, 400, 150);
        buyPanel.add(currentMoneyLabel);

        //로고 이미지 불러오기
        ImageIcon logoIcon = new ImageIcon("image/logo.png");
        Image logoOriginalImage = logoIcon.getImage();
        Image logoResizedImage = logoOriginalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon logoIconResized = new ImageIcon(logoResizedImage);
        JButton logoButton = new JButton(logoIconResized);
        logoButton.setBounds(50, 700, 150, 150);
        logoButton.setBorderPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setFocusPainted(false);
        logoButton.addActionListener(e -> { //로고를 5번 누르면 관리자 페이지 열기
            pressLogoCount++;
            if (pressLogoCount == 5) {
                pressLogoCount = 0;
                AdminFrame.startAdminThread();
            }
        });
        buyPanel.add(logoButton);


        //물 이미지 불러오기
        ImageIcon waterIcon = new ImageIcon("image/water.png");
        Image waterOriginalImage = waterIcon.getImage();
        Image waterResizedImage = waterOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel waterLabel = new JLabel(new ImageIcon(waterResizedImage));
        waterLabel.setBounds(135, 40, 100, 200);
        buyPanel.add(waterLabel);


        //커피 이미지 불러오기
        ImageIcon coffeeIcon = new ImageIcon("image/coffee.png");
        Image coffeeOriginalImage = coffeeIcon.getImage();
        Image coffeeResizedImage = coffeeOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel coffeeLabel = new JLabel(new ImageIcon(coffeeResizedImage));
        coffeeLabel.setBounds(510, 40, 100, 200);
        buyPanel.add(coffeeLabel);

        //이온 음료 이미지 불러오기
        ImageIcon sportsDrinkIcon = new ImageIcon("image/sportsDrink.png");
        Image sportsDrinkOriginalImage = sportsDrinkIcon.getImage();
        Image sportsDrinkResizedImage = sportsDrinkOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel sportsDrinkLabel = new JLabel(new ImageIcon(sportsDrinkResizedImage));
        sportsDrinkLabel.setBounds(880, 40, 100, 200);
        buyPanel.add(sportsDrinkLabel);

        //탄산 음료 이미지 불러오기
        ImageIcon sodaIcon = new ImageIcon("image/soda.png");
        Image sodaOriginalImage = sodaIcon.getImage();
        Image sodaResizedImage = sodaOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel sodaLabel = new JLabel(new ImageIcon(sodaResizedImage));
        sodaLabel.setBounds(510, 370, 100, 200);
        buyPanel.add(sodaLabel);

        //고급 커피 이미지 불러오기
        ImageIcon premiumCoffeeIcon = new ImageIcon("image/premiumCoffee.png");
        Image premiumCoffeeOriginalImage = premiumCoffeeIcon.getImage();
        Image premiumCoffeeResizedImage = premiumCoffeeOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel premiumCoffeeLabel = new JLabel(new ImageIcon(premiumCoffeeResizedImage));
        premiumCoffeeLabel.setBounds(135, 370, 100, 200);
        buyPanel.add(premiumCoffeeLabel);

        //특별 음료 이미지 불러오기
        ImageIcon specialDrinkIcon = new ImageIcon("image/specialDrink.png");
        Image specialDrinkOriginalImage = specialDrinkIcon.getImage();
        Image specialDrinkResizedImage = specialDrinkOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel specialDrinkLabel = new JLabel(new ImageIcon(specialDrinkResizedImage));
        specialDrinkLabel.setBounds(880, 370, 100, 200);
        buyPanel.add(specialDrinkLabel);

        //구매 가능 버튼 이미지 불러오기
        ImageIcon canBuyButton = new ImageIcon("image/can buy button.png");
        Image canBuyButtonOriginalImage = canBuyButton.getImage();
        Image canBuyButtonResizedImage = canBuyButtonOriginalImage.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
        ImageIcon canBuyButtonResizedImageIcon = new ImageIcon(canBuyButtonResizedImage);

        //구매 불가 버튼 이미지 불러오기
        ImageIcon canNotBuyButton = new ImageIcon("image/can not buy button.png");
        Image canNotBuyButtonOriginalImage = canNotBuyButton.getImage();
        Image canNotBuyButtonResizedImage = canNotBuyButtonOriginalImage.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
        ImageIcon canNotBuyButtonResizedImageIcon = new ImageIcon(canNotBuyButtonResizedImage);


        //가격 표시
        JLabel[] priceLabels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            priceLabels[i] = new JLabel(DrinkList.drinks.get(i).getPrice() + "원");
            if (i < 3) {   //첫번째 줄 음료 가격
                priceLabels[i].setBounds(140 + (i * 370), 240, 120, 50);
            } else {      //두번째 줄 음료 가격
                priceLabels[i].setBounds(140 + ((i % 3) * 370), 570, 120, 50);
            }
            priceLabels[i].setFont(buttonFont);
            buyPanel.add(priceLabels[i]);
        }

        //구매 가능 버튼 이미지 불러오기
        for (int i = 0; i < 6; i++) {
            canBuyButtons[i] = new JButton(canBuyButtonResizedImageIcon);
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
        //물 구매 가능 버튼
        canBuyButtons[0].addActionListener(e -> {
            currentMoney -= DrinkList.drinks.get(0).getPrice();
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            DrinkList.drinks.get(0).decreaseStock();
            updateBuyButton();
        });
        //커피 구매 가능 버튼
        canBuyButtons[1].addActionListener(e -> {
            currentMoney -= DrinkList.drinks.get(1).getPrice();
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            DrinkList.drinks.get(1).decreaseStock();
            updateBuyButton();
        });
        //이온 음료 구매 가능 버튼
        canBuyButtons[2].addActionListener(e -> {
            currentMoney -= DrinkList.drinks.get(2).getPrice();
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            DrinkList.drinks.get(2).decreaseStock();
            updateBuyButton();
        });
        //고급 커피 구매 가능 버튼
        canBuyButtons[3].addActionListener(e -> {
            currentMoney -= DrinkList.drinks.get(3).getPrice();
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            DrinkList.drinks.get(3).decreaseStock();
            updateBuyButton();
        });
        //탄산 음료 구매 가능 버튼
        canBuyButtons[4].addActionListener(e -> {
            currentMoney -= DrinkList.drinks.get(4).getPrice();
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            DrinkList.drinks.get(4).decreaseStock();
            updateBuyButton();
        });
        //특별 음료 구매 가능 버튼
        canBuyButtons[5].addActionListener(e -> {
            currentMoney -= DrinkList.drinks.get(5).getPrice();
            currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
            DrinkList.drinks.get(5).decreaseStock();
            updateBuyButton();
        });


        //구매 불가 표시
        for (int i = 0; i < 6; i++) {
            canNotBuyButtons[i] = new JButton(canNotBuyButtonResizedImageIcon);
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
            moneyButton[i].setFont(buttonFont);
            buyPanel.add(moneyButton[i]);
        }

        //10원 입력 버튼
        moneyButton[0].addActionListener(e -> {
            if(currentMoney+10 <= 7000) {
                insertMoneyCount[0]++;
                currentMoney += 10;
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                MoneyList.moneyList.get(0).increaseStock();
                updateBuyButton();
                updateBuyButton();
            }
        });

        //50원 입력 버튼
        moneyButton[1].addActionListener(e -> {
            if(currentMoney+50 <= 7000) {
                insertMoneyCount[1]++;
                currentMoney += 50;
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                MoneyList.moneyList.get(1).increaseStock();
                updateBuyButton();
            }
        });

        //100원 입력 버튼
        moneyButton[2].addActionListener(e -> {
            if(currentMoney+100 <= 7000) {
                insertMoneyCount[2]++;
                currentMoney += 100;
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                MoneyList.moneyList.get(2).increaseStock();
                updateBuyButton();
            }
        });

        //500원 입력 버튼
        moneyButton[3].addActionListener(e -> {
            if(currentMoney+500 <= 7000) {
                insertMoneyCount[3]++;
                currentMoney += 500;
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                MoneyList.moneyList.get(4).increaseStock();
                updateBuyButton();
            }
        });

        //1000원 입력 버튼
        moneyButton[4].addActionListener(e -> {
            if(currentMoney+1000 <= 7000 && insertMoneyCount[4] < 5) {
                insertMoneyCount[4]++;
                currentMoney += 1000;
                currentMoneyLabel.setText("현재 금액 : " + currentMoney + "원");
                MoneyList.moneyList.get(4).increaseStock();
                updateBuyButton();
            }

        });


        //반환 버튼
        ImageIcon returnButtonImage = new ImageIcon("image/returnButton.png");
        Image returnButtonOriginalImage = returnButtonImage.getImage();
        Image returnButtonResizedImage = returnButtonOriginalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon returnButtonResizedImageIcon = new ImageIcon(returnButtonResizedImage);
        JButton returnButton = new JButton(returnButtonResizedImageIcon);
        returnButton.setBounds(900, 700, 150, 150);
        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        buyPanel.add(returnButton);
        //반환 버튼을 눌렀을 때
        returnButton.addActionListener(e -> {
            while(currentMoney > 0) {
                if(currentMoney >= 1000) {
                    MoneyList.moneyList.get(4).decreaseStock();
                    currentMoney -= 1000;
                }
                else if(currentMoney >= 500) {
                    if(MoneyList.moneyList.get(3).getStock() == 0) {
                        MoneyList.moneyList.get(2).decreaseStock();
                        currentMoney -= 100;
                    }
                    else {
                        MoneyList.moneyList.get(3).decreaseStock();
                        currentMoney -= 500;
                    }
                }
                else if(currentMoney >= 100) {
                    if(MoneyList.moneyList.get(2).getStock() == 0) {
                        MoneyList.moneyList.get(1).decreaseStock();
                        currentMoney -= 50;
                    }
                    else {
                        MoneyList.moneyList.get(2).decreaseStock();
                        currentMoney -= 100;
                    }
                }
                else if(currentMoney >= 50) {
                    if(MoneyList.moneyList.get(1).getStock() == 0) {
                        MoneyList.moneyList.get(0).decreaseStock();
                        currentMoney -= 10;
                    }
                    else {
                        MoneyList.moneyList.get(1).decreaseStock();
                        currentMoney -= 50;
                    }
                }
                else {
                    MoneyList.moneyList.get(0).decreaseStock();
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
                    for(int i = 0;i<DrinkList.drinks.size();i++) {
                        writer.write(DrinkList.drinks.get(i).getName() + " ");
                        writer.write(DrinkList.drinks.get(i).getPrice() + " ");
                        writer.write(DrinkList.drinks.get(i).getStock() + " ");
                        if(i == DrinkList.drinks.size()-1) {
                            writer.write(DrinkList.drinks.get(i).getImagePath());
                        }
                        else {
                            writer.write(DrinkList.drinks.get(i).getImagePath()+"\n");
                        }
                    };
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //거스름돈 정보 최신화
                try(BufferedWriter writer = new BufferedWriter(new FileWriter("Money.txt"))) {
                    for(int i = 0;i<MoneyList.moneyList.size();i++) {
                        writer.write(MoneyList.moneyList.get(i).getName() + " ");
                        writer.write(MoneyList.moneyList.get(i).getPrice() + " ");
                        if(i == MoneyList.moneyList.size()-1) {
                            writer.write(MoneyList.moneyList.get(i).getStock()+"");
                        }
                        else {
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

    private void updateBuyButton() {
        for (int i = 0; i < 6; i++) {
            if (DrinkList.drinks.get(i).getPrice() <= currentMoney && DrinkList.drinks.get(i).getStock() > 0) {

//                //10원짜리가 모자랄 때
//                if (DrinkList.drinks.get(0).getPrice() % 100 > MoneyList.moneyList.get(1).price && MoneyList.moneyList.get(1).getStock() > 0 && MoneyList.moneyList.get(0).getStock() * MoneyList.moneyList.get(0).getPrice() + MoneyList.moneyList.get(1).getPrice() > DrinkList.drinks.get(0).getPrice() % 100) {
//
//                }
//
//                if (DrinkList.drinks.get(0).getPrice() % 100 > MoneyList.moneyList.g)
//
//                    if (DrinkList.drinks.get(0).getPrice() % 100 > MoneyList.moneyList.get(0).getStock() * MoneyList.moneyList.get(0).getPrice()) {
//                        if (MoneyList.moneyList.get(0))
//                    }
//
//                //50원짜리가 모자랄 때
//
//                //100원짜리가 모자랄 때
//
//                //500원짜리가 모자랄 때
//
//
//                if (DrinkList.drinks.get(0).getPrice() % 100) {
//                }
//                if (MoneyList.moneyList.get(1).getStock() == 0) {
//
//                }


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