import coin.CoinController;
import message.ServerMessageController;
import user.makeUserThread;
import java.io.IOException;
import java.net.ServerSocket;
public class Main {
    public static void main(String[] args) {
        CoinController cmg = new CoinController();
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            new Thread(new makeUserThread(serverSocket, cmg)).start();
            new Thread(new ServerMessageController(cmg)).start();
            while(true){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}