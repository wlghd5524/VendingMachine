import javax.swing.*;
import java.util.concurrent.ConcurrentHashMap;

public class Frame extends JFrame {
    public Frame() {
        setTitle("Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setVisible(true);
    }
    public static void main(String [] args) {
        Frame frame = new Frame();
    }
}
