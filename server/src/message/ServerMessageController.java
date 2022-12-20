package message;

import coin.CoinController;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ServerMessageController implements Runnable {
    Scanner sc = new Scanner(System.in);
    CoinController coinController;
    StringTokenizer input;

    public DatagramSocket datagramSocket = new DatagramSocket();
    public DatagramPacket datagramPacket;
    public InetAddress groupAddress = InetAddress.getByName("224.0.0.1");
    public int port = 7000;

    public ServerMessageController(CoinController coinController) throws SocketException, UnknownHostException {
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

                    coinController.makeCoin(coinName, coinAmount, coinPrice, coinRateOfChange);
                    System.out.println(coinName + "코인이 생성되었습니다.");
                    System.out.println();
                    //생성될때 공지사항 기능 추가.
                    String message = coinName + "코인이 생성되었습니다.";
                    byte[] buffer = message.getBytes();
                    datagramPacket = new DatagramPacket(buffer,buffer.length,groupAddress,7000);
                    try {
                        datagramSocket.send(datagramPacket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
                case "코인삭제":
                    String name;
                    System.out.print("삭제하려는 코인의 이름을 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    name = input.nextToken();

                    coinController.removeCoin(name);
                    System.out.println(name + "코인이 삭제되었습니다.");
                    System.out.println();
                    break;
                case "코인 리스트":
                    System.out.println("전체 코인리스트입니다.");
                    coinController.printAllCoin();
                    break;
                case "랜덤변동률": {
                    String coinName;
                    int changeRateRange;

                    System.out.print("변동시킬 코인 이름과 변동률의 범위를 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    coinName = input.nextToken();
                    changeRateRange = Integer.parseInt(input.nextToken());

                    coinController.changeCoinRateByRandom(coinName, changeRateRange);
                    System.out.println(coinName + " 코인의 변동률이 변동되었습니다.");
                    System.out.println();
                    break;
                }
                case "랜덤가격": {
                    String coinName;
                    int changeRateRange;

                    System.out.print("변동시킬 코인 이름과 가격의 범위를 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    coinName = input.nextToken();
                    changeRateRange = Integer.parseInt(input.nextToken());

                    coinController.changeCoinPriceByRandom(coinName, changeRateRange);
                    System.out.println(coinName + " 코인의 가격이 변동되었습니다.");
                    System.out.println();
                    break;
                }
                case "랜덤수량": {
                    String coinName;
                    int changeRateRange;

                    System.out.print("변동시킬 코인 이름과 수량의 범위를 입력해주세요: ");
                    input = new StringTokenizer(sc.nextLine());

                    coinName = input.nextToken();
                    changeRateRange = Integer.parseInt(input.nextToken());

                    coinController.changeCoinAmountByRandom(coinName, changeRateRange);
                    System.out.println(coinName + " 코인의 수량이 변동되었습니다.");
                    System.out.println();
                    break;
                }
                case "리스트":
                    System.out.println("서버 메세지로는 \"코인생성, 코인삭제, 코인 리스트, 랜덤변동률, 랜덤가격, 랜덤수량, 리스트, 나가기\"가 있습니다. ");
                    break;
                case "나가기":
                    break label;
                default:
                    System.out.println("잘못된 메세지입니다. 리스트를 참조해주십시오!");
                    break;
            }
        }
    }
}