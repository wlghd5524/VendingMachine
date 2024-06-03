import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectMoneyPanel extends JPanel {
    Font moneyFont = new Font("Arial", Font.BOLD, 60);
    Font textFont = new Font("Arial", Font.BOLD, 30);

    public CollectMoneyPanel() {
        //수금 화면
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼
        JButton backButton = new JButton(new ImageIcon(new ImageIcon("image/back.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        backButton.setBounds(0, 0, 50, 50);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        add(backButton);
        backButton.addActionListener(e -> {
            setVisible(false);
            AdminFrame.adminMenuPanel.setVisible(true);
        });


        // 각 화폐를 나타내는 라벨
        String[] money = {"10원", "50원", "100원", "500원", "1000원"};
        JLabel[][] moneyLabels = new JLabel[money.length][2];
        int[][] moneyLabelsLocations = {
                {50, 50}, {325, 50}, {625, 50}, {50, 300}, {325, 300}
        };
        int[][] stockLabelsLocations = {
                {215, 50}, {490, 50}, {800, 50}, {225, 300}, {515, 300}
        };

        for (int i = 0; i < money.length; i++) {
            moneyLabels[i][0] = new JLabel(money[i]);
            moneyLabels[i][0].setHorizontalAlignment(SwingConstants.CENTER);
            moneyLabels[i][0].setFont(moneyFont);
            moneyLabels[i][0].setBounds(moneyLabelsLocations[i][0], moneyLabelsLocations[i][1],200,100);
            add(moneyLabels[i][0]);

            moneyLabels[i][1] = new JLabel(" X " + MoneyList.moneyList.get(i).getStock());
            moneyLabels[i][1].setFont(textFont);
            moneyLabels[i][1].setBounds(stockLabelsLocations[i][0], stockLabelsLocations[i][1],200,100);
            add(moneyLabels[i][1]);
        }

        int[][][] moneyButtonLocations = {
                {{100, 150}, {100, 200}, {100, 250}},
                {{375, 150}, {375, 200}, {375, 250}},
                {{675, 150}, {675, 200}, {675, 250}},
                {{100, 400}, {100, 450}, {100, 500}},
                {{375, 400}, {375, 450}, {375, 500}}
        };
        //동전 추가 버튼
        JButton[][] addMoneyButtons = new JButton[5][3];
        for (int i = 0; i < 5; i++) {
            Money moneyI = MoneyList.moneyList.get(i);
            addMoneyButtons[i][0] = new JButton("+5");
            addMoneyButtons[i][1] = new JButton("+10");
            addMoneyButtons[i][2] = new JButton("+50");
            int finalI = i;
            addMoneyButtons[i][0].addActionListener(e -> {
                moneyI.setStock(moneyI.getStock()+5);
                moneyLabels[finalI][1].setText(" X "+moneyI.getStock());
            });
            addMoneyButtons[i][1].addActionListener(e -> {
                moneyI.setStock(moneyI.getStock()+10);
                moneyLabels[finalI][1].setText(" X "+moneyI.getStock());

            });
            addMoneyButtons[i][2].addActionListener(e -> {
                moneyI.setStock(moneyI.getStock()+50);
                moneyLabels[finalI][1].setText(" X "+moneyI.getStock());
            });

            for(int j = 0;j<3;j++) {
                addMoneyButtons[i][j].setFont(textFont);
                addMoneyButtons[i][j].setBounds(moneyButtonLocations[i][j][0],moneyButtonLocations[i][j][1], 150, 50);
                add(addMoneyButtons[i][j]);
            }
        }

        AtomicInteger collectableMoney = new AtomicInteger();
        for (int i = 0; i < 5; i++) {
            int stock = MoneyList.moneyList.get(i).getStock();
            while (stock > 10) {
                collectableMoney.addAndGet(MoneyList.moneyList.get(i).getPrice());
                stock--;
            }
        }
        JLabel collectableMoneyLabel = new JLabel("<html><div style='text-align: center;'>수금 가능 금액<br>" + collectableMoney + "원</div></html>");
        collectableMoneyLabel.setFont(textFont);
        collectableMoneyLabel.setBounds(650, 350, 500, 100);
        add(collectableMoneyLabel);

        JButton collectButton = new JButton("수금");
        collectButton.setFont(textFont);
        collectButton.setBounds(700, 450, 100, 100);
        add(collectButton);
        collectButton.addActionListener(e -> {
            for (int i = 0; i < 5; i++) {
                while (MoneyList.moneyList.get(i).getStock() > 10) {
                    MoneyList.moneyList.get(i).setStock(MoneyList.moneyList.get(i).getStock() - 1);
                }
            }
            if (collectableMoney.get() == 0) {
                JOptionPane.showMessageDialog(null,"수금할만한 돈이 부족합니다.");
            }
            else {
                JOptionPane.showMessageDialog(null,collectableMoney+"원이 수금되었습니다.");
            }
            collectableMoney.set(0);
            collectableMoneyLabel.setText("<html><div style='text-align: center;'>수금 가능 금액<br>" + collectableMoney + "원</div></html>");
            for (int i = 0; i < money.length; i++) {
                moneyLabels[i][1].setText(" X " + MoneyList.moneyList.get(i).getStock());
            }
        });
    }
}