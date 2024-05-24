import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Runnable adminMode = new Runnable() {
            @Override
            public void run() {
                adminFrame adminFrame = new adminFrame();
            }
        };

        Runnable buyMode = new Runnable() {
            @Override
            public void run() {
                try {
                    Frame frame = new Frame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread buyThread = new Thread(buyMode);
        Thread adminThread = new Thread(adminMode);
        adminThread.start();
        buyThread.start();
    }
}
