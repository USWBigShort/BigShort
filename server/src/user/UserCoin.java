package user;

public class UserCoin {
    // 가장 먼저 set해야하는건 수량과 평균매수가
    // 코인이름, 보유수량, 평균매수가, 평가손익, 수익률, 매수금액
    private String coinName;
    private int amount;
    // 평균 매수가
    private double averagePurchasePrice;
    // 평가손익 valuation gains and losses
    private int valuationGainLoss;
    // 수익률
    private double rateOfReturn;
    // 매수 금액
    private int totalPurchaseAmount;

    public UserCoin(String coinName, int amount, double averagePurchasePrice) {
        this.coinName = coinName;
        this.amount = amount;
        this.averagePurchasePrice = averagePurchasePrice;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getValuationGain_Loss() {
        return valuationGainLoss;
    }

    // 평가손익은
    public void setValuationGain_Loss(int changeCoinPrice) {
        this.valuationGainLoss = (int) ((changeCoinPrice - getAveragePurchasePrice()) * getAmount());
    }

    public double getRateOfReturn() {
        return rateOfReturn;
    }

    // 수익률은
    public void setRateOfReturn(int changeCoinPrice) {
        this.rateOfReturn = (changeCoinPrice - getAveragePurchasePrice()) / getAveragePurchasePrice();
    }

    public double getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public void setAveragePurchasePrice(int newBuyPrice, int buyAmount) {
        this.averagePurchasePrice = (getAveragePurchasePrice() * getAmount() + newBuyPrice * buyAmount) / (getAmount() + buyAmount);
    }

    public int getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount() {
        this.totalPurchaseAmount = (int) (getAmount()*getAveragePurchasePrice());
    }
}