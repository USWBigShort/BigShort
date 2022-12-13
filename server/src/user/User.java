package user;

import coin.Coin;
import coin.CoinController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

//스레드 생성은 main쪽에서 new Thread(new User(socket)).start() 해주면 됨
public class User implements Runnable{
    //유저를 구분하기 위한 유저명
    private String name;
    //유저가 보유중인 돈
    private int money;
    //CoinController 객체로 CoinController 메서드를 사용해야해서 생성해둔 객체
    private final CoinController coinController = new CoinController();
    //유저가 보유중인 코인의 리스트<코인명, 가진개수>
    private HashMap<String, Integer> hasCoinMap;
    //소켓
    private final Socket clientSocket;

    public User(String name, HashMap<String, Integer> hasCoinMap, Socket clientSocket) {
        this.name = name;
        this.hasCoinMap = hasCoinMap;
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public HashMap<String, Integer> getHasCoinMap() {
        return hasCoinMap;
    }

    @Override
    public void run() {
        System.out.println("유저 스레드 시작");
        //입력값이 "매수/매도 코인명 개수"의 형식일때 각각의 값을 떼오는 용도로 사용한 StringTokenizer
        StringTokenizer stringTokenizer;
        UserController userController = new UserController(this);
        try(BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            while (true){
                stringTokenizer = new StringTokenizer(socketReader.readLine());
                //매수인지 매도인지 확인
                String checkSellOrBuy = stringTokenizer.nextToken();
                //구매/판매하는 코인 명
                String coinName = stringTokenizer.nextToken();
                //구매/판매하는 코인 개수
                int coinCount = Integer.parseInt(stringTokenizer.nextToken());

                //이벤트 처리 후 클라이언트에 보내줄 출력 메세지
                String sendMessage;
                //코인이 존재하지 않는다면 이후 메서드들에서 오류가 생길 수 있으므로 체크
                if (coinController.isCoin(coinName)){
                    //코인 객체를 findCoin 으로 찾아와서 이후 메서드들에 매개변수로 넘겨줌
                    Coin coin = coinController.findCoin(coinName);
                    if (checkSellOrBuy.equals("매수")){
                        if (userController.checkCanBuy(coin, coinCount)){
                            userController.buyCoin(coin, coinCount);
                            sendMessage = "구매에 성공했습니다.";
                        }
                        else{
                            sendMessage = "구매할 수 없습니다.";
                        }
                    }
                    else if (checkSellOrBuy.equals("매도")){
                        if (userController.checkCanSell(coin, coinCount)){
                            userController.sellCoin(coin, coinCount);
                            sendMessage = "판매에 성공했습니다.";
                        }
                        else{
                            sendMessage = "판매할 수 없습니다.";
                        }
                    }
                    else{
                        sendMessage = "잘못된 입력값입니다.";
                    }
                }
                else{
                    sendMessage = "해당 코인은 존재하지 않습니다.";
                }

                try (PrintStream socketWrite = new PrintStream(clientSocket.getOutputStream())){
                    socketWrite.println(sendMessage);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
