import coin.CoinController;
import message.ServerMessageController;
import user.makeUserThread;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        CoinController coinController = new CoinController();
        ServerMessageController serverMessageController = new ServerMessageController(coinController);


        try(ServerSocket serverSocket = new ServerSocket(8888)){
            new Thread(new makeUserThread(serverSocket)).start();
            serverMessageController.run();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}