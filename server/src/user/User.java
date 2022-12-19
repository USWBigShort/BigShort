package user;

import java.net.Socket;
//스레드 생성은 main쪽에서 new Thread(new User(socket)).start() 해주면 됨
public class User{
    //유저를 구분하기 위한 유저명
    private String name;
    //유저가 보유중인 돈
    private int money = 1000;
    //유저가 보유중인 코인의 리스트<코인명, 가진개수>
    //소켓
    private final Socket clientSocket;

    public User(String name, Socket clientSocket) {
        this.name = name;
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    
    @Override
    public String toString() {
        return "유저 : " + name;
    }
}
