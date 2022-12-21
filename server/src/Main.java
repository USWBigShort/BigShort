import coin.CoinController;
import message.ServerMessageController;
import user.makeUserThread;
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.ServerSocket;
public class Main {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8888);
             MulticastSocket multicastSocket = new MulticastSocket()) {
            CoinController cmg = new CoinController(multicastSocket);
            new Thread(new makeUserThread(serverSocket, cmg)).start();
            new Thread(new ServerMessageController(cmg)).start();

            while(true){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}