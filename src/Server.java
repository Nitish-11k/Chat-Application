import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {
    Socket socket;
    ServerSocket server;
    BufferedReader reader;
    PrintWriter write;

    public Server() {
        try {
            server = new ServerSocket(8080);
            System.out.println("Server is waiting for the connection..");
            socket = server.accept();
            System.out.println("Connected Successfully");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            write = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startWriting() {
        Runnable r2 = ()->{
            System.out.println("Writer started");
            try {
                while (true){
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String content = br.readLine();
                    write.println(content);
                    write.flush();
                }
            }catch (IOException e ){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public void startReading(){
        Runnable r1 = ()->{
            System.out.println("Reader started");
            try {
                while(true) {
                    String msg = reader.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client Terminted!!!");
                        break;
                    }
                    System.out.println("Client : " + msg);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {
        System.out.println("Server is started...");
        new Server();
    }
}