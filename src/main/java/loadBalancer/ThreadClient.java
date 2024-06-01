package loadBalancer;
import network.graph.node.ServerNode;

import java.io.*;
import java.net.Socket;

public class ThreadClient extends Thread{

    private final Socket socketConn;
    private final String request;
    private ServerNode serverNode = new ServerNode();

    public ThreadClient(Socket connetion, ServerNode serverNode, String request){
        System.out.println("new connetion thread client");
        this.socketConn = connetion;
        this.serverNode = serverNode;
        this.request = request;
    }

    @Override
    public void run()
    {
        try {
            PrintWriter out = new PrintWriter(socketConn.getOutputStream(), true);
            if (serverNode != null) {
                out.println("Connect to server: " + serverNode.name);
                System.out.println("Redirecting client to server: " + serverNode.name);
                System.out.println("response time: "+serverNode.timeResponse);
                Thread.sleep(serverNode.timeResponse*100);
                String response = serverNode.receiveRequest(request);
                out.println(response);
            } else {
                out.println("No server available");
                System.out.println("No server available");
            }
            socketConn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}