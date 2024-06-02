package Test;
import network.Network;
import network.graph.Edge;
import network.graph.Graph;
import network.graph.node.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphvizExporter {

    public static void exportGraphToDot(Graph graph, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("digraph G {");
            for (Node node : graph.nodes) {
                writer.printf("  %d [label=\"%s\"];\n", node.id, node.name);
            }
            for (Edge edge : graph.edges) {
                writer.printf("  %d -> %d [label=\"%d/%d\"];\n", edge.from, edge.to, edge.capacity, edge.cost);
            }
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Graph graph = null;
        try {
            graph = Network.createNetoworkFromFile("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/network_"+"0"+".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        exportGraphToDot(graph, "network.dot");
        // Run Graphviz to generate the image (Assuming Graphviz is installed)
        try {
            Process process = Runtime.getRuntime().exec("dot -Tpng network.dot -o network.png");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
