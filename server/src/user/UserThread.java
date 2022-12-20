package user;

import coin.Coin;
import coin.CoinController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class UserThread implements Runnable{
    private final UserController userController;
    private final CoinController coinController;
    final private User user;
    public UserThread(UserController userController, CoinController coinController, User user){
        this.userController = userController;
        this.coinController = coinController;
        this.user = user;
    }
    @Override
    public void run() {
        //입력값이 "매수/매도 코인명 개수"의 형식일때 각각의 값을 떼오는 용도로 사용한 StringTokenizer
        StringTokenizer stringTokenizer;
        try(BufferedReader socketReader = new BufferedReader(new InputStreamReader(user.getClientSocket().getInputStream()));
            PrintStream socketWrite = new PrintStream(user.getClientSocket().getOutputStream())){
            socketWrite.println("접속하셨습니다.\n기본 비용 " + user.getMoney());
            socketWrite.println(coinController.stringPrintAllCoin());
            String sendMessage;
            String checkRequestType; String coinName; int coinCount;
            while (true){
                //이벤트 처리 후 클라이언트에 보내줄 출력 메세지, 특정 조건을 만족하지 못하면 디폴트 메세지로 전송
                String readString = socketReader.readLine();
                sendMessage = readString;
                stringTokenizer = new StringTokenizer(readString);
                try{
                    checkRequestType = stringTokenizer.nextToken();
                    //구매/판매하는 코인 명
                    if (checkRequestType.equals("보유코인")){
                        socketWrite.println("HOLDINGS : " + userController.getUserCoinController().stringPrintAllCoin());
                    }
                    else if (checkRequestType.equals("매수") || checkRequestType.equals("매도")) {
                        coinName = stringTokenizer.nextToken();
                        //구매/판매하는 코인 개수
                        coinCount = Integer.parseInt(stringTokenizer.nextToken());
                        //코인 객체를 findCoin 으로 찾아와서 이후 메서드들에 매개변수로 넘겨줌
                        Coin coin = coinController.findCoin(coinName);
                        if (checkRequestType.equals("매수")){
                            if (userController.checkCanBuy(coin, coinCount)){
                                userController.buyCoin(coin, coinCount);
                                sendMessage = "매수 성공!\n" + "가용 자산 : " + user.getMoney() + "\n" + "UPDATE:" + userController.getUserCoinController().stringPrintCoin(coinName);
                            }
                            else{
                                sendMessage = "구매할 수 없습니다.";
                            }
                        }
                        else {
                            if (userController.checkCanSell(coin, coinCount)){
                                userController.sellCoin(coin, coinCount);
                                sendMessage = "매도 성공!\n" + "가용 자산 : " + user.getMoney() + "\n" + "UPDATE:" + userController.getUserCoinController().stringPrintCoin(coinName);
                            }
                            else{
                                sendMessage = "판매할 수 없습니다.";
                            }
                        }
                    }
                    else{
                        if (coinController.isCoin(checkRequestType)){
                            socketWrite.println("HOLDING : " + userController.getUserCoinController().stringPrintCoin(checkRequestType));
                            continue;
                        }
                    }
                }catch(NoSuchElementException noSuchElementException){
                    noSuchElementException.printStackTrace();
                }catch(NumberFormatException numberFormatException){
                    numberFormatException.printStackTrace();
                }
                socketWrite.println(sendMessage);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
