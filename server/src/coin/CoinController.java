package coin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CoinController {
    public Set<Coin> coinSet = new HashSet<>();
    Iterator<Coin> iterator;

    public void makeCoin(String name, int amount, int price, double roc) {
        Coin coin = new Coin(name, amount, price, roc);
        coinSet.add(coin);
    }

    public boolean isCoin(String coinName) {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(coinName)) {
                return true;
            }
        }
        return false;
    }

    public Coin findCoin(String coinName) {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            Coin tmp = iterator.next();
            if (tmp.getName().equals(coinName)) {
                return tmp;
            }
        }
        return null;
    }

    public void printCoin(String name) {
        iterator = coinSet.iterator();

        if (isCoin(name)) {
            Coin tmp = findCoin(name);
            System.out.println("[코인 이름: " + tmp.getName() +
                    " 가격: " + tmp.getPrice() +
                    " 남은 수량: " + tmp.getAmount() +
                    " 변동률: " + tmp.getRoc());
        }
    }

    public void printAllCoin() {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            Coin tmp = iterator.next();
            System.out.println("[코인 이름: " + tmp.getName() +
                    " 가격: " + tmp.getPrice() +
                    " 남은 수량: " + tmp.getAmount() +
                    " 변동률: " + tmp.getRoc());
        }
    }

    public void removeCoin(String coinName) {
        iterator = coinSet.iterator();

        Coin rmCoin = findCoin(coinName);
        coinSet.remove(rmCoin);
    }


}
