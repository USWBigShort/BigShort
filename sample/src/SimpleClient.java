import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {
        System.out.println("Client Started");
        try (Socket socket = new Socket("127.0.0.1", 8888);
             PrintStream out = new PrintStream(socket.getOutputStream());
             InputStreamReader isr = new InputStreamReader(socket.getInputStream());
             BufferedReader br = new BufferedReader(isr)){
            BufferedReader inputStringReader = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                String inputString = inputStringReader.readLine();
                out.println(inputString);
                System.out.println("Request sent : " + inputString);
                System.out.println("Response: " + br.readLine());
            }


//
//
//            // Sending second request
//            socket = new Socket("127.0.0.1", 5000);
//            System.out.println("Connected to a Server");
//            out = new PrintStream(socket.getOutputStream());
//            isr = new InputStreamReader(socket.getInputStream());
//            br = new BufferedReader(isr);
//
//            partName = "Wheel";
//            out.println(partName);
//            System.out.println(partName + " request sent");
//            System.out.println("Response: " + br.readLine());
//            socket.close();
//            partName = "Quit";
//            out.println(partName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Client Terminated");
    }
}
