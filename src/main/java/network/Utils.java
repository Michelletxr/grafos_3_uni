package network;

import network.graph.*;
import network.graph.node.Node;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface Utils {
    static final Logger logger = Logger.getLogger("Logger");
    static void printPath(Map<Integer, Edge> prev_path, Node sink) {
        List<Integer> path = new ArrayList<>();
        for (Edge e = prev_path.get(sink.id); e != null; e = prev_path.get(e.from)) {
            path.add(e.from);
        }

        Collections.reverse(path);
        path.add(sink.id);
        System.out.println("Caminho de aumento: " + String.join(" -> ", path.stream().toString()));
        //adaptar classes de algoritmos para nova estrutura
    }

    static void printNetwork(Graph graph) {

        System.out.println("Origem -> Destino | Capacidade | Custo | Fluxo");
        graph.edges.stream().forEach(edge -> {
            System.out.printf("%d -> %d          | %d         | %d   | %d\n", edge.from, edge.to, edge.getCapacity(), edge.cost, edge.flow);
        });
    }

    static public void createFlow(){}

    public static void logInfo(String identifier,String message) {

        logger.log(Level.INFO, identifier +" - ["+ message+"]");

    }

}
