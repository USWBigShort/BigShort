package user;

import coin.CoinController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class makeUserThread implements Runnable{
    //private static final
    private int userCountNumber = 0;

    private final ServerSocket serverSocket;
    private final CoinController coinController;

    public makeUserThread(ServerSocket serverSocket, CoinController coinController) {
        this.serverSocket = serverSocket;
        this.coinController = coinController;
    }

    @Override
    public void run() {
        try{
            while (true){
                System.out.println("Listening for a client connection");
                Socket socket = serverSocket.accept();
                System.out.println(socket);
                System.out.println("Connected to a Client");
                User user = new User(""+userCountNumber++, socket);
                new Thread(new UserThread(new UserController(new UserCoinController(user), user), coinController, user)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
