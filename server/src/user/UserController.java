package user;

import coin.Coin;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    User user;
    final private HashMap<String, Integer> hasCoinMap = new HashMap<>();

    //getCoin 코인을 buyCoinCount 만큼 구매할 수 있는지 확인해서 true/false return 해주는 메서드
    public boolean checkCanBuy(Coin getCoin, int buyCoinCount){
        return getCoin.getAmount() >= buyCoinCount && (getCoin.getPrice() * buyCoinCount) <= user.getMoney();
    }

    UserController(User user){
        this.user = user;
    }

    public HashMap<String, Integer> getHasCoinMap() {
        return hasCoinMap;
    }

    public String getUserCoinToString(String getCoinName){
        String returnString = "해당 코인을 보유하고 있지 않습니다.";
        StringBuilder sb = new StringBuilder();
        if (hasCoinMap.containsKey(getCoinName)){
            sb.append("코인 이름 : ");
            sb.append(getCoinName);
            sb.append(" 보유 개수 : ");
            sb.append(hasCoinMap.get(getCoinName).intValue());
            returnString = sb.toString();
        }
        return returnString;
    }

    public String getUserCoinListToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName());
        sb.append("님이 가지고 계신 코인 목록은 다음과 같습니다. ");
        if (!hasCoinMap.isEmpty()){
            for (Map.Entry<String, Integer> entry : hasCoinMap.entrySet()){
                sb.append(getUserCoinToString(entry.getKey()));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    //getCoin 코인을 buyCoinCount 만큼 구매하는 메서드
    public void buyCoin(Coin getCoin, int buyCoinCount){
        String getCoinName = getCoin.getName();
        getCoin.setAmount(getCoin.getAmount() - buyCoinCount);
        user.setMoney(user.getMoney() - (getCoin.getPrice() * buyCoinCount));
        if (!hasCoinMap.containsKey(getCoinName)){
            hasCoinMap.put(getCoinName, buyCoinCount);
        }
        else{
            hasCoinMap.replace(getCoinName, hasCoinMap.get(getCoinName) + buyCoinCount);
        }
    }

    //getCoin 코인을 sellCoinCount 만큼 판매할 수 있는지 확인해서 true/false return 해주는 메서드
    public boolean checkCanSell(Coin getCoin, int sellCoinCount){
        String getCoinName = getCoin.getName();
        boolean checkBoolean = false;

        if (hasCoinMap.containsKey(getCoinName)){
            if (hasCoinMap.get(getCoinName) >= sellCoinCount){
                checkBoolean = true;
            }
        }
        return checkBoolean;
    }

    //getCoin 코인을 sellCoinCount 만큼 판매하는 메서드
    public void sellCoin(Coin getCoin, int sellCoinCount){
        String getCoinName = getCoin.getName();

        getCoin.setAmount(getCoin.getAmount() + sellCoinCount);
        user.setMoney(user.getMoney() + (getCoin.getPrice() * sellCoinCount));
        hasCoinMap.replace(getCoinName, hasCoinMap.get(getCoinName) - sellCoinCount);
    }
}
