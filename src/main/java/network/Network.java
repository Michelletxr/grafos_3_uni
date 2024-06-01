package network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import network.graph.Graph;
import network.graph.node.ClientNode;
import network.graph.node.DatabaseNode;
import network.graph.node.Node;
import network.graph.node.ServerNode;
import network.graph.node.SourceNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static network.algoritms.Algoritms.minCostMaxFlowWithBF;

public interface Network {
    List<ClientNode> clients = new ArrayList<>();
    List<ServerNode> servers = new ArrayList<>();

    public static Graph createNetoworkFromFile(String filename) throws IOException, FileNotFoundException {
        Graph graph = new Graph();
        Gson gson = new Gson();

        SourceNode source = null;
        DatabaseNode databaseSink = null;

        try (FileReader reader = new FileReader(filename)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Parse clients
            JsonArray clientsArray = jsonObject.getAsJsonArray("clients");
            for (int i = 0; i < clientsArray.size(); i++) {
                JsonObject clientObj = clientsArray.get(i).getAsJsonObject();
                ClientNode client = new ClientNode(
                        clientObj.get("id").getAsInt(),
                        clientObj.get("total_requests").getAsInt(),
                        clientObj.get("name").getAsString()
                );
                clients.add(client);
                graph.addNode(client);
            }

            // Parse servers
            JsonArray serversArray = jsonObject.getAsJsonArray("servers");
            for (int i = 0; i < serversArray.size(); i++) {
                JsonObject serverObj = serversArray.get(i).getAsJsonObject();
                ServerNode server = new ServerNode(
                        serverObj.get("id").getAsInt(),
                        serverObj.get("name").getAsString(),
                        serverObj.get("totalRequests").getAsInt(),
                        serverObj.get("timeResponse").getAsInt(),
                        serverObj.get("port").getAsInt()
                );
                servers.add(server);
                graph.addNode(server);
            }

            // Add source and databaseSink nodes
            source = new SourceNode(0);
            graph.addNode(source);
            int databaseId = graph.nodes.size();
            databaseSink = new DatabaseNode(databaseId, 100, 100);
            graph.addNode(databaseSink);

            // Parse edges
            JsonArray edgesArray = jsonObject.getAsJsonArray("edges");
            for (int i = 0; i < edgesArray.size(); i++) {
                JsonObject edgeObj = edgesArray.get(i).getAsJsonObject();
                String from = edgeObj.get("from").getAsString();
                String to = edgeObj.get("to").getAsString();
                int capacity = edgeObj.get("capacity").getAsInt();
                int cost = edgeObj.get("cost").getAsInt();

                Node fromNode = graph.getNodeByName(from);
                Node toNode = graph.getNodeByName(to);
                if (fromNode != null && toNode != null) {
                    graph.addEdge(fromNode, toNode, capacity, cost);
                }
            }

            graph.total_nodes = graph.nodes.size();

        }

        int[] result = minCostMaxFlowWithBF(graph, new int[0][0], source.id, databaseSink.id);
        Utils.printNetwork(graph);
        System.out.println(result[0] + " " + result[1]);

        return graph;
    }

    public static void main(String[] args) {
        try {
            createNetoworkFromFile("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/network.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
