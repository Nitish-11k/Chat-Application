import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public Client(){
        try {
            System.out.println("Requesting Connection to Server");
            socket = new Socket("localhost", 8080);
            System.out.println("Connection Build Successfully");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startReading() {
        Runnable r1 = ()->{
            System.out.println("Reader Started");
            try{
                while (true) {
                    String msg = reader.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Sever Termnated !!!");
                        break;
                    }
                    System.out.println("Server : " + msg);
                }
            }catch (IOException e){
                e.printStackTrace();;
            }
        };
        new Thread(r1).start();
    }

    private void startWriting() {
        Runnable r2  = ()->{
            try{
                while (true){
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String content = br.readLine();
                    writer.println(content);
                    writer.flush();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is Client.");
        new Client();
    }
}
