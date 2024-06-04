package buyPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import drink.*;
import money.*;

public class BuyFrame extends JFrame {

    public BuyFrame() throws IOException {
        setTitle("Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             //창을 닫으면 프로그램 종료 설정
        setResizable(false);                                        //창 크기 변경 불가
        setSize(1100, 1100);                           //창 크기 설정
        getContentPane().setBackground(new Color(252, 255, 216));   //백그라운드 색 변경
        setLayout(null);
        //음료 정보 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Drinks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");        //공백 문자를 기준으로 음료수 정보를 나눠서 temp에 저장. temp[0]:음료수 이름 temp[1]:가격 temp[2]:재고 temp[3]:이미지 경로
                DrinkList.drinks.add(new Drink(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]));  //생성자로 음료수 정보 초기화하고 Linked-list에 추가
            }
        } catch (IOException e) {       //음료수 파일 불러오기 실패 예외 처리
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "음료수 정보를 파일에서 불러올 수 없습니다. 프로그램을 종료합니다.");
            System.exit(0);
        }

        //거스름돈 정보 불러오기
        try (BufferedReader br = new BufferedReader(new FileReader("Money.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");      //공백 문자를 기준으로 거스름돈 정보를 나눠서 temp에 저장. temp[0]:이름 temp[1]:가격 temp[2]:재고
                MoneyList.moneyList.add(new Money(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));      //생성자로 돈 정보 초기화하고 Linked-list에 추가
            }
        } catch (IOException e) {       //거스름돈 파일 불러오기 실패 예외 처리
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "거스름돈 정보를 파일에서 불러올 수 없습니다. 프로그램을 종료합니다.");
            System.exit(0);
        }

        //판매 페이지 패널
        JPanel buyPanel = new BuyPanel();
        add(buyPanel);


        // 프로그램이 종료될 때 이벤트 설정
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //파일에 음료 정보 최신화
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Drinks.txt"))) {
                    for (int i = 0; i < DrinkList.drinks.size(); i++) {
                        writer.write(DrinkList.drinks.get(i).getName() + " ");
                        writer.write(DrinkList.drinks.get(i).getPrice() + " ");
                        writer.write(DrinkList.drinks.get(i).getStock() + " ");
                        if (i == DrinkList.drinks.size() - 1) {
                            writer.write(DrinkList.drinks.get(i).getImagePath());
                        } else {
                            writer.write(DrinkList.drinks.get(i).getImagePath() + "\n");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //거스름돈 정보 최신화
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Money.txt"))) {
                    for (int i = 0; i < MoneyList.moneyList.size(); i++) {
                        writer.write(MoneyList.moneyList.get(i).getName() + " ");
                        writer.write(MoneyList.moneyList.get(i).getPrice() + " ");
                        if (i == MoneyList.moneyList.size() - 1) {
                            writer.write(MoneyList.moneyList.get(i).getStock() + "");
                        } else {
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


}