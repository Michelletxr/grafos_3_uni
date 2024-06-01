package network.graph.node;

import loadBalancer.LoadBalancer;
import network.Utils;
import org.jfree.data.time.Millisecond;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServerNode extends Node {
    public String name;
    public Integer timeResponse;
    public  String lb_host;
    public int lb_port;
    public int port;
    public int totalRequests = 0;
    private String data;
    private LoadBalancer loadBalancer = new LoadBalancer();

    public ServerNode(int id, String name, int totalRequests, int timeResponse, int port) {
        super(id, name);
        this.port = port;
        this.name = name;
        this.timeResponse = timeResponse;
        this.totalRequests = totalRequests;
    }

    public ServerNode() {
        super(-1, "");
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ServerNode && ((ServerNode) o).id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Utils.logInfo(this.name , "started server node on port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                        String request = in.readLine();
                        Utils.logInfo(this.name , "received request: " + request);
                        int millisecond = timeResponse*100000;
                        Thread.sleep(millisecond);
                        out.println(name + " response to " + request);
                        sendDataToSave(request);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToSave(String request){
        Utils.logInfo(this.name , "send data to database................");
        try {
            loadBalancer.processingDataInDatabase(this.name, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Utils.logInfo(this.name, "Dado enviado para database!");
    }

    public void startConn(LoadBalancer loadBalancer){
        this.loadBalancer = loadBalancer;

    }

    public String receiveRequest(String request) throws InterruptedException {
        Utils.logInfo(this.name , "received request: " + request);
        String response = name + " response to " + request;
        sendDataToSave(request);
        return response;
    }

;
}

