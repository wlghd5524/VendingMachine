package adminPage;

import adminPage.util.BackButtonGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import drink.*;

//음료 정보 수정 화면 패널 생성 클래스
public class ModifyDrinkPanel extends JPanel {
    Font textFont = new Font("Arial", Font.BOLD, 35);
    JFileChooser fileChooser;
    File selectedFile;

    ModifyDrinkPanel() {
        setBackground(new Color(252, 255, 216));
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        //뒤로 가기 버튼
        JButton backButton = BackButtonGenerator.createBackButton(this);
        add(backButton);


        //음료 이미지 불러오기 및 설정
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


        //음료 정보 수정을 위한 입력칸 설정
        JTextField[][] drinkTextField = new JTextField[DrinkList.drinks.size()][2];
        for (int i = 0; i < drinkTextField.length; i++) {
            for (int j = 0; j < drinkTextField[i].length; j++) {
                drinkTextField[i][j] = new JTextField();
                drinkTextField[i][j].setFont(textFont);
                if (i < 3) {
                    drinkTextField[i][j].setBounds(150 + (i * 300), 40 + (j * 75), 150, 50);
                } else {
                    drinkTextField[i][j].setBounds(150 + ((i % 3) * 300), 300 + (j * 75), 150, 50);
                }
                add(drinkTextField[i][j]);
            }
            drinkTextField[i][0].setText(DrinkList.drinks.get(i).getName());
            drinkTextField[i][1].setText(String.valueOf(DrinkList.drinks.get(i).getPrice()));
        }


        //사진 변경을 위한 업로드 버튼 설정
        JButton[] imageChangeButton = new JButton[DrinkList.drinks.size()];
        for (int i = 0; i < DrinkList.drinks.size(); i++) {
            imageChangeButton[i] = new JButton("사진 변경");
            imageChangeButton[i].setFont(textFont);
            if (i < 3) {
                imageChangeButton[i].setBounds(150 + (i * 300), 190, 150, 50);
            } else {
                imageChangeButton[i].setBounds(150 + (i % 3) * 300, 450, 150, 50);
            }
            add(imageChangeButton[i]);

            //업로드 버튼을 눌렀을 때 이벤트 설정(업로드된 사진을 로컬에 복사하고 이미지 변경)
            int finalI = i;
            imageChangeButton[i].addActionListener(e -> {
                fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    drinkImageLabel[finalI].setIcon(new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath()).getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH)));
                    try {
                        uploadFile(finalI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }


        //저장 버튼 설정
        JButton saveButton = new JButton("저장");
        saveButton.setBounds(725, 515, 150, 50);
        saveButton.setFont(textFont);
        add(saveButton);
        //저장 버튼을 눌렀을 때 이벤트 설정(음료 리스트에 있는 음료 이름과 가격 변경)
        saveButton.addActionListener(e -> {
            for (int i = 0; i < DrinkList.drinks.size(); i++) {
                DrinkList.drinks.get(i).setName(drinkTextField[i][0].getText());
                DrinkList.drinks.get(i).setPrice(Integer.parseInt(drinkTextField[i][1].getText()));
            }
            JOptionPane.showMessageDialog(null,"음료 정보가 저장되었습니다.");
        });


    }

    //업로드 된 사진을 로컬에 저장하고 음료 리스트에 있는 파일 경로 변경
    private void uploadFile(int index) throws IOException {
        if (selectedFile != null) {
            File sourceFile = selectedFile;
            File destFile = new File("image/" + sourceFile.getName());
            destFile.getParentFile().mkdirs();
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            DrinkList.drinks.get(index).setImagePath(String.valueOf(destFile));
            DrinkList.drinks.get(index).setStock(0);
        } else {
            JOptionPane.showMessageDialog(this, "사진 업로드 중 문제가 발생하였습니다.");
        }
    }
}
