package coin;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class RandomCoin implements Runnable{
    public CoinController coinController;

    public RandomCoin(CoinController coinController) {
        this.coinController = coinController;
    }
    int i =1;
    @Override
    public void run() {
        while (true) {

            if(!coinController.coinSet.isEmpty()) {
                try {
                    System.out.println("RandomCoin클래스 시작");
                    Iterator<Coin> iterator = coinController.coinSet.iterator();
                    int randomRange = (int) (Math.random() * 51);
                    Coin tmp = iterator.next();
                    coinController.changeCoinPriceByRandom(tmp.getName() , randomRange);
                    System.out.println(tmp.getName() + "Price Change");
                    Thread.sleep(3000);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
