package adminPage;

import adminPage.util.BackButtonGenerator;

import javax.swing.*;
import java.awt.*;

import drink.*;

//음료 재고 보충 화면 패널 생성 클래스
public class AddDrinkPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);

    AddDrinkPanel() {
        //재고 보충 화면
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼 생성
        JButton backButton = BackButtonGenerator.createBackButton(this);
        add(backButton);


        //음료 이미지 불러오기
        JLabel[] drinkImageLabel = new JLabel[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkImageLabel[i] = new JLabel(new ImageIcon(new ImageIcon(DrinkList.drinks.get(i).getImagePath()).getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH)));
            //음료 이미지 크기와 위치 조정
            if (i < 3) {
                drinkImageLabel[i].setBounds(50 + (i * 300), 40, 100, 200);
            } else {
                drinkImageLabel[i].setBounds(50 + ((i % 3) * 300), 300, 100, 200);
            }
            add(drinkImageLabel[i]);
        }


        JLabel[] drinkStockLabel = new JLabel[DrinkList.drinks.size()];  //음료 개수 표시
        JButton[][] drinkStockButton = new JButton[DrinkList.drinks.size()][3];  //음료 재고 보충 버튼
        //음료 개수 표시 라벨과 음료 재고 보충 버튼 설정
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkStockButton[i][0] = new JButton("+ " + 1);
            drinkStockButton[i][1] = new JButton("+ " + 5);
            drinkStockButton[i][2] = new JButton("+ " + 10);
            for (int j = 0; j < 3; j++) {
                //이벤트 설정(addActionListener)를 위한 final 변수 생성
                int finalI = i;
                int finalJ = j;
                // 음료 재고 보충 버튼 크기 및 위치 조정
                if (i < 3) {
                    drinkStockButton[i][j].setBounds(150 + (i * 300), 80 + (j * 55), 125, 50);
                } else {
                    drinkStockButton[i][j].setBounds(150 + (i % 3 * 300), 350 + (j * 55), 125, 50);
                }
                drinkStockButton[i][j].setFont(textFont);
                add(drinkStockButton[i][j]);
                //각 버튼을 눌렀을 때 이벤트 설정
                drinkStockButton[i][j].addActionListener(e -> {
                    if (finalJ == 0) {
                        DrinkList.drinks.get(finalI).setStock(DrinkList.drinks.get(finalI).getStock() + 1);             //재고+1
                    } else {
                        DrinkList.drinks.get(finalI).setStock(DrinkList.drinks.get(finalI).getStock() + (finalJ * 5));  //재고+5, 재고+10
                    }
                    drinkStockLabel[finalI].setText(" X " + DrinkList.drinks.get(finalI).getStock());                   //재고 표시 텍스트 최신화
                });
            }
            //음료 재고 수 표시 라벨
            drinkStockLabel[i] = new JLabel(" X " + DrinkList.drinks.get(i).getStock());
            drinkStockLabel[i].setFont(textFont);
            //음료 재고 수 표시 라벨 크기 및 위치 조정
            if (i < 3) {
                drinkStockLabel[i].setBounds(150 + (i * 300), 0, 200, 100);
            } else {
                drinkStockLabel[i].setBounds(150 + (i % 3 * 300), 270, 200, 100);
            }
            add(drinkStockLabel[i]);
        }
    }
}
