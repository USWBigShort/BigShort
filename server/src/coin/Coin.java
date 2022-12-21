package coin;

import java.io.Serializable;
import java.util.ArrayList;

public class Coin implements Serializable {

    // 코인 정보 : 코인이름, 수량, 가격, 변동률
    private String name;
    private int amount;
    private int price;
    private double rateOfChange; // 변동률은 퍼센트로 1보다 작거나 같다.

    private ArrayList<String> notice = new ArrayList<>();

    public ArrayList<String> getNotice() {
        return notice;
    }

    public Coin() {
        this.name = null;
        this.amount = 0;
        this.price = 0;
        this.rateOfChange = 0;
    }

    public Coin(String name, int amount, int price, double rateOfChange) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.rateOfChange = rateOfChange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getRateOfChange() {
        return rateOfChange;
    }

    public void setRateOfChange(double rateOfChange) {
        this.rateOfChange = rateOfChange;
    }



}