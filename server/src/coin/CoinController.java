package coin;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class CoinController {
    public Set<Coin> coinSet = new HashSet<>();
    public Iterator<Coin> iterator;

    private MulticastSocket multicastSocket;
    public DatagramPacket sendPacket;
    InetAddress inetAddress = InetAddress.getByName("224.0.0.1");
    int port = 7777;

    public CoinController(MulticastSocket multicastSocket) throws IOException {
        this.multicastSocket = multicastSocket;
        multicastSocket.joinGroup(inetAddress);
    }

    public void sendMessageMulticast(Coin coin, String inputMessage) throws IOException {
        String Message = inputMessage + stringPrintCoin(coin.getName());
        byte[] sendMessage = Message.getBytes();
        sendPacket = new DatagramPacket(sendMessage, sendMessage.length, inetAddress, port);
        multicastSocket.send(sendPacket);
    }

    public void makeCoin(String name, int amount, int price, double rateOfChange) throws IOException {
        Coin coin = new Coin(name, amount, price, rateOfChange);
        coinSet.add(coin);
        sendMessageMulticast(coin, "ADD: ");
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
            return "[" + tmp.getName() +
                    ", " + tmp.getPrice() +
                    ", " + tmp.getAmount() +
                    ", " + tmp.getRateOfChange()+ "]";
        }
        return "입력하신 코인이 없습니다.";
    }

    public String stringPrintAllCoin() {
        iterator = coinSet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Coin tmp = iterator.next();
            sb.append("[" + tmp.getName() +
                    ", " + tmp.getPrice() +
                    ", " + tmp.getAmount() +
                    ", " + tmp.getRateOfChange()+ "] \n");
        }
        return sb.toString();
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

    public void removeCoin(String coinName) throws IOException {
        iterator = coinSet.iterator();

        Coin removeCoin = findCoin(coinName);
        sendMessageMulticast(removeCoin, "REMOVE: ");
        coinSet.remove(removeCoin);

    }

     /*
        변동률 = 이전가격 - 신가격 / 100
        신가격 = 변동률 * 100 + 이전가격
        신수량 = 이전수량 - 변동률*이전수량
    */

    // 가격이 랜덤으로 바뀌는 메소드
    public void changeCoinPriceByRandom(String coinName, int changePriceRange) throws IOException {
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            // 총 범위 = 2 * 입력 범위 * 랜덤함수 - 입력범위
            int range = 2 * changePriceRange + 1;
            int priceOfChange = (int) (Math.random() * range - changePriceRange);
            int previousPrice = tmp.getPrice();
            // 랜덤 가격 변경
            tmp.setPrice( tmp.getPrice() + priceOfChange );

            // 가격이 0이하면 상장폐지로 코인을 삭제
            if (tmp.getPrice() > 0) {
                // 가격변경에 따라서 변동률 변경
                tmp.setRateOfChange( (previousPrice - tmp.getPrice()) / 100.0 );

                // 가격변경에 따라서 수량 변경
                tmp.setAmount( tmp.getAmount() - (int)(tmp.getRateOfChange() * tmp.getAmount()) );
                sendMessageMulticast(tmp, "UPDATE: ");
            } else {
                removeCoin(tmp.getName());
            }

        }
    }

    public void setNotice(String coinName, String notice) {
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            String str = "[Notice: " + notice +" ]";
            tmp.getNotice().add(str);
        }
    }

    public void printNotice(String coinName){
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            for (int index = 0; index < tmp.getNotice().size(); index++) {
                System.out.println(tmp.getNotice().get(index));
            }
        }
    }

    public String getNotice(String coinName) {
        String str = "<전체공지>";
        if (isCoin(coinName)) {
            Coin tmp = findCoin(coinName);
            for (int index = 0; index < tmp.getNotice().size(); index++) {
                str = str + "\n" +  tmp.getNotice().get(index);
            }
        }
        return str;
    }

    public void printNoticeToClient(String coinName) throws IOException {
        if (isCoin(coinName)) {
            String str = "<전체공지>";
            Coin tmp = findCoin(coinName);
            for (int index = 0; index < tmp.getNotice().size(); index++) {
                str = str + "\n" + tmp.getNotice().get(index);
            }
            String Message = tmp.getName() + str;
            byte[] sendMessage = Message.getBytes();
            sendPacket = new DatagramPacket(sendMessage, sendMessage.length, inetAddress, port);
            multicastSocket.send(sendPacket);
        }
    }
}
