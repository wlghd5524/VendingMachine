import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DrinkSalesReportPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);
    Font comboBoxFont = new Font("Arial", Font.PLAIN, 20);

    DrinkSalesReportPanel() {
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼
        JButton backButton = BackButtonGenerator.createBackButton(this);
        add(backButton);

        int[] totalSalesAmount = new int[DrinkList.drinks.size()];
        int[][][][] dailySalesAmount = new int[10][12][31][DrinkList.drinks.size()];  //일간 매출 [년][월][일][음료수]
        int[][][] monthSalesAmount = new int[10][12][DrinkList.drinks.size()];        //월간 매출 [년][월][음료수]


        //매출 불러오기
        Queue<File> fileQueue = new LinkedList<>();  //매출 파일을 저장할 큐
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        //매출 파일을 불러와서 큐에 저장
        for(int i = 2020;i<=now.getYear();i++) {
            for(int j = 1;j<=12;j++) {
                for(int k = 1;k<=31;k++) {
                    String strI = String.format("%02d",i);
                    String strJ = String.format("%02d",j);
                    String strK = String.format("%02d",k);
                    File file = new File("salesReport/"+strI+"년/"+strJ+"월/"+strK+"일.txt");
                    if(file.exists()) {
                        fileQueue.add(file);
                    }
                }
            }
        }

        //큐에서 순서대로 꺼내면서 매출 계산
        while(!fileQueue.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileQueue.poll()))) {
                String line;
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    String[] temp = line.split(" ");
                    String date = temp[0];
                    String drinkName = temp[2];
                    int drinkPrice = Integer.parseInt(temp[3]);
                    LocalDate dateTime = LocalDate.parse(date, formatter);
                    int recordedYear = dateTime.getYear();
                    int recordedMonth = dateTime.getMonthValue();
                    int recordedDay = dateTime.getDayOfMonth();
                    for (Drink drink : DrinkList.drinks) {
                        if (drink.getName().equals(drinkName)) {
                            dailySalesAmount[recordedYear - 2020][recordedMonth - 1][recordedDay - 1][DrinkList.drinks.indexOf(drink)] += drinkPrice;
                            monthSalesAmount[recordedYear - 2020][recordedMonth - 1][DrinkList.drinks.indexOf(drink)] += drinkPrice;
                            totalSalesAmount[DrinkList.drinks.indexOf(drink)] += drinkPrice;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "매출 파일을 읽을 수 없습니다. 관리자 메뉴로 돌아갑니다.");
                setVisible(false);
                AdminFrame.adminMenuPanel.setVisible(true);
            }
        }


        //날짜 선택 라벨
        JLabel yearLabel = new JLabel("년");
        JLabel monthLabel = new JLabel("월");
        JLabel dayLabel = new JLabel("일");
        yearLabel.setFont(textFont);
        monthLabel.setFont(textFont);
        dayLabel.setFont(textFont);
        yearLabel.setBounds(180, 100, 100, 50);
        monthLabel.setBounds(380, 100, 100, 50);
        dayLabel.setBounds(580, 100, 100, 50);


        //총 매출 라벨
        JLabel totalResultLabel = new JLabel();
        totalResultLabel.setFont(textFont);
        totalResultLabel.setVisible(false);
        totalResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalResultLabel.setBounds(0, 200, 900, 50);

        //일별 매출 라벨
        JLabel dayResultLabel = new JLabel();
        dayResultLabel.setFont(textFont);
        dayResultLabel.setVisible(false);
        dayResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayResultLabel.setBounds(0, 300, 900, 50);

        //월별 매출 라벨
        JLabel monthResultLabel = new JLabel();
        monthResultLabel.setFont(textFont);
        monthResultLabel.setVisible(false);
        monthResultLabel.setBounds(0, 400, 900, 50);
        monthResultLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // 연, 월, 일, 음료수 목록 생성
        List<String> years = generateYearComboBox(2020, 10);
        List<String> months = generateMonthComboBox();
        List<String> days = generateDayComboBox();
        List<String> drinks = generateDrinkComboBox();


        // 날짜와 음료 선택을 위한 comboBox
        JComboBox<String> yearComboBox = new JComboBox<>(years.toArray(new String[0]));
        JComboBox<String> monthComboBox = new JComboBox<>(months.toArray(new String[0]));
        JComboBox<String> dayComboBox = new JComboBox<>(days.toArray(new String[0]));
        JComboBox<String> drinkComboBox = new JComboBox<>(drinks.toArray(new String[0]));
        yearComboBox.setFont(comboBoxFont);
        monthComboBox.setFont(comboBoxFont);
        dayComboBox.setFont(comboBoxFont);
        drinkComboBox.setFont(comboBoxFont);
        yearComboBox.setBounds(20, 100, 150, 50);
        monthComboBox.setBounds(220, 100, 150, 50);
        dayComboBox.setBounds(420, 100, 150, 50);
        drinkComboBox.setBounds(620, 100, 150, 50);
        LocalDate localDate = LocalDate.now();
        yearComboBox.setSelectedItem(String.valueOf(localDate.getYear()));
        monthComboBox.setSelectedItem(String.valueOf(localDate.getMonthValue()));
        dayComboBox.setSelectedItem(String.valueOf(localDate.getDayOfMonth()));

        //날짜 선택 버튼
        JButton selectButton = new JButton("선택");
        selectButton.setFont(textFont);
        selectButton.setBounds(780, 100, 100, 50);
        selectButton.addActionListener(e -> {
            int year = Integer.parseInt((String) yearComboBox.getSelectedItem());
            int month = Integer.parseInt((String) monthComboBox.getSelectedItem());
            int day = Integer.parseInt((String) dayComboBox.getSelectedItem());
            String drinkName = (String) drinkComboBox.getSelectedItem();
            int drinkIndex = 0;
            int drinkPrice = 0;
            for (Drink drink : DrinkList.drinks) {
                if (drink.getName().equals(drinkName)) {
                    drinkIndex = DrinkList.drinks.indexOf(drink);
                    drinkPrice = drink.getPrice();
                }
            }
            totalResultLabel.setText(drinkName + " 총 매출 : " + (totalSalesAmount[drinkIndex] / drinkPrice) + "개 " + totalSalesAmount[drinkIndex] + "원");
            dayResultLabel.setText(drinkName + " " + year + "년 " + month + "월 " + day + "일 매출 : " + dailySalesAmount[year - 2020][month - 1][day - 1][drinkIndex] / drinkPrice + "개 " + dailySalesAmount[year - 2020][month - 1][day - 1][drinkIndex] + "원");
            monthResultLabel.setText(drinkName + " " + year + "년 " + month + "월 매출 : " + monthSalesAmount[year - 2020][month - 1][drinkIndex] / drinkPrice + "개 " + monthSalesAmount[year - 2020][month - 1][drinkIndex] + "원");
            totalResultLabel.setVisible(true);
            dayResultLabel.setVisible(true);
            monthResultLabel.setVisible(true);
        });

        add(drinkComboBox);
        add(yearComboBox);
        add(yearLabel);
        add(monthComboBox);
        add(monthLabel);
        add(dayComboBox);
        add(dayLabel);
        add(selectButton);
        add(totalResultLabel);
        add(dayResultLabel);
        add(monthResultLabel);
    }

    //ComboBox에 들어갈 년 월 일 숫자 생성기
    private List<String> generateYearComboBox(int startYear, int years) {
        List<String> yearComboBox = new ArrayList<>();
        for (int i = 0; i < years; i++) {
            yearComboBox.add(String.valueOf(startYear + i));
        }
        return yearComboBox;
    }

    private List<String> generateMonthComboBox() {
        List<String> monthComboBox = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.add(String.valueOf(i));
        }
        return monthComboBox;
    }

    private List<String> generateDayComboBox() {
        List<String> dayComboBox = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.add(String.valueOf(i));
        }
        return dayComboBox;
    }

    public List<String> generateDrinkComboBox() {
        List<String> drinkComboBox = new ArrayList<>();
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            drinkComboBox.add(DrinkList.drinks.get(i).getName());
        }
        return drinkComboBox;
    }
}

