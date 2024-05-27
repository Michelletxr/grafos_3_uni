package graph;

import graph.node.Node;

import java.util.*;

public class Graph {
    public final Set<Node> nodes = new HashSet<>();
    public final Map<Integer, Set<Node>> adjacencyNodes = new HashMap<>();
    public final Set<Edge> edges = new HashSet<>();
    public Map<Integer, Set<Edge>> adj_edges = new HashMap<>();
    public int total_cost = 0;
    public int total_nodes = 0;
    public int total_edges = 0;
    public int total_flow=0;

    public void updateFlowCapacityEdge(Integer source, Integer destination, int newflow){
        edges.forEach(edge -> {
            if (edge.from.equals(source) && edge.to.equals(destination)) {
                edge.flow+=newflow;
                edge.capacity-=newflow;
                this.total_flow+=newflow;
            }
        });
    }

    public Edge getEdge(Integer source, Integer destination) {
        for (Edge edge : edges) {
            if (edge.from.equals(source) && edge.to.equals(destination)) {
                return edge;
            }
        }
        return null;
    }
    public Collection<Node> getAdjacencyNodes(Integer id) {
        return adjacencyNodes.get(id);
    }

    public Collection<Edge> getEdges() {
        return edges;
    }
    public List<Integer> getNodesKeys(){
        List<Integer> keys = new ArrayList<>();
        nodes.stream().forEach(node -> {keys.add(node.id);});
        return keys;
    }

    public Collection<Edge> getAdj_edges(Integer id) {
        return adj_edges.get(id);
    }

    public void addEdge(Node source, Node destination, Integer capacity, Integer cost) {

        //verifica se nos existem no grafo
        if(!nodes.contains(source)) {
            addNode(source);
            adj_edges.put(source.id, new HashSet<>());
        }
        if(!nodes.contains(destination)) {
            addNode(destination);
            adj_edges.put(destination.id, new HashSet<>());
        }

        // Cria nova aresta
        Edge newEdge = new Edge(source.id, destination.id, capacity, cost);
        // Atualiza arestas do grafo
        edges.add(newEdge);
        // Atualiza arestas adjacentes do nó source
        Set<Edge> up_adj_edges = adj_edges.get(source.id);
        up_adj_edges.add(newEdge);
        adj_edges.put(source.id, up_adj_edges);
        // Atualiza nós adjacentes do nó source e do nó destination
        adjacencyNodes.get(source.id).add(destination);
        adjacencyNodes.get(destination.id).add(source);
        total_edges=edges.size();
    }

    private void addNode(Node node) {
        nodes.add(node);
        adjacencyNodes.put(node.id, new HashSet<>());
        this.total_nodes=nodes.size();
    }

}
