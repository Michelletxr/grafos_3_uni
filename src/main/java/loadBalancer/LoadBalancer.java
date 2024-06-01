package loadBalancer;

import network.Utils;
import network.graph.Edge;
import network.graph.Graph;
import network.graph.node.ClientNode;
import network.graph.node.DatabaseNode;
import network.graph.node.Node;
import network.Network;
import network.graph.node.ServerNode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class LoadBalancer {
    private Integer port;
    private String host;
    private Graph network = new Graph();
    private Map<Integer, Integer> servicesPorts;
    private Map<Integer, ServerNode> serverNodes = new HashMap<>();
    boolean test_minMax = true;

    private Map<Integer, List<ServerNode>> clientNodes = new HashMap<Integer, List<ServerNode>>();

    public LoadBalancer() {}
    public LoadBalancer(Integer port,  Graph network, Boolean test_minMax) {
        this.port = port;
        this.host = "localhost";
        this.network = network;
        this.test_minMax = test_minMax;
    }

    public void initServices(){
        this.servicesPorts = new HashMap<>();
        Network.servers.forEach(server -> {
            this.servicesPorts.put(server.id, server.port);
            this.serverNodes.put(server.id, server);
            server.startConn(this);
        });
        DatabaseNode databaseSink = (DatabaseNode) network.getNodeByName("DB");
        servicesPorts.put(databaseSink.id, 5000);
        //new Thread(databaseSink::start).start();
    }

    public void initClients(){
        //terminar essa função
        //gerar graficos comparando os 3 serviços
        this.clientNodes = new HashMap<Integer, List<ServerNode>>();
        Network.clients.forEach(networkNode -> {
            List<ServerNode> nodes = new ArrayList<>();
            network.getAdj_edges(networkNode.id).forEach(
                    edge -> {nodes.add((ServerNode) network.getNode(edge.to));}
            );
            Integer id = networkNode.id;
            clientNodes.put(id, nodes);
        });
    }

    public void start() throws IOException, ClassNotFoundException {

        ServerSocket serverSocket = new ServerSocket(this.port);
        Utils.logInfo("LB","iniciando serviço load balancer na porta:" + serverSocket.getLocalPort());
        initServices();
        initClients();

        while (true){
            Socket client = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String clientName = in.readLine();
            Integer serviceId = null;

            if(test_minMax){
                serviceId = getServerId(clientName);
            }else{
                serviceId = getServerIdRB(clientName);
            }

            if (serviceId == null) {
                new Thread(new ThreadClient(client, null, "")).start();
            }else{
                new Thread(new ThreadClient(client, serverNodes.get(serviceId), clientName)).start();
            }
        }
    }

    public void processingDataInDatabase(String serverName, String data) throws IOException {
        Integer serverId = getServerId(serverName);
        //System.out.println(serverId);
        Socket serverSocket = new Socket(this.host, 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true){
            //out.println(request);
            //String response = in.readLine();
        };
        out.println(data);
    }

    // método para definir servidor baseado no  fluxo
    public Integer getServerId(String clienName) {
        Integer serviceId = 0;
        Node nodeClient = network.getNodeByName(clienName);
        Set<Edge> edges = network.adj_edges.get(nodeClient.id);
        for (Edge edge : edges) {
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


    public Integer getServerIdRB(String clienName) {
        AtomicReference<Integer> serviceId = new AtomicReference<>(0);
        ClientNode clientNode = (ClientNode) network.getNodeByName(clienName);
        clientNodes.forEach((key, value) -> {
            if(key == clientNode.id && clientNode.total_requests > 0){
                for(ServerNode serverNode : value){
                    if(serverNode.totalRequests > 0){
                        serverNode.totalRequests-=1;
                        clientNode.total_requests-=-1;
                        System.out.println(clientNode.total_requests);
                        serviceId.set(serverNode.id);
                        break;
                    }
                }
            }
            if(serviceId.get() != 0){
                return;
            }
        });

        if(serviceId.get() != 0){
            return serviceId.get();
        }
        return null;
    }
}
