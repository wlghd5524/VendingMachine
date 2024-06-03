import javax.swing.*;
import java.awt.*;

public class AddDrinkPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);

    AddDrinkPanel() {
        //재고 보충 화면
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼
        JButton backButton = BackButtonGenerator.createBackButton(this);
        add(backButton);


        //음료 이미지
        JLabel[] drinkImageLabel = new JLabel[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkImageLabel[i] = new JLabel(new ImageIcon(new ImageIcon(DrinkList.drinks.get(i).getImagePath()).getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH)));
            if (i < 3) {
                drinkImageLabel[i].setBounds(50 + (i * 300), 40, 100, 200);
            } else {
                drinkImageLabel[i].setBounds(50 + ((i % 3) * 300), 300, 100, 200);
            }
            add(drinkImageLabel[i]);
        }


        JLabel[] drinkStockLabel = new JLabel[DrinkList.drinks.size()];  //음료 개수 표시
        JButton[][] drinkStockButton = new JButton[DrinkList.drinks.size()][3];  //음료 재고 보충 버튼
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
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
