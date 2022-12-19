package user;

import coin.Coin;

public class UserController {
    final private UserCoinController userCoinController;
    User user;

    public UserCoinController getUserCoinController() {
        return userCoinController;
    }

    UserController(UserCoinController userCoinController, User user){
        this.userCoinController = userCoinController;
        this.user = user;
    }
    //getCoin 코인을 buyCoinCount 만큼 구매할 수 있는지 확인해서 true/false return 해주는 메서드
    public boolean checkCanBuy(Coin getCoin, int buyCoinCount){
        return getCoin.getAmount() >= buyCoinCount && (getCoin.getPrice() * buyCoinCount) <= user.getMoney();
    }

    //getCoin 코인을 buyCoinCount 만큼 구매하는 메서드
    public void buyCoin(Coin getCoin, int buyCoinCount){
        String getCoinName = getCoin.getName();
        getCoin.setAmount(getCoin.getAmount() - buyCoinCount);
        user.setMoney(user.getMoney() - (getCoin.getPrice() * buyCoinCount));
        if (!userCoinController.isCoin(getCoinName)){
            userCoinController.coinSet.add(new UserCoin(getCoinName, buyCoinCount, getCoin.getPrice()));
        }
        else{
            userCoinController.findCoin(getCoinName).setAmount(userCoinController.findCoin(getCoinName).getAmount() + buyCoinCount);
        }
    }

    //getCoin 코인을 sellCoinCount 만큼 판매할 수 있는지 확인해서 true/false return 해주는 메서드
    public boolean checkCanSell(Coin getCoin, int sellCoinCount){
        String getCoinName = getCoin.getName();
        boolean checkBoolean = false;
        if (userCoinController.isCoin(getCoinName)){
            if (userCoinController.findCoin(getCoinName).getAmount() >= sellCoinCount){
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
        userCoinController.findCoin(getCoinName).setAmount(userCoinController.findCoin(getCoinName).getAmount() - sellCoinCount);
    }
}
