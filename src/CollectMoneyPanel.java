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
        setVisible(false);

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
        int[][] bounds = {
                {50, 50, 200, 100}, {325, 50, 200, 100}, {625, 50, 200, 100},
                {50, 300, 200, 100}, {325, 300, 200, 100}
        };
        int[][] stockBounds = {
                {215, 50, 200, 100}, {490, 50, 200, 100}, {800, 50, 200, 100},
                {225, 300, 200, 100}, {515, 300, 200, 100}
        };

        for (int i = 0; i < money.length; i++) {
            moneyLabels[i][0] = new JLabel(money[i]);
            moneyLabels[i][0].setHorizontalAlignment(SwingConstants.CENTER);
            moneyLabels[i][0].setFont(moneyFont);
            moneyLabels[i][0].setBounds(bounds[i][0], bounds[i][1], bounds[i][2], bounds[i][3]);
            add(moneyLabels[i][0]);

            moneyLabels[i][1] = new JLabel(" X " + MoneyList.moneyList.get(i).getStock());
            moneyLabels[i][1].setFont(textFont);
            moneyLabels[i][1].setBounds(stockBounds[i][0], stockBounds[i][1], stockBounds[i][2], stockBounds[i][3]);
            add(moneyLabels[i][1]);
        }

        //동전 추가 버튼
        JButton[][] addMoneyButtons = new JButton[5][3];
        for (int i = 0; i < 5; i++) {
            addMoneyButtons[i][0] = new JButton("+5");
            addMoneyButtons[i][1] = new JButton("+10");
            addMoneyButtons[i][2] = new JButton("+50");

            addMoneyButtons[i][0].setFont(textFont);
            addMoneyButtons[i][1].setFont(textFont);
            addMoneyButtons[i][2].setFont(textFont);

            add(addMoneyButtons[i][0]);
            add(addMoneyButtons[i][1]);
            add(addMoneyButtons[i][2]);
        }

        addMoneyButtons[0][0].setBounds(100, 150, 150, 50);
        addMoneyButtons[0][1].setBounds(100, 200, 150, 50);
        addMoneyButtons[0][2].setBounds(100, 250, 150, 50);

        addMoneyButtons[1][0].setBounds(375, 150, 150, 50);
        addMoneyButtons[1][1].setBounds(375, 200, 150, 50);
        addMoneyButtons[1][2].setBounds(375, 250, 150, 50);

        addMoneyButtons[2][0].setBounds(675, 150, 150, 50);
        addMoneyButtons[2][1].setBounds(675, 200, 150, 50);
        addMoneyButtons[2][2].setBounds(675, 250, 150, 50);

        addMoneyButtons[3][0].setBounds(100, 400, 150, 50);
        addMoneyButtons[3][1].setBounds(100, 450, 150, 50);
        addMoneyButtons[3][2].setBounds(100, 500, 150, 50);

        addMoneyButtons[4][0].setBounds(375, 400, 150, 50);
        addMoneyButtons[4][1].setBounds(375, 450, 150, 50);
        addMoneyButtons[4][2].setBounds(375, 500, 150, 50);


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
            collectableMoney.set(0);
            collectableMoneyLabel.setText("<html><div style='text-align: center;'>수금 가능 금액<br>" + collectableMoney + "원</div></html>");
            for (int i = 0; i < money.length; i++) {
                moneyLabels[i][1].setText(" X " + MoneyList.moneyList.get(i).getStock());
            }
        });
    }
}