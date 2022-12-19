import coin.CoinController;
import message.ServerMessageController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        CoinController coinController = new CoinController();
        ServerMessageController serverMessageController = new ServerMessageController(coinController);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        serverMessageController.run();


    }
}