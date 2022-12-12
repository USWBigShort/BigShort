import coin.CoinController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CoinController cmg = new CoinController();
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /*cmg.makeCoin("루나", 7000, 3, 5);
        cmg.makeCoin("도지", 6000, 20, 8);
        cmg.makeCoin("이더", 5000, 500, 1);
        cmg.makeCoin("비트", 3000, 2000, 2);
        cmg.makeCoin("샘", 4000, 60, 3);*/
        while (true) {
            System.out.println("코인을 생성하려면 make Coin을 입력해주세요");
            String str = sc.nextLine();
            if (str.equals("make Coin")) {
                String name; int amount, price; double roc;
                System.out.print("생성하려는 코인의 이름을 입력해주세요: ");
                name = sc.next();
                sc.nextLine();
                System.out.print("생성하려는 코인의 수량을 입력해주세요: ");
                amount = sc.nextInt();
                sc.nextLine();
                System.out.print("생성하려는 코인의 가격을 입력해주세요: ");
                price = sc.nextInt();
                sc.nextLine();
                System.out.print("생성하려는 코인의 변동률을 입력해주세요: ");
                roc = sc.nextDouble();
                sc.nextLine();

                cmg.makeCoin(name, amount, price, roc);
                System.out.println("코인이 생성되었습니다.");
                System.out.println();

            } else if (str.equals("exit")) {
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

    }
}