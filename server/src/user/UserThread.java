package user;

import coin.Coin;
import coin.CoinController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class UserThread implements Runnable{
    final private User user;
    UserThread(User user){
        this.user = user;
    }
    @Override
    public void run() {
        System.out.println("유저 스레드 시작");
        //입력값이 "매수/매도 코인명 개수"의 형식일때 각각의 값을 떼오는 용도로 사용한 StringTokenizer
        StringTokenizer stringTokenizer;
        UserController userController = new UserController(user);
        CoinController coinController = new CoinController();
        try(BufferedReader socketReader = new BufferedReader(new InputStreamReader(user.getClientSocket().getInputStream()))){
            while (true){
                stringTokenizer = new StringTokenizer(socketReader.readLine());
                //매수인지 매도인지 확인
                String checkSellOrBuy = stringTokenizer.nextToken();
                //구매/판매하는 코인 명
                String coinName = stringTokenizer.nextToken();
                //구매/판매하는 코인 개수
                int coinCount = Integer.parseInt(stringTokenizer.nextToken());

                //이벤트 처리 후 클라이언트에 보내줄 출력 메세지, 특정 조건을 만족하지 못하면 디폴트 메세지로 전송
                String sendMessage = "잘못된 입력값입니다.";
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
                }

                try (PrintStream socketWrite = new PrintStream(user.getClientSocket().getOutputStream())){
                    socketWrite.println(sendMessage);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
