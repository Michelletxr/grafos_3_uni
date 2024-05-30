package loadBalancer;

import network.Utils;
import network.graph.Edge;
import network.graph.Graph;
import network.graph.node.DatabaseNode;
import network.graph.node.Node;
import network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class LoadBalancer {
    private final Integer port;
    private final String host;
    private Graph network = new Graph();
    private Map<Integer, Integer> servicesPorts;

    public void initServices(){
        this.servicesPorts = new HashMap<>();
        Network.servers.forEach(server -> {
            this.servicesPorts.put(server.id, server.port);
            new Thread(server::start).start();
        });
        DatabaseNode databaseSink = (DatabaseNode) network.getNodeByName("DB");
        servicesPorts.put(databaseSink.id, databaseSink.port);
        new Thread(databaseSink::start).start();

    }

    public LoadBalancer(Integer port,  Graph network) {
        this.port = port;
        this.host = "localhost";
        this.network = network;
    }

    public void start() throws IOException, ClassNotFoundException {

        ServerSocket serverSocket = new ServerSocket(this.port);
        Utils.logInfo("LB","iniciando serviço load balancer na porta:" + serverSocket.getLocalPort());
        initServices();

        while (true){
            Socket client = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String clientName = in.readLine();
            Integer serviceId = getServerId(clientName);
            if (serviceId == null){
                //Utils.logger()
            }
            new Thread(new ThreadClient(client, servicesPorts.get(serviceId).toString())).start();
        }
    }

    public void stopServer() throws IOException {
    }

    // método para definir servidor baseado no  fluxo
    public Integer getServerId(String clienName) {
        Integer serviceId = 0;
        Node nodeClient = network.getNodeByName(clienName);
        Set<Edge> edges = network.adj_edges.get(nodeClient.id);
        for (Edge edge : edges) {
            //System.out.println(serviceId);
            if(edge.flow>0){
                serviceId = edge.to;
                network.updateFlowEdge(edge.from, edge.to, -1);
                break;
            }
        }
        if(serviceId != 0){
            return  serviceId;
        }
        return null;
    }

}
