package loadBalancer;
import java.io.*;
import java.net.Socket;

public class ThreadClient extends Thread{

    private final Socket socketConn;
    private final String serverPort;

    public ThreadClient(Socket connetion, String serverPort){
        System.out.println("new connetion thread client");
        this.socketConn = connetion;
        this.serverPort = serverPort;
    }

    @Override
    public void run()
    {
        try {
            PrintWriter out = new PrintWriter(socketConn.getOutputStream(), true);
            if (serverPort != null) {
                out.println("Connect to server: " + serverPort);
                System.out.println("Redirecting client to server: " + serverPort);
            } else {
                out.println("No server available");
                System.out.println("No server available");
            }
            socketConn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}