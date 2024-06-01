import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Runnable buyMode = new Runnable() {
            @Override
            public void run() {
                try {
                    BuyFrame buyFrame = new BuyFrame();  //판매 스레드를 실행하면 판매 페이지 띄우기
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        //판매 스레드 실행
        Thread buyThread = new Thread(buyMode);
        buyThread.start();
    }
}
