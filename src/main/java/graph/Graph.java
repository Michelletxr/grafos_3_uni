package graph;

import java.util.*;

public class Graph {

    public final Set<ServerNode> nodes = new HashSet<>();
    public final Map<String, Set<ServerNode>> adjacencyNodes = new HashMap<>();

    public final Set<Edge> edges = new HashSet<>();
    public Map<String, Set<Edge>> adj_edges = new HashMap<>();

    public Map<String, Integer> potential = new HashMap<>();

    public int total_nodes = 0;
    public int total_edges = 0;

    public void addServerNode(ServerNode server) {
        potential.put(server.name, 0);

        nodes.add(server);

        adjacencyNodes.put(server.name, new HashSet<>());
        adj_edges.put(server.name, new HashSet<>());


        this.total_nodes++;
    }

    public void addEdge(ServerNode source, ServerNode destination, Integer capacity, Integer cost) {
        //verifica se nos existem no grafo
        if(!nodes.contains(source)) {
            addServerNode(source);
        }
        if(!nodes.contains(destination)) {
            addServerNode(destination);
        }

        // Cria nova aresta
        Edge newEdge = new Edge(source.name, destination.name, capacity, cost);
        // Atualiza arestas do grafo
        edges.add(newEdge);
        // Atualiza arestas adjacentes do nó source
        Set<Edge> up_adj_edges = adj_edges.get(source.name);
        up_adj_edges.add(newEdge);
        adj_edges.put(source.name, up_adj_edges);

        // Atualiza nós adjacentes do nó source e do nó destination
        adjacencyNodes.get(source.name).add(destination);
        adjacencyNodes.get(destination.name).add(source);
        total_edges++;
    }

    public ServerNode getServerNode(String nodeName) {
        return nodes.stream().filter(n -> n.name.equals(nodeName)).findFirst().orElse(null);
    }

    public Edge getEdge(String source, String destination) {
        for (Edge edge : edges) {
            if (edge.from.equals(source) && edge.to.equals(destination)) {
                return edge;
            }
        }
        return null;
    }

    public Set<String> getNameServersNodes() {
        return adjacencyNodes.keySet();
    }

    public int getEdgeCapacity(String source, String destination) {

        for (Edge edge : edges) {
            if (edge.from.equals(source) && edge.to.equals(destination)) {
                return edge.capacity;
            }
        }
        return 0;
    }

    public void updateEdgeCapacity(String source, String destination, int newCapacity) {
        edges.forEach(edge -> {
            if (edge.from.equals(source) && edge.to.equals(destination)) {
                edge.capacity = newCapacity;
            }
        });
    }
}
