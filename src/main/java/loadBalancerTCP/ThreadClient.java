package loadBalancerTCP;



import java.io.*;
import java.net.Socket;

public class ThreadClient extends Thread{

    private final Socket socketConn;
    private final ServerTCP server;


    public ThreadClient(Socket connetion, ServerTCP server){
        System.out.println("new connetion thread client");
        this.socketConn = connetion;
        this.server = server;
    }


    @Override
    public void run()
    {

        System.out.println("helo thread");
        String msg = null;

        try {
            System.out.println("Esperando mensagem.........");
            msg = server.receivePackage(socketConn.getInputStream());
            System.out.println("mensagem recebida:" + msg);
            //String response = server.generateResponseToSend(msg);
            String response = "response";
            server.sendPackage(response, socketConn.getOutputStream());
            socketConn.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}