package loadBalancer;
import network.Utils;
import network.graph.node.ServerNode;

import java.io.*;
import java.net.Socket;

public class ThreadClient extends Thread{

    private final Socket socketConn;
    private final String request;
    private ServerNode serverNode = new ServerNode();

    public ThreadClient(Socket connetion, ServerNode serverNode, String request){
        System.out.println("Nova conexão com o cliente!");
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
                Utils.logInfo("LB","Redirecionando ao serviço: " + serverNode.name);
                //Utils.logInfo("LB","Redirecionando cliente:  " + request);
               // out.println("Connect to server: " + serverNode.name);
                //System.out.println("Redirecting client to server: " + serverNode.name);
                System.out.println("response time: "+serverNode.timeResponse);
                Thread.sleep(serverNode.timeResponse);
                String response = serverNode.receiveRequest(request);
                out.println(response);
            } else {
                out.println("No server available");
                System.out.println("Falha na conexão!");
            }
            socketConn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}