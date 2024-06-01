package network.graph.node;

import network.Network;
import network.Utils;
import network.graph.Graph;
import utils.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String request = in.readLine();
                System.out.println(name + " received data: " + request);
                saveData(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData(String data) {
        database.add(data);
    }

    public static void main() throws IOException {
        String fileName = FileUtils.getFileName();
        //String fileName = "network_1";
        Graph graph =  Network.createNetoworkFromFile("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/"+fileName+".json");
        DatabaseNode databaseNode = (DatabaseNode) graph.getNodeByName("DB");
        databaseNode.start();
    }
}

