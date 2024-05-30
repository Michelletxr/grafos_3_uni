package network.graph.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNode extends Node {
    public int total_requests;
    public  String lb_host;
    public int lb_port;
    public String name = "A";

    public ClientNode(int id, int total_requests, String name) {
        super(id, name);
        this.total_requests = total_requests;
        this.name = name;
    }

    public ClientNode(String name) {
        super(-1,name);
        this.name = name;
    }

    public void start(){
        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String request = this.name;
            out.println(request);
            String response = in.readLine();
            System.out.println("Load Balancer response: " + response);
            // Connect to the redirected server
            if (response.startsWith("Connect to server: ")) {
                String[] serverInfo = response.substring("Connect to server: ".length()).split(":");
                System.out.println("try connect " + serverInfo[0]);
                String serverHost = "localhost";
                int serverPort = Integer.parseInt(serverInfo[0]);
                System.out.println("try connect " + serverHost + ":" + serverPort);

                try (Socket serverSocket = new Socket(serverHost, serverPort);
                     BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                     PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true)) {
                    serverOut.println(request);
                    String serverResponse = serverIn.readLine();
                    System.out.println("Server response: " + serverResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
