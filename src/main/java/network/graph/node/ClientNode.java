package network.graph.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNode extends Node {
    public int total_requests;
    public Boolean stopped = false;

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

                if(response.contains("No server available")){
                    this.stopped = true;
                }
                // Connect to the redirected server
                if (response.startsWith("Connect to server: ")) {
                    String response2 = in.readLine();
                    System.out.println("Server response: " + response2);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
