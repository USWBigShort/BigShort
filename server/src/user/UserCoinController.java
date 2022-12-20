package user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserCoinController {
    private final User user;
    public Set<UserCoin> coinSet = new HashSet<>();
    Iterator<UserCoin> iterator;

    public UserCoinController(User user) {
        this.user = user;
    }


    public boolean isCoin(String coinName) {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getCoinName().equals(coinName)) {
                return true;
            }
        }
        return false;
    }

    public UserCoin findCoin(String coinName) {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            UserCoin tmp = iterator.next();
            if (tmp.getCoinName().equals(coinName)) {
                return tmp;
            }
        }
        return null;
    }

    public void printCoin(String name) {
        iterator = coinSet.iterator();

        if (isCoin(name)) {
            UserCoin tmp = findCoin(name);
            System.out.println("[ 코인 이름: " + tmp.getCoinName() +
                    " 보유수량: " + tmp.getAmount() +
                    " 평균매수가: " + tmp.getAveragePurchasePrice() +
                    " 평가손익: " + tmp.getValuationGain_Loss() +
                    " 수익률: " + tmp.getRateOfReturn() +
                    " 총 매수금액: " + tmp.getTotalPurchaseAmount() + " ]");
        }
    }

    public String stringPrintCoin(String name) {
        iterator = coinSet.iterator();

        if (isCoin(name)) {
            UserCoin tmp = findCoin(name);
            return "HOLDING : " + "[ " + tmp.getCoinName() +
                    ", " + tmp.getAmount() +
                    ", " + tmp.getAveragePurchasePrice() +
                    ", " + tmp.getValuationGain_Loss() +
                    ", " + tmp.getRateOfReturn() +
                    ", " + tmp.getTotalPurchaseAmount() + " ] \n";
        }
        return "입력하신 코인이 없습니다.";
    }

    public void printAllCoin() {
        iterator = coinSet.iterator();

        while (iterator.hasNext()) {
            UserCoin tmp = iterator.next();
            System.out.println("[ 코인 이름: " + tmp.getCoinName() +
                    " 보유수량: " + tmp.getAmount() +
                    " 평균매수가: " + tmp.getAveragePurchasePrice() +
                    " 평가손익: " + tmp.getValuationGain_Loss() +
                    " 수익률: " + tmp.getRateOfReturn() +
                    " 총 매수금액: " + tmp.getTotalPurchaseAmount() + " ]");
        }
    }

    public String stringPrintAllCoin() {
        iterator = coinSet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            UserCoin tmp = iterator.next();
            sb.append("HOLDING : " + "[ " + tmp.getCoinName() +
                    ", " + tmp.getAmount() +
                    ", " + tmp.getAveragePurchasePrice() +
                    ", " + tmp.getValuationGain_Loss() +
                    ", " + tmp.getRateOfReturn() +
                    ", " + tmp.getTotalPurchaseAmount() + " ]\n");
        }
        return sb.toString();
    }
}