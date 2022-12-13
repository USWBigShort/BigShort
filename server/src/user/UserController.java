package user;

import coin.Coin;

import java.util.HashMap;

public class UserController {
    User user;

    //getCoin 코인을 buyCoinCount 만큼 구매할 수 있는지 확인해서 true/false return 해주는 메서드
    public boolean checkCanBuy(Coin getCoin, int buyCoinCount){
        return getCoin.getAmount() >= buyCoinCount && (getCoin.getPrice() * buyCoinCount) <= user.getMoney();
    }

    UserController(User user){
        this.user = user;
    }

    //getCoin 코인을 buyCoinCount 만큼 구매하는 메서드
    public void buyCoin(Coin getCoin, int buyCoinCount){
        String getCoinName = getCoin.getName();
        HashMap<String, Integer> hasCoinMap = user.getHasCoinMap();

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
        HashMap<String, Integer> hasCoinMap = user.getHasCoinMap();
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
        HashMap<String, Integer> hasCoinMap = user.getHasCoinMap();

        getCoin.setAmount(getCoin.getAmount() + sellCoinCount);
        user.setMoney(user.getMoney() + (getCoin.getPrice() * sellCoinCount));
        hasCoinMap.replace(getCoinName, hasCoinMap.get(getCoinName) - sellCoinCount);
    }
}
