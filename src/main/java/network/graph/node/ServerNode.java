package network.graph.node;

import network.Utils;

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

    public ServerNode(int id, String name, int totalRequests, int timeResponse, int port) {
        super(id, name);
        this.port = port;
        this.name = name;
        this.timeResponse = timeResponse;
        this.totalRequests = totalRequests;
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
                        //Thread.sleep(10000);
                        out.println(name + "response to " + request);
                        sendDataToSave(request);
                        //fechar conexão
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToSave(String request){
        Utils.logInfo(this.name , "send data to database................");
        try (Socket socket = new Socket(lb_host, 8080);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String serviceRequest = this.name;
            out.println(serviceRequest);
            String response = in.readLine();
            //System.out.println("Load Balancer response: " + response);

            // Connect to the redirected server
            if (response.startsWith("Connect to database_server: ")) {
                String[] serverInfo = response.substring("Connect to server: ".length()).split(":");
                //System.out.println("try connect " + serverInfo[0]);
                String database_host = "localhost";
                int database_port = Integer.parseInt(serverInfo[0]);

                try (Socket serverSocket = new Socket(database_host, database_port);
                     BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                     PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true)) {
                        serverOut.println(request);
                        socket.close();
                     }

            }
            Utils.logInfo(this.name, "Dado enviado para database!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

;
}

