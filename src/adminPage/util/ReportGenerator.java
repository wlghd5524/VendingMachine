package adminPage.util;

import drink.DrinkList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ReportGenerator {
    public ReportGenerator() {
        try {
            Random drinkRand = new Random();
            drinkRand.setSeed(System.currentTimeMillis());
            drinkRand.nextInt(6);

            Random hourRand = new Random();
            hourRand.setSeed(System.currentTimeMillis());
            hourRand.nextInt(24);

            Random minRand = new Random();
            minRand.setSeed(System.currentTimeMillis());
            minRand.nextInt(60);

            Random secRand = new Random();
            secRand.setSeed(System.currentTimeMillis());
            secRand.nextInt(60);

            List<String> logList = new ArrayList<>();

            for (int i = 2020; i <= 2024; i++) {
                for (int j = 1; j <= 12; j++) {
                    for (int k = 1; k <= 31; k++) {
                        String strYear = String.format("%02d", i);
                        String strMonth = String.format("%02d", j);
                        String strDay = String.format("%02d", k);
                        String folderPath = "salesReport/" + strYear + "년";
                        File folder = new File(folderPath);
                        if (!folder.exists()) {
                            folder.mkdirs(); // 연도별 폴더 생성
                        }
                        folderPath += "/" + strMonth + "월";
                        folder = new File(folderPath);
                        if (!folder.exists()) {
                            folder.mkdirs(); // 월별 폴더 생성
                        }
                        String fileName = folderPath + "/" + strDay + "일.txt";
                        String log = "";
                        int sum = 0;
                        while (sum < secRand.nextInt(40000, 60000)) {
                            int index = drinkRand.nextInt(6);
                            String strHour = String.format("%02d", hourRand.nextInt(24));
                            String strMin = String.format("%02d", minRand.nextInt(60));
                            String strSec = String.format("%02d", secRand.nextInt(60));
                            log = strYear + "-" + strMonth + "-" + strDay + " " + strHour + ":" + strMin + ":" + strSec + " " + DrinkList.drinks.get(index).getName() + " " + DrinkList.drinks.get(index).getPrice();
                            logList.add(log);
                            sum += DrinkList.drinks.get(index).getPrice();
                        }
                        Collections.sort(logList);
                        for (String logStr : logList) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                                writer.write(logStr + "\n");  //파일을 생성하고 판매 로그 추가
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        logList.clear();
                    }
                }
            }

        } finally {

        }
    }

    public static void main(String[] args) {
        ReportGenerator reportGenerator = new ReportGenerator();
    }
}
