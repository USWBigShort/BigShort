package coin;

import java.io.Serializable;

public class Coin implements Serializable {

    // 코인 정보 : 코인이름, 수량, 가격, 변경되는 퍼센트
    private String name;
    private int amount;
    private int price;
    private double rateOfChange; // rate of change

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

    public void randomRoc(){
        this.rateOfChange = this.rateOfChange *(Math.random()/1 + Math.random());
    }

}