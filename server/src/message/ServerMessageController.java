package message;

import coin.Coin;
import coin.CoinController;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ServerMessageController implements Runnable {
    Scanner sc = new Scanner(System.in);
    CoinController coinController;
    StringTokenizer input;

    public ServerMessageController(CoinController coinController) {
        this.coinController = coinController;
    }

    @Override
    public void run() {

        System.out.println("서버 메세지 컨트롤러 생성");
        System.out.println("메세지 입력에 대해 궁금하시면 리스트를 입력해주세요.");

        label:
        while (true) {
            String str = sc.nextLine();

            switch (str) {
                case "코인생성": {
                    String coinName;
                    int coinAmount, coinPrice;
                    double coinRateOfChange;

                    System.out.print("생성하려는 코인의 이름, 수량, 가격, 변동률을 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    coinName = input.nextToken();
                    coinAmount = Integer.parseInt(input.nextToken());
                    coinPrice = Integer.parseInt(input.nextToken());
                    coinRateOfChange = Double.parseDouble(input.nextToken());

                    new Thread (() -> {
                        try {
                            coinController.makeCoin(coinName, coinAmount, coinPrice, coinRateOfChange);
                            while (true) {
                                coinController.changeCoinPriceByRandom(coinName, 10);
                                Thread.sleep(1000);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    System.out.println(coinName + "코인이 생성되었습니다.");
                    System.out.println();

                    break;
                }
                case "코인삭제":{
                    String name;
                    System.out.print("삭제하려는 코인의 이름을 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    name = input.nextToken();

                    try {
                        coinController.removeCoin(name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + "코인이 삭제되었습니다.");
                    System.out.println();
                    break;
                }
                case "코인 리스트":
                    System.out.println("전체 코인리스트입니다.");
                    coinController.printAllCoin();
                    break;
                case "랜덤가격": {
                    String coinName;
                    int changeRateRange;

                    System.out.print("변동시킬 코인 이름과 가격의 범위를 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    coinName = input.nextToken();
                    changeRateRange = Integer.parseInt(input.nextToken());

                    try {
                        coinController.changeCoinPriceByRandom(coinName, changeRateRange);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(coinName + " 코인의 가격이 변동되었습니다.");
                    System.out.println();
                    break;
                }
                case "공지 생성":{
                    String name, announcement;
                    System.out.print("공지를 추가하려는 코인의 이름을 입력해주세요: ");
                    name = sc.nextLine();
                    System.out.println("추가하려는 공지 내용을 입력해주세요.");
                    System.out.print("> ");
                    announcement = sc.nextLine();

                    coinController.setNotice(name, announcement);
                    break;
                }
                case "공지 출력":{
                    String name;
                    System.out.print("공지를 보려는 코인의 이름을 입력해주세요: ");
                    name = sc.nextLine();

                    coinController.printNotice(name);
                    break;
                }

                case "리스트":
                    System.out.println("서버 메세지로는 \"코인생성, 코인삭제, 코인 리스트, 랜덤가격, 리스트, 나가기\"가 있습니다. ");
                    break;
                case "나가기":
                    System.exit(0);
                    break label;
                default:
                    System.out.println("잘못된 메세지입니다. 리스트를 참조해주십시오!");
                    break;
            }
        }
    }
}