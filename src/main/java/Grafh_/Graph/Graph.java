package Grafh_.Graph;

import java.util.*;

class Graph {
    Map<String, List<Edge>> adj;
    Set<ServerNode> nodes;
    List<Edge> edges;

    public Graph() {
        adj = new HashMap<>();
        nodes = new HashSet<>();
        edges = new ArrayList<>();
    }

    void addEdge(ServerNode from, ServerNode to, int capacity, int cost) {
        Edge edge = new Edge(from, to, capacity, cost);
        adj.computeIfAbsent(from.name, k -> new ArrayList<>()).add(edge);
        adj.computeIfAbsent(to.name, k -> new ArrayList<>()).add(new Edge(to, from, 0, -cost)); // Aresta reversa
        nodes.add(from);
        nodes.add(to);
        edges.add(edge);
    }

    List<Edge> adj(ServerNode node) {
        return adj.getOrDefault(node.name, new ArrayList<>());
    }
}
