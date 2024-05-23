import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class Frame extends JFrame {
    int currentMoney = 0;
    JLabel currentMoneyLabel;
    int pressLogoCount = 0;
    public Frame() throws IOException {
        setTitle("Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 1100);


        getContentPane().setBackground(new Color(252, 255, 216));
        setLayout(null);

        DrinkList.drinks.add(new Drink("삼다수", 450, "image/water.png"));
        DrinkList.drinks.add(new Drink("조지아", 500, "image/coffee.png"));
        DrinkList.drinks.add(new Drink("2%", 550, "image/sportsDrink.png"));
        DrinkList.drinks.add(new Drink("TOP", 700, "image/premiumCoffee.png"));
        DrinkList.drinks.add(new Drink("콜라", 750, "image/soda.png"));
        DrinkList.drinks.add(new Drink("몬스터", 800, "image/specialDrink.png"));

        MoneyList.moneyList.add(new Money("10원",10,10));
        MoneyList.moneyList.add(new Money("50원", 50, 10));
        MoneyList.moneyList.add(new Money("100원", 100, 10));
        MoneyList.moneyList.add(new Money("500원", 500, 10));
        MoneyList.moneyList.add(new Money("1000원", 1000, 10));

        //구매 패널
        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(null);
        buyPanel.setSize(1100,1100);
        buyPanel.setBackground(new Color(252, 255, 216));
        add(buyPanel);


        //관리자 패널
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setBackground(new Color(252, 255, 216));
        adminPanel.setSize(1100,1100);
        add(adminPanel);
        adminPanel.setVisible(false);


        Font buttonFont = new Font("Arial", Font.BOLD, 40);

        //입력된 금액 표시
        currentMoneyLabel = new JLabel("현재 금액 : "+currentMoney +"원");
        currentMoneyLabel.setFont(buttonFont);
        currentMoneyLabel.setBounds(410,700,400,150);
        buyPanel.add(currentMoneyLabel);

        //로고 이미지 불러오기
        ImageIcon logoIcon = new ImageIcon("image/logo.png");
        Image logoOriginalImage = logoIcon.getImage();
        Image logoResizedImage = logoOriginalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon logoIconResized = new ImageIcon(logoResizedImage);
        JButton logoButton = new JButton(logoIconResized);
        logoButton.setBounds(50,700,150,150);
        logoButton.setBorderPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setFocusPainted(false);
        logoButton.addActionListener(e -> {
            pressLogoCount++;
            if(pressLogoCount == 5){
                pressLogoCount = 0;
                buyPanel.setVisible(false);
                adminPanel.setVisible(true);
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
        ImageIcon sportsDrinkIcon = new ImageIcon("image/sports drink.png");
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
        sodaLabel.setBounds(135, 370, 100, 200);
        buyPanel.add(sodaLabel);

        //고급 커피 이미지 불러오기
        ImageIcon premiumCoffeeIcon = new ImageIcon("image/premium coffee.png");
        Image premiumCoffeeOriginalImage = premiumCoffeeIcon.getImage();
        Image premiumCoffeeResizedImage = premiumCoffeeOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        JLabel premiumCoffeeLabel = new JLabel(new ImageIcon(premiumCoffeeResizedImage));
        premiumCoffeeLabel.setBounds(510, 370, 100, 200);
        buyPanel.add(premiumCoffeeLabel);

        //특별 음료 이미지 불러오기
        ImageIcon specialDrinkIcon = new ImageIcon("image/special drink.png");
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
        JLabel [] priceLabels = new JLabel[6];
        for(int i = 0;i<6;i++) {
            priceLabels[i] = new JLabel(DrinkList.drinks.get(i).getPrice()+"원");
            if(i<3) {
                priceLabels[i].setBounds(140+(i*370), 240, 120, 50);
            }
            else {
                priceLabels[i].setBounds(140+((i%3)*370), 570, 120, 50);
            }
            priceLabels[i].setFont(buttonFont);
            buyPanel.add(priceLabels[i]);
        }



        //물 구매 가능 버튼
        JButton canBuyWaterButton = new JButton(canBuyButtonResizedImageIcon);
        canBuyWaterButton.setBounds(60, 290, 250, 50);
        canBuyWaterButton.setFont(buttonFont);
        canBuyWaterButton.setBorderPainted(false);
        canBuyWaterButton.setContentAreaFilled(false);
        canBuyWaterButton.setFocusPainted(false);
        buyPanel.add(canBuyWaterButton);

        //물 구매 불가 표시
        JLabel canNotBuyWaterLabel = new JLabel(canNotBuyButtonResizedImageIcon);
        canNotBuyWaterLabel.setBounds(60, 290, 250, 50);
        buyPanel.add(canNotBuyWaterLabel);



        //커피 구매 가능 버튼
        JButton canBuyCoffee = new JButton(canBuyButtonResizedImageIcon);
        canBuyCoffee.setBounds(430, 290, 250, 50);
        canBuyCoffee.setBorderPainted(false);
        canBuyCoffee.setContentAreaFilled(false);
        canBuyCoffee.setFocusPainted(false);
        buyPanel.add(canBuyCoffee);
        //커피 구매 불가 표시
        JLabel canNotBuyCoffeeLabel = new JLabel(canNotBuyButtonResizedImageIcon);
        canNotBuyCoffeeLabel.setBounds(430, 290, 250, 50);
        buyPanel.add(canNotBuyCoffeeLabel);

        //이온 음료 구매 가능 버튼
        JButton canBuySportsDrink = new JButton(canBuyButtonResizedImageIcon);
        canBuySportsDrink.setBounds(805, 290, 250, 50);
        canBuySportsDrink.setBorderPainted(false);
        canBuySportsDrink.setContentAreaFilled(false);
        canBuySportsDrink.setFocusPainted(false);
        buyPanel.add(canBuySportsDrink);
        //이온 음료 구매 불가 표시
        JLabel canNotBuySportsDrinkLabel = new JLabel(canNotBuyButtonResizedImageIcon);
        canNotBuySportsDrinkLabel.setBounds(805, 290, 250, 50);
        buyPanel.add(canNotBuySportsDrinkLabel);


        //고급 커피 구매 가능 버튼
        JButton canBuyPremiumCoffee = new JButton(canBuyButtonResizedImageIcon);
        canBuyPremiumCoffee.setBounds(60, 620, 250, 50);
        canBuyPremiumCoffee.setBorderPainted(false);
        canBuyPremiumCoffee.setContentAreaFilled(false);
        canBuyPremiumCoffee.setFocusPainted(false);
        buyPanel.add(canBuyPremiumCoffee);
        //고급 커피 구매 불가 표시
        JLabel canNotBuyPremiumCoffeeLabel = new JLabel(canNotBuyButtonResizedImageIcon);
        canNotBuyPremiumCoffeeLabel.setBounds(430, 620, 250, 50);
        buyPanel.add(canNotBuyPremiumCoffeeLabel);

        //탄산 음료 구매 가능 버튼
        JButton canBuySoda = new JButton(canBuyButtonResizedImageIcon);
        canBuySoda.setBounds(435, 620, 250, 50);
        canBuySoda.setBorderPainted(false);
        canBuySoda.setContentAreaFilled(false);
        canBuySoda.setFocusPainted(false);
        buyPanel.add(canBuySoda);
        //탄산 음료 구매 불가 표시
        JLabel canNotBuySodaLabel = new JLabel(canNotBuyButtonResizedImageIcon);
        canNotBuySodaLabel.setBounds(435, 620, 250, 50);
        buyPanel.add(canNotBuySodaLabel);

        //특별 음료 구매 가능 버튼
        JButton canBuySpecialDrink = new JButton(canBuyButtonResizedImageIcon);
        canBuySpecialDrink.setBounds(805, 620, 250, 50);
        canBuySpecialDrink.setBorderPainted(false);
        canBuySpecialDrink.setContentAreaFilled(false);
        canBuySpecialDrink.setFocusPainted(false);
        buyPanel.add(canBuySpecialDrink);
        //특별 음료 구매 불가 표시
        JLabel canNotBuySpecialDrinkLabel = new JLabel(canNotBuyButtonResizedImageIcon);
        canNotBuySpecialDrinkLabel.setBounds(805, 620, 250, 50);
        buyPanel.add(canNotBuySpecialDrinkLabel);


        //화폐 입력 버튼
        JButton [] moneyButton = new JButton[5];
        for(int i = 0;i<5;i++) {
            moneyButton[i] = new JButton(MoneyList.moneyList.get(i).getName());
            moneyButton[i].setBounds(50+(i*210), 900, 150,100);
            moneyButton[i].setFont(buttonFont);
            buyPanel.add(moneyButton[i]);
        }


        //10원 입력 버튼
        moneyButton[0].addActionListener(e -> {
            currentMoney += 10;
            currentMoneyLabel.setText("현재 금액 : "+currentMoney+"원");
            MoneyList.moneyList.get(0).increaseStock();
        });

        //50원 입력 버튼
        moneyButton[1].addActionListener(e -> {
            currentMoney += 50;
            currentMoneyLabel.setText("현재 금액 : "+currentMoney+"원");
            MoneyList.moneyList.get(1).increaseStock();
        });

        //100원 입력 버튼
        moneyButton[2].addActionListener(e -> {
            currentMoney += 100;
            currentMoneyLabel.setText("현재 금액 : "+currentMoney+"원");
            MoneyList.moneyList.get(2).increaseStock();
        });

        //500원 입력 버튼
        moneyButton[3].addActionListener(e -> {
            currentMoney += 500;
            currentMoneyLabel.setText("현재 금액 : "+currentMoney+"원");
            MoneyList.moneyList.get(4).increaseStock();
        });

        //1000원 입력 버튼
        moneyButton[4].addActionListener(e -> {
            currentMoney += 1000;
            currentMoneyLabel.setText("현재 금액 : "+currentMoney+"원");
            MoneyList.moneyList.get(5).increaseStock();
        });

        







        //반환 버튼
        ImageIcon returnButtonImage = new ImageIcon("image/return button.png");
        Image returnButtonOriginalImage = returnButtonImage.getImage();
        Image returnButtonResizedImage = returnButtonOriginalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon returnButtonResizedImageIcon = new ImageIcon(returnButtonResizedImage);
        JButton returnButton = new JButton(returnButtonResizedImageIcon);
        returnButton.setBounds(900, 700, 150, 150);
        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        buyPanel.add(returnButton);
        returnButton.addActionListener(e -> {
            currentMoney = 0;

        });

        setVisible(true);

    }


    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Frame().setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Frame frame = new Frame();
    }



}