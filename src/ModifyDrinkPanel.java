import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
            int finalI = i;

            imageChangeButton[i].addActionListener(e -> {
                fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image originalImage = imageIcon.getImage();
                    Image resizedImage = originalImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
                    drinkImageLabel[finalI].setIcon(new ImageIcon(resizedImage));
                    try {
                        uploadFile(finalI);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }


        JButton saveButton = new JButton("저장");
        saveButton.setBounds(725, 515, 150, 50);
        saveButton.setFont(textFont);
        add(saveButton);
        saveButton.addActionListener(e -> {
            for (int i = 0; i < DrinkList.drinks.size(); i++) {
                DrinkList.drinks.get(i).setName(drinkTextField[i][0].getText());
                DrinkList.drinks.get(i).setPrice(Integer.parseInt(drinkTextField[i][1].getText()));
            }
        });


    }

    private void uploadFile(int index) throws IOException {
        if (selectedFile != null) {
            File sourceFile = selectedFile;
            File destFile = new File("image/" + sourceFile.getName());
            // Ensure the destination directory exists
            destFile.getParentFile().mkdirs();
            // Copy file to the destination directory
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            //JOptionPane.showMessageDialog(this, "File uploaded: " + destFile.getAbsolutePath());
            DrinkList.drinks.get(index).setImagePath(String.valueOf(destFile));
            DrinkList.drinks.get(index).setStock(0);
        } else {
            JOptionPane.showMessageDialog(this, "No file selected");
        }
    }
}
