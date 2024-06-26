package adminPage;

import adminPage.util.BackButtonGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

//자판기 매출 화면 패널 생성 클래스
public class MachineSalesReportPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 40);
    Font comboBoxFont = new Font("Arial", Font.PLAIN, 20);

    MachineSalesReportPanel() {
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼
        JButton backButton = BackButtonGenerator.createBackButton(this);
        add(backButton);


        int[] yearSalesAmount = new int[10];               //연간 매출
        int[][] monthSalesAmount = new int[10][12];        //월간 매출 [년][월]
        int[][][] dailySalesAmount = new int[10][12][31];  //일간 매출 [년][월][일]


        //매출 불러오기(매출 파일들을 불러와서 스택에 저장한 후 스택에서 꺼내면서 매출 계산)
        Stack<File> fileStack = new Stack<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        //매출 파일을 불러와서 스택에 push
        for (int i = 2020; i <= now.getYear(); i++) {
            for (int j = 1; j <= 12; j++) {
                for (int k = 1; k <= 31; k++) {
                    String strI = String.format("%02d", i);
                    String strJ = String.format("%02d", j);
                    String strK = String.format("%02d", k);
                    File file = new File("salesReport/" + strI + "년/" + strJ + "월/" + strK + "일.txt");
                    if (file.exists()) {
                        fileStack.push(file);
                    }
                }
            }
        }

        //스택에서 파일 하나씩 꺼내면서 매출 계산
        while (!fileStack.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileStack.pop()))) {
                String line;
                //한 줄 씩 불러와서 공백문자를 기준으로 나눠서 temp[]에 저장
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    String[] temp = line.split(" ");
                    String date = temp[0];
                    int drinkPrice = Integer.parseInt(temp[3]);
                    LocalDate dateTime = LocalDate.parse(date, formatter);
                    int recordedYear = dateTime.getYear();
                    int recordedMonth = dateTime.getMonthValue();
                    int recordedDay = dateTime.getDayOfMonth();
                    dailySalesAmount[recordedYear - 2020][recordedMonth - 1][recordedDay - 1] += drinkPrice;        //일별 매출 추가
                    monthSalesAmount[recordedYear - 2020][recordedMonth - 1] += drinkPrice;                         //월별 매출 추가
                    yearSalesAmount[recordedYear - 2020] += drinkPrice;                                             //연별 매출 추가
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "매출 파일을 읽을 수 없습니다. 관리자 메뉴로 돌아갑니다.");    //매출 파일을 불러오는 중 문제가 생기면 메세지로 알리고 관리자 메뉴로 돌아감
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
        yearLabel.setBounds(200, 100, 100, 50);
        monthLabel.setBounds(400, 100, 100, 50);
        dayLabel.setBounds(600, 100, 100, 50);

        //연별 매출 라벨
        JLabel yearResultLabel = new JLabel();
        yearResultLabel.setFont(textFont);
        yearResultLabel.setVisible(false);
        yearResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yearResultLabel.setBounds(0, 200, 900, 50);

        //월별 매출 라벨
        JLabel monthResultLabel = new JLabel();
        monthResultLabel.setFont(textFont);
        monthResultLabel.setVisible(false);
        monthResultLabel.setBounds(0, 300, 900, 50);
        monthResultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //일별 매출 라벨
        JLabel dayResultLabel = new JLabel();
        dayResultLabel.setFont(textFont);
        dayResultLabel.setVisible(false);
        dayResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayResultLabel.setBounds(0, 400, 900, 50);


        // 연, 월, 일 목록 생성
        List<String> years = generateYearComboBox(2020, 10);
        List<String> months = generateMonthComboBox();
        List<String> days = generateDayComboBox();

        // 날짜 선택을 위한 comboBox
        JComboBox<String> yearComboBox = new JComboBox<>(years.toArray(new String[0]));
        JComboBox<String> monthComboBox = new JComboBox<>(months.toArray(new String[0]));
        JComboBox<String> dayComboBox = new JComboBox<>(days.toArray(new String[0]));
        yearComboBox.setFont(comboBoxFont);
        monthComboBox.setFont(comboBoxFont);
        dayComboBox.setFont(comboBoxFont);
        yearComboBox.setBounds(50, 100, 150, 50);
        monthComboBox.setBounds(250, 100, 150, 50);
        dayComboBox.setBounds(450, 100, 150, 50);
        LocalDate localDate = LocalDate.now();
        yearComboBox.setSelectedItem(String.valueOf(localDate.getYear()));
        monthComboBox.setSelectedItem(String.valueOf(localDate.getMonthValue()));
        dayComboBox.setSelectedItem(String.valueOf(localDate.getDayOfMonth()));

        //날짜 선택 버튼
        JButton selectButton = new JButton("선택");
        selectButton.setFont(textFont);
        selectButton.setBounds(700, 100, 100, 50);
        //날짜 선택 버튼을 눌렀을 때 이벤트(선택된 년 월 일 콤보박스 아이템에 해당하는 매출 출력)
        selectButton.addActionListener(e -> {
            int year = Integer.parseInt((String) yearComboBox.getSelectedItem());
            int month = Integer.parseInt((String) monthComboBox.getSelectedItem());
            int day = Integer.parseInt((String) dayComboBox.getSelectedItem());
            yearResultLabel.setText(year + "년 매출 : " + yearSalesAmount[year - 2020] + "원");
            dayResultLabel.setText(year + "년 " + month + "월 " + day + "일 매출 : " + dailySalesAmount[year - 2020][month - 1][day - 1] + "원");
            monthResultLabel.setText(year + "년 " + month + "월 매출 : " + monthSalesAmount[year - 2020][month - 1] + "원");
            yearResultLabel.setVisible(true);
            dayResultLabel.setVisible(true);
            monthResultLabel.setVisible(true);
        });


        add(yearComboBox);
        add(yearLabel);
        add(monthComboBox);
        add(monthLabel);
        add(dayComboBox);
        add(dayLabel);
        add(selectButton);
        add(dayResultLabel);
        add(monthResultLabel);
        add(yearResultLabel);
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
}
