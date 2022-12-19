import coin.CoinController;
import user.makeUserThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        CoinController cmg = new CoinController();
        //Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /*cmg.makeCoin("루나", 7000, 3, 5);
        cmg.makeCoin("도지", 6000, 20, 8);
        cmg.makeCoin("이더", 5000, 500, 1);
        cmg.makeCoin("비트", 3000, 2000, 2);
        cmg.makeCoin("샘", 4000, 60, 3);*/
        try(ServerSocket serverSocket = new ServerSocket(8888)){
            new Thread(new makeUserThread(serverSocket, cmg)).start();
            while (true) {
                System.out.println("코인을 생성하려면 make를 입력해주세요");
                String str = br.readLine();
                if (str.equals("make")) {
                    String name; int amount, price; double rateOfChange;

                    System.out.print("생성하려는 코인의 이름, 수량, 가격, 변동률을 입력해주세요: ");
                    StringTokenizer input = new StringTokenizer(br.readLine());

                    name = input.nextToken();
                    amount = Integer.parseInt(input.nextToken());
                    price = Integer.parseInt(input.nextToken());
                    rateOfChange = Double.parseDouble(input.nextToken());

                    cmg.makeCoin(name, amount, price, rateOfChange);
                    System.out.println("코인이 생성되었습니다.");
                    System.out.println();

                }else if (str.equals("exit")) {
                    break;
                }
            }
            System.out.println("전체 출력");
            cmg.printAllCoin();
            System.out.println();

            System.out.println("루나만 출력");
            cmg.printCoin("루나");

            System.out.println("도지 삭제");
            cmg.removeCoin("도지");
            cmg.printAllCoin();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}