import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Runnable buyMode = new Runnable() {
            @Override
            public void run() {
                try {
                    BuyFrame buyFrame = new BuyFrame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread buyThread = new Thread(buyMode);
        buyThread.start();
    }
}
