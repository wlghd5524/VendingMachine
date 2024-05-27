import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddDrinkPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);

    AddDrinkPanel() {
        //재고 보충 화면
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(false);

        //뒤로 가기 버튼
        ImageIcon backIcon = new ImageIcon("image/back.png");
        Image backOriginalImage = backIcon.getImage();
        Image backResizedImage = backOriginalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon backIconResized = new ImageIcon(backResizedImage);
        JButton backButton = new JButton(backIconResized);
        backButton.setBounds(0, 0, 50, 50);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        add(backButton);
        backButton.addActionListener(e -> {
            setVisible(false);
            AdminFrame.adminPanel.setVisible(true);
        });


        //음료 이미지
        JLabel[] drinkImageLabel = new JLabel[6];
        //물 이미지 불러오기
        ImageIcon waterIcon = new ImageIcon("image/water.png");
        Image waterOriginalImage = waterIcon.getImage();
        Image waterResizedImage = waterOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        drinkImageLabel[0] = new JLabel(new ImageIcon(waterResizedImage));
        drinkImageLabel[0].setBounds(50, 40, 100, 200);
        add(drinkImageLabel[0]);

        //커피 이미지 불러오기
        ImageIcon coffeeIcon = new ImageIcon("image/coffee.png");
        Image coffeeOriginalImage = coffeeIcon.getImage();
        Image coffeeResizedImage = coffeeOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        drinkImageLabel[1] = new JLabel(new ImageIcon(coffeeResizedImage));
        drinkImageLabel[1].setBounds(350, 40, 100, 200);
        add(drinkImageLabel[1]);

        //이온 음료 이미지 불러오기
        ImageIcon sportsDrinkIcon = new ImageIcon("image/sportsDrink.png");
        Image sportsDrinkOriginalImage = sportsDrinkIcon.getImage();
        Image sportsDrinkResizedImage = sportsDrinkOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        drinkImageLabel[2] = new JLabel(new ImageIcon(sportsDrinkResizedImage));
        drinkImageLabel[2].setBounds(650, 40, 100, 200);
        add(drinkImageLabel[2]);

        //탄산 음료 이미지 불러오기
        ImageIcon sodaIcon = new ImageIcon("image/soda.png");
        Image sodaOriginalImage = sodaIcon.getImage();
        Image sodaResizedImage = sodaOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        drinkImageLabel[3] = new JLabel(new ImageIcon(sodaResizedImage));
        drinkImageLabel[3].setBounds(350, 300, 100, 200);
        add(drinkImageLabel[3]);

        //고급 커피 이미지 불러오기
        ImageIcon premiumCoffeeIcon = new ImageIcon("image/premiumCoffee.png");
        Image premiumCoffeeOriginalImage = premiumCoffeeIcon.getImage();
        Image premiumCoffeeResizedImage = premiumCoffeeOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        drinkImageLabel[4] = new JLabel(new ImageIcon(premiumCoffeeResizedImage));
        drinkImageLabel[4].setBounds(50, 300, 100, 200);
        add(drinkImageLabel[4]);

        //특별 음료 이미지 불러오기
        ImageIcon specialDrinkIcon = new ImageIcon("image/specialDrink.png");
        Image specialDrinkOriginalImage = specialDrinkIcon.getImage();
        Image specialDrinkResizedImage = specialDrinkOriginalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
        drinkImageLabel[5] = new JLabel(new ImageIcon(specialDrinkResizedImage));
        drinkImageLabel[5].setBounds(650, 300, 100, 200);
        add(drinkImageLabel[5]);


        JLabel[] drinkStockLabel = new JLabel[6];  //음료 개수 표시
        JButton[][] drinkStockButton = new JButton[6][3];  //음료 재고 보충 버튼
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                if (j == 0) {
                    drinkStockButton[i][j] = new JButton("+ " + 1);
                } else {
                    drinkStockButton[i][j] = new JButton("+ " + j * 5);
                }
                drinkStockButton[i][j].setFont(textFont);
                if (i < 3) {
                    drinkStockButton[i][j].setBounds(150 + (i * 300), 80 + (j * 55), 125, 50);
                } else {
                    drinkStockButton[i][j].setBounds(150 + (i % 3 * 300), 350 + (j * 55), 125, 50);
                }
                add(drinkStockButton[i][j]);
                drinkStockButton[i][j].addActionListener(e -> {
                    if (finalJ == 0) {
                        DrinkList.drinks.get(finalI).setStock(DrinkList.drinks.get(finalI).getStock() + 1);
                    } else {
                        DrinkList.drinks.get(finalI).setStock(DrinkList.drinks.get(finalI).getStock() + (finalJ * 5));
                    }
                    drinkStockLabel[finalI].setText(" X " + DrinkList.drinks.get(finalI).getStock());
                });
            }
            drinkStockLabel[i] = new JLabel(" X " + DrinkList.drinks.get(i).getStock());
            drinkStockLabel[i].setFont(textFont);
            if (i < 3) {
                drinkStockLabel[i].setBounds(150 + (i * 300), 0, 200, 100);
            } else {
                drinkStockLabel[i].setBounds(150 + (i % 3 * 300), 270, 200, 100);
            }
            add(drinkStockLabel[i]);
        }


    }

}
