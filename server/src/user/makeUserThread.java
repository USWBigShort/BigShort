package user;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class makeUserThread implements Runnable{
    //private static final
    private int userCountNumber = 0;

    private final ServerSocket serverSocket;

    public makeUserThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try{
            while (true){
                System.out.println("Listening for a client connection");
                Socket socket = serverSocket.accept();
                System.out.println(socket);
                System.out.println("Connected to a Client");
                new Thread(new UserThread(new User(""+userCountNumber++, new HashMap<>(), socket))).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
