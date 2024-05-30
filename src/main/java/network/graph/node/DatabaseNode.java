package network.graph.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DatabaseNode extends Node {
    public int storage_capacity = 0;
    public int cost_data;
    public int port = 5000;
    private List<String> database = new ArrayList<>();

    public DatabaseNode(int id, int storage_capacity, int cost_data) {
        super(id, "DB");
        this.name = "DB";

        this.storage_capacity = storage_capacity;
        this.cost_data = cost_data;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(this.name + " started database server node on port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                   ///  String r = in.readLine();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    String requestData = in.readLine();
                    System.out.println(name + " received data: " + requestData);
                    saveData(requestData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveData(String data) {
        database.add(data);
    }
}

