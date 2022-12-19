package coin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CoinController {
    public Set<Coin> coinSet = new HashSet<>();
    Iterator<Coin> iterator;

    public void makeCoin(String name, int amount, int price, double rateOfChange) {
        Coin coin = new Coin(name, amount, price, rateOfChange);
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

    public String stringPrintCoin(String name) {
        iterator = coinSet.iterator();

        if (isCoin(name)) {
            Coin tmp = findCoin(name);
            return "[코인 이름: " + tmp.getName() +
                    " 가격: " + tmp.getPrice() +
                    " 남은 수량: " + tmp.getAmount() +
                    " 변동률: " + tmp.getRateOfChange();
        }
        return "입력하신 코인이 없습니다.";
    }

    public void printCoin(String name) {
        iterator = coinSet.iterator();

        if (isCoin(name)) {
            Coin tmp = findCoin(name);
            System.out.println("[코인 이름: " + tmp.getName() +
                    " 가격: " + tmp.getPrice() +
                    " 남은 수량: " + tmp.getAmount() +
                    " 변동률: " + tmp.getRateOfChange());
        }
    }

    public void printAllCoin() {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            Coin tmp = iterator.next();
            System.out.println("[코인 이름: " + tmp.getName() +
                    " 가격: " + tmp.getPrice() +
                    " 남은 수량: " + tmp.getAmount() +
                    " 변동률: " + tmp.getRateOfChange());
        }
    }

    public void removeCoin(String coinName) {
        iterator = coinSet.iterator();

        Coin removeCoin = findCoin(coinName);
        coinSet.remove(removeCoin);
    }

     /*
        변동률은 = 이전가격 - 신가격 / 100
        신가격 = 변동률 * 100 + 이전가격
        신수량 = 이전수량 - 변동률*이전수량
        변동률 = (이전수량 - 신수량) / 이전수량
    */

    // 랜덤 변동률 메소드
    public void changeCoinRateByRandom(String coinName, int changeRateRange) {
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            // 총 범위 = 2 * 입력 범위 * 랜덤함수 - 입력범위
            int range = 2 * changeRateRange + 1;
            double rateOfChange = (int)(Math.random() * range - changeRateRange) / 100.0;
            // 랜덤 변동률 변경
            tmp.setRateOfChange(rateOfChange);

            // 변동률에 따라서 가격변경
            tmp.setPrice( (int)(rateOfChange * 100) + tmp.getPrice() );

            // 변동률에 따라 수량변경 (가격이 내려갈때는 매도수량이 많아지는 것이므로)
            tmp.setAmount( tmp.getAmount() - (int)(rateOfChange * tmp.getAmount()) );

        }
    }

    public void changeCoinPriceByRandom(String coinName, int changePriceRange) {
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            // 총 범위 = 2 * 입력 범위 * 랜덤함수 - 입력범위
            int range = 2 * changePriceRange + 1;
            int priceOfChange = (int) (Math.random() * range - changePriceRange);
            int previousPrice = tmp.getPrice();
            // 랜덤 가격 변경
            tmp.setPrice( tmp.getPrice() + priceOfChange );

            // 가격변경에 따라서 변동률 변경
            tmp.setRateOfChange( (previousPrice - tmp.getPrice()) / 100.0 );

            // 가격변경에 따라서 수량 변경
            tmp.setAmount( tmp.getAmount() - (int)(tmp.getRateOfChange() * tmp.getAmount()) );

        }
    }

    public void changeCoinAmountByRandom(String coinName, int changeAmountRange) {
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            // 총 범위 = 2 * 입력 범위 * 랜덤함수 - 입력범위
            int range = 2 * changeAmountRange + 1;
            int amountOfChange = (int) (Math.random() * range - changeAmountRange);
            int amount = tmp.getAmount();
            int previousPrice = tmp.getPrice();

            // 랜덤 수량 변경
            tmp.setAmount( tmp.getAmount() + amountOfChange );

            // 수량변경에 따라서 가격 변경
            tmp.setPrice( (amount - tmp.getAmount()) / amount * 100+ tmp.getPrice() );

            // 수량변경에 따라서 변동률 변경
            tmp.setRateOfChange( (previousPrice - tmp.getPrice()) / 100.0 );

        }
    }

}
